/**
 * Copyright 2013 Marco Berri - marcoberri@gmail.com
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
import com.google.zxing.WriterException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.LogQrcode;
import it.marcoberri.mbfasturl.model.Url;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@WebServlet(name = "QrCode", urlPatterns = {"/q/*"})
public class QrCode extends HttpServlet {

    /**
     *
     */
    protected String charset = ConfigurationHelper.getProp().getProperty("app.charset", "UTF8");
    /**
     *
     */
    protected String proxyUrl = ConfigurationHelper.getProp().getProperty("url.proxy.url", null);
    /**
     *
     */
    protected String proxyGetTo = ConfigurationHelper.getProp().getProperty("url.proxy.getto", "/");
    /**
     *
     */
    protected String proxyDomain = ConfigurationHelper.getProp().getProperty("url.proxy.domain", null);
    /**
     *
     */
    protected final Datastore ds = MongoConnectionHelper.ds;
    /**
     *
     */
    protected String proxy = ConfigurationHelper.getProp().getProperty("url.proxy.domain", "http://mbfu.it/");

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String fast = request.getPathInfo().replace("/", "");
        Url u = null;
        try {
            u = ds.find(Url.class, "fast", fast).get();
        } catch (final Exception e) {
            Commons.log.fatal("Url " + request.getPathInfo() + " not found", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        final LogQrcode logQrcode = getHeader(request);
        logQrcode.setUrlId(u.getId());
        logQrcode.setUrl(u.getUrl());
        logQrcode.setFast(fast);
        ds.save(logQrcode);


        final ObjectId qrcode_id = u.getQrcodeMedium();
        final GridFS fs = MongoConnectionHelper.getGridFS();

        GridFSDBFile file = fs.findOne(qrcode_id);

        if (file == null) {
            try {
                u = Commons.generateQrcodes(u);
                ds.save(u);
            } catch (final WriterException ex) {
                Commons.log.error(ex.getMessage(), ex);
            }

        }

        file = fs.findOne(qrcode_id);

        if (file == null) {
            Commons.log.fatal("Qrcode " + request.getPathInfo() + " not found second find");
            return;
        }


        file.writeTo(response.getOutputStream());
        response.setContentType(file.getContentType());
        response.setContentLength((int) file.getLength());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Response a Qrcode image";
    }// </editor-fold>

    /**
     *
     * @param request
     * @return
     */
    protected LogQrcode getHeader(HttpServletRequest request) {

        final LogQrcode logQrcode = new LogQrcode();

        final Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            final String headerName = (String) headerNames.nextElement();

            logQrcode.addHeader(headerName.toLowerCase(), request.getHeader(headerName));

        }

        logQrcode.addHeader("request_remote_addr", request.getRemoteAddr());
        logQrcode.addHeader("request_remote_host", request.getRemoteHost());
        logQrcode.addHeader("request_remote_user", request.getRemoteUser());
        logQrcode.addHeader("request_remote_remote_port", "" + request.getRemotePort());
        logQrcode.addHeader("request_remote_url", "" + request.getRemotePort());

        //questo è da capire se ha senso
        String ip = request.getRemoteAddr();

        if (ip.equals("127.0.0.1") && logQrcode.getHeaders().containsKey("x-forwarded-for")) {

            logQrcode.addHeader("MBURL_request_getRemoteAddr", ip);
            ip = logQrcode.getHeaders().get("x-forwarded-for");
        }


        logQrcode.setIp(ip);
        return logQrcode;

    }
}
