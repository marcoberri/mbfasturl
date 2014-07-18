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
package it.marcoberri.mbfasturl.view;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@WebServlet(name = "Migrate", urlPatterns = { "/Migrate" })
public class Migrate extends HttpServlet {
	/*
	 * protected final static org.apache.log4j.Logger log =
	 * Log4j.getLogger("migrate",
	 * ConfigurationHelper.getProp().getProperty("log.path"),
	 * Log4j.ROTATE_DAILY);
	 * 
	 * protected void processRequest(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * 
	 * 
	 * 
	 * final MigradeThread m = new MigradeThread(); new Thread(m).start();
	 * 
	 * response.setContentType("text/html;charset=UTF-8"); PrintWriter out =
	 * response.getWriter(); try { out.println("<html>"); out.println("<head>");
	 * out.println("<title>Migration tools</title>"); out.println("</head>");
	 * out.println("<body>");
	 * out.println("<h1>Migration tools start now </h1>");
	 * out.println("</body>"); out.println("</html>"); } finally { out.close();
	 * } }
	 * 
	 * @Override protected void doGet(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * processRequest(request, response); }
	 * 
	 * @Override protected void doPost(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * processRequest(request, response); }
	 * 
	 * @Override public String getServletInfo() { return "Short description";
	 * }// </editor-fold> }
	 * 
	 * class MigradeThread implements Runnable {
	 * 
	 * @Override public void run() { final MigrateTools migrate = new
	 * MigrateTools(); migrate.dropDB(); migrate.exportJson();
	 * migrate.importJson(); }
	 */
}
