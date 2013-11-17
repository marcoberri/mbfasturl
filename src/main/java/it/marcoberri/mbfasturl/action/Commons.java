/**
 * Copyright 2011 Marco Berri - marcoberri@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package it.marcoberri.mbfasturl.action;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Key;
import com.github.jmkgreen.morphia.query.Query;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.City;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.IpSpecify;
import it.marcoberri.mbfasturl.model.Log;
import it.marcoberri.mbfasturl.model.Url;
import it.marcoberri.mbfasturl.model.system.AppEvent;
import it.marcoberri.mbfasturl.utils.Default;
import it.marcoberri.mbfasturl.utils.HttpUtil;
import it.marcoberri.mbfasturl.utils.Log4j;
import it.marcoberri.mbfasturl.utils.StringUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
public class Commons {

    /**
     *
     */
    public final static org.apache.log4j.Logger log = Log4j.getLogger("mbfasturl", ConfigurationHelper.getProp().getProperty("log.path"), Log4j.ROTATE_DAILY);

    /**
     *
     * @param u
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static Url generateQrcodes(Url u) throws WriterException, IOException {

        final int smallx = Default.toInt(ConfigurationHelper.getProp().getProperty("qrcode.small.x", "50"), 50);
        final int smally = Default.toInt(ConfigurationHelper.getProp().getProperty("qrcode.small.y", "50"), 50);
        final int mediumx = Default.toInt(ConfigurationHelper.getProp().getProperty("qrcode.medium.x", "50"), 200);
        final int mediumy = Default.toInt(ConfigurationHelper.getProp().getProperty("qrcode.medium.y", "50"), 200);
        final int bigx = Default.toInt(ConfigurationHelper.getProp().getProperty("qrcode.big.x", "50"), 300);
        final int bigy = Default.toInt(ConfigurationHelper.getProp().getProperty("qrcode.big.y", "50"), 300);

        u.setQrcodeSmall(new ObjectId(generateQrcode(u, smallx, smally)));
        u.setQrcodeMedium(new ObjectId(generateQrcode(u, mediumx, mediumy)));
        u.setQrcodeBig(new ObjectId(generateQrcode(u, bigx, bigy)));

        return u;
    }

    /**
     *
     * @param u
     * @param dimx
     * @param dimy
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static String generateQrcode(Url u, int dimx, int dimy) throws WriterException, IOException {

        final String proxy = ConfigurationHelper.getProp().getProperty("url.proxy.domain", "http://mbfu.it/");
        final GridFS fs = MongoConnectionHelper.getGridFS();

        final QRCodeWriter writer = new QRCodeWriter();
        final BitMatrix bitMatrix = writer.encode(proxy + "/" + u.getFast(), BarcodeFormat.QR_CODE, dimx, dimy);
        final File temp = File.createTempFile("tempfile" + System.currentTimeMillis(), ".tmp");
        MatrixToImageWriter.writeToFile(bitMatrix, "gif", temp);

        final GridFSInputFile gfi = fs.createFile(temp);
        gfi.setFilename(u.getFast() + ".gif");

        final BasicDBObject meta = new BasicDBObject();
        meta.put("ref_url", u.getId());
        meta.put("created", new Date());
        gfi.setMetaData(meta);

        gfi.setContentType("image/gif");
        gfi.save();

        temp.deleteOnExit();

        return gfi.getId().toString();

    }

    /**
     *
     * @param log
     */
    public static void ipToLog(Logger log) {
        final long ts = System.currentTimeMillis();
        final Datastore ds = MongoConnectionHelper.ds;
        final Datastore ds2 = MongoConnectionHelper.ds;
        final Datastore ds3 = MongoConnectionHelper.ds;
        int count = 0;


        final Query q = ds2.createQuery(it.marcoberri.mbfasturl.model.Log.class).field("ipSpecify").doesNotExist();

        for (it.marcoberri.mbfasturl.model.Log logEl : (List<it.marcoberri.mbfasturl.model.Log>) q.asList()) {
            final String ip = logEl.getIp();
            final IpSpecify ipspecify = ds.createQuery(IpSpecify.class).field("ip").equal(ip).get();
            if (ipspecify == null) {
                continue;
            }
            logEl.setIpSpecify(ipspecify);
            ds3.save(logEl);
            count++;
        }


        writeEventLog("ipToLog", true, "tot element updated:" + count, "Manipulate", (System.currentTimeMillis() - ts));
    }

    /**
     *
     * @param log
     * @param mmdb
     */
    public static void ipCalc(Logger log, File mmdb) {

        final String action = "ipCalc";
        final long ts = System.currentTimeMillis();

        try {

            final Datastore ds = MongoConnectionHelper.ds;
            final Datastore dsSave = MongoConnectionHelper.ds;

            final DatabaseReader reader = new DatabaseReader(mmdb);

            int count = 0;
            String msg = "";
            for (Log l : ds.find(Log.class)) {

                if (StringUtil.isNullOrEmpty(l.getIp())) {
                    continue;
                }

                String ip = l.getIp();
                if (l.getIp().indexOf(",") > -1) {
                    ip = ip.substring(0, ip.indexOf(","));
                }

                if (StringUtil.isNullOrEmpty(l.getIp())) {
                    continue;
                }

                final IpSpecify check = ds.find(IpSpecify.class, "ip", ip).get();
                if (check != null) {
                    continue;
                }

                try {
                    final City model = reader.city(InetAddress.getByName(ip));
                    final IpSpecify modelIp = new IpSpecify();
                    modelIp.setIp(ip);
                    modelIp.setCity(model.getCity().getName());
                    modelIp.setPostalIso(model.getPostal().getCode());
                    modelIp.setContinent(model.getContinent().getName());
                    modelIp.setContinentIso(model.getContinent().getCode());
                    modelIp.setCountry(model.getCountry().getName());
                    modelIp.setCountryIso(model.getCountry().getIsoCode());
                    modelIp.setDivision(model.getMostSpecificSubdivision().getName());
                    modelIp.setDivisionIso(model.getMostSpecificSubdivision().getIsoCode());

                    if (model.getLocation().getLatitude() != null && model.getLocation().getLongitude() != null) {
                        final double loc[] = {model.getLocation().getLatitude(), model.getLocation().getLongitude()};
                        modelIp.setLoc(loc);
                    }
                    count++;
                    dsSave.save(modelIp);
                } catch (final AddressNotFoundException e) {
                    log.error("No AddressNotFoundException :" + ip);
                } catch (final GeoIp2Exception e) {
                    log.error(e.getMessage(), e);
                } catch (final IOException e) {
                    log.error(e.getMessage(), e);
                }
            }

            writeEventLog(action, true, "tot new elements:" + count + " msg:" + msg, "Manipulate", (System.currentTimeMillis() - ts));

        } catch (final IOException ex) {
            writeEventLog(action, false, ex.getMessage(), "Manipulate", (System.currentTimeMillis() - ts));
            log.fatal(ex);
        }
    }

    /**
     *
     * @param log
     * @param path
     * @param url
     */
    public static void downloadGeoIp2(Logger log, String path, String url) {

        long ts = System.currentTimeMillis();
        final String action = "downloadGeoIp2";

        try {
            org.apache.commons.io.FileUtils.forceMkdir(new File(path));
        } catch (final IOException ex) {
        }

        final String fileName = url.substring(url.lastIndexOf("/"), url.length());

        HttpUtil.downloadData(url, path + "/" + fileName);

        try {
            final File f = new File(path + "/" + fileName);

            final GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(f));

            final byte[] buffer = new byte[1024];
            final FileOutputStream out = new FileOutputStream(new File(path + "/" + fileName.replaceAll(".gz", "")));
            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gzis.close();
            out.close();

            writeEventLog(action, true, "File size:" + f.length(), "Download Resources", (System.currentTimeMillis() - ts));

        } catch (final IOException ex) {

            log.fatal(ex);
            writeEventLog(action, false, ex.getMessage(), "Download Resources", (System.currentTimeMillis() - ts));
        }

    }

    /**
     *
     * @param action
     * @param result
     * @param note
     */
    public static void writeEventLog(String action, boolean result, String note, String category, Long time) {
        final Datastore ds = MongoConnectionHelper.ds;
        final AppEvent appEventModel = new AppEvent();
        appEventModel.setAction(action);
        appEventModel.setResult(result);
        appEventModel.setNote(note);
        appEventModel.setCategory(category);
        appEventModel.setTime(time);
        ds.save(appEventModel);

    }
}
