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
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.LogView;
import it.marcoberri.mbfasturl.model.Url;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@WebServlet(name = "UrlRedirect", urlPatterns = {"/r/*"})
public class UrlRedirect extends HttpServlet {

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
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String fast = request.getPathInfo().replace("/", "");

        try {
            
            final Url u = ds.find(Url.class, "fast", fast).get();
            if (u == null) {
                Commons.log.fatal("Url " + request.getPathInfo() + " not found");
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            final LogView logView = getHeader(request);
            logView.setUrlId(u.getId());
            logView.setUrl(u.getUrl());
            logView.setFast(fast);

            ds.save(logView);
            response.sendRedirect(u.getRedirectComplete());


        } catch (IOException e) {
            Commons.log.fatal(e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /**
     *
     * @param request
     * @return
     */
    protected LogView getHeader(HttpServletRequest request) {

        final LogView logView = new LogView();

        Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            final String headerName = (String) headerNames.nextElement();

            logView.addHeader(headerName.toLowerCase(), request.getHeader(headerName));

        }

        logView.addHeader("request_remote_addr", request.getRemoteAddr());
        logView.addHeader("request_remote_host", request.getRemoteHost());
        logView.addHeader("request_remote_user", request.getRemoteUser());
        logView.addHeader("request_remote_remote_port", "" + request.getRemotePort());
        logView.addHeader("request_remote_url", "" + request.getRemotePort());

        //questo è da capire se ha senso
        String ip = request.getRemoteAddr();

        if (ip.equals("127.0.0.1") && logView.getHeaders().containsKey("x-forwarded-for")) {

            logView.addHeader("MBURL_request_getRemoteAddr", ip);
            ip = logView.getHeaders().get("x-forwarded-for");
        }
        
        logView.setIp(ip);
        return logView;

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
        return "Redirect functionality";
    }// </editor-fold>

}
