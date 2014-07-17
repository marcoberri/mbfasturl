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

import org.mongodb.morphia.Datastore;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.LogSave;
import it.marcoberri.mbfasturl.model.Url;
import it.marcoberri.mbfasturl.utils.DateTimeUtil;
import it.marcoberri.mbfasturl.utils.Default;
import it.marcoberri.mbfasturl.utils.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@WebServlet("/s")
public class UrlSave extends HttpServlet {

    /**
     *
     */
    protected String charset = ConfigurationHelper.getProp().getProperty("app.charset", "UTF8");
    /**
     *
     */
    protected String proxy = ConfigurationHelper.getProp().getProperty("url.proxy.domain", "http://mbfu.it/");
    /**
     *
     */
    protected final Datastore ds = MongoConnectionHelper.ds;

    class objResult {

        private String url;
        private String urlComplete;
        private String fast;

        public String toJson() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }

        /**
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url the url to set
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return the completedUrl
         */
        public String getUrlComplete() {
            return urlComplete;
        }

        /**
         * @param completedUrl the completedUrl to set
         */
        public void setUrlComplete(String urlComplete) {
            this.urlComplete = urlComplete;
        }

        /**
         * @return the fast
         */
        public String getFast() {
            return fast;
        }

        /**
         * @param fast the fast to set
         */
        public void setFast(String fast) {
            this.fast = fast;
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String urlParam = request.getParameter("url");

        
         Commons.log.error("debug :" + UrlSave.class.getName());
         
        if (StringUtil.isNullOrEmpty(urlParam)) {
            Commons.log.error("params not found");
            return;
        }

           Commons.log.error("debug :" + urlParam);
      
           
        final Date bestBefore = DateTimeUtil.getDate("yyyy-MM-dd HH:mm:ss", Default.toString(request.getParameter("bestBefore"), DateTimeUtil.getNowWithOffsetAndFormat(ConfigurationHelper.getProp().getProperty("url.bestbefore", "12M"), "yyyy-MM-dd HH:mm:ss")));
        final URL utlToAnalyze = new URL(urlParam);

        String urltoSave = utlToAnalyze.getHost() + (utlToAnalyze.getPort() != -1 ? (":" + utlToAnalyze.getPort()) : "") + ((utlToAnalyze.getPath() != null && !utlToAnalyze.getPath().equals("")) ? ("/" + utlToAnalyze.getPath()) : ""); 
           
        Commons.log.error("debug :urltoSave 1: " + urltoSave);
        
         if(utlToAnalyze.getQuery() != null){
             urltoSave += "?" + utlToAnalyze.getQuery();
         }
        Commons.log.error("debug :urltoSave 2: " + urltoSave);
        
        Url u = ds.find(Url.class, "url", urltoSave).get();

        Commons.log.error("debug :find 1: " + u);

        
        if (u == null) {

            u = new Url();

            int let = 1;
            int check = 0;
            String fast;

            do {
                fast = randomString(let);
                check++;

                //incremento le lettere dopo 10 tentativi
                if (check == 10) {
                    check = 0;
                    let++;
                }

            } while (ds.find(Url.class, "fast", fast).get() != null);

            u.setFast(fast);
            u.setUrl(urltoSave);
            u.setProtocol(utlToAnalyze.getProtocol());
            u.setPort((utlToAnalyze.getPort() <= 0 ? 80 : utlToAnalyze.getPort()));
            u.setUrlComplete(proxy);
            u.setEnding(bestBefore);
            ds.save(u);

            Commons.log.error("debug :urltoSave 5: " + u);
            
            try {
                final Url qrcode = Commons.generateQrcodes(u);
                ds.save(qrcode);
            } catch (WriterException ex) {
                Commons.log.error(ex.getMessage(), ex);
            }

            final LogSave logSave = getHeader(request);
            logSave.setUrlId(u.getId());
            ds.save(logSave);

        }

        final objResult result = new objResult();
        result.setFast(u.getFast());
        result.setUrl(u.getUrl());
        result.setUrlComplete(proxy + "/" + u.getFast());

        final PrintWriter out = response.getWriter();
        response.setContentType("application/json;charset=" + this.charset);
        out.println(result.toJson());
        out.close();

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
        return "Short description";
    }// </editor-fold>

    /**
     *
     * @param length
     * @return
     */
    protected static String randomString(int length) {
        Random rnd = new Random();
        char[] arr = new char[length];

        for (int i = 0; i < length; i++) {
            int n = rnd.nextInt(36);
            arr[i] = (char) (n < 10 ? '0' + n : 'a' + n - 10);
        }

        return new String(arr);
    }

    /**
     *
     * @param request
     * @return
     */
    protected LogSave getHeader(HttpServletRequest request) {

        final LogSave logSave = new LogSave();

        Enumeration headerNames = request.getHeaderNames();
        String X_Forwarded_For = null;
        while (headerNames.hasMoreElements()) {
            final String headerName = (String) headerNames.nextElement();

            if (headerName.equals("X-Forwarded-For") || headerName.equals("x-forwarded-for")) {
                X_Forwarded_For = request.getHeader(headerName);
            }

            logSave.addHeader(headerName.toLowerCase(), request.getHeader(headerName));

        }

        logSave.addHeader("request_remote_addr", request.getRemoteAddr());
        logSave.addHeader("request_remote_host", request.getRemoteHost());
        logSave.addHeader("request_remote_user", request.getRemoteUser());
        logSave.addHeader("request_remote_remote_port", "" + request.getRemotePort());
        logSave.addHeader("request_remote_url", "" + request.getRemotePort());

        //questo è da capire se ha senso
        String ip = request.getRemoteAddr();

        if (ip.equals("127.0.0.1") && logSave.getHeaders().containsKey("x-forwarded-for")) {

            logSave.addHeader("MBURL_request_getRemoteAddr", ip);
            ip = logSave.getHeaders().get("x-forwarded-for");
        }

        logSave.setIp(ip);
        return logSave;

    }
}
