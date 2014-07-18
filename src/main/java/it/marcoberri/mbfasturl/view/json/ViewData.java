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
package it.marcoberri.mbfasturl.view.json;

import it.marcoberri.mbfasturl.action.Commons;
import it.marcoberri.mbfasturl.helper.ConfigurationHelper;
import it.marcoberri.mbfasturl.helper.MongoConnectionHelper;
import it.marcoberri.mbfasturl.model.LogView;
import it.marcoberri.mbfasturl.utils.Default;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mongodb.morphia.Datastore;

import com.google.gson.Gson;

/**
 *
 * @author Marco Berri <marcoberri@gmail.com>
 */
@WebServlet(name = "ViewData", urlPatterns = { "/json/ViewData" })
public class ViewData extends HttpServlet {

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

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Response Data";
	}// </editor-fold>

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Commons.log.debug("start : " + this.getClass().getName());
		final Integer limit = Default.toInteger(request.getParameter("limit"));
		final Integer start = Default.toInteger(request.getParameter("start"));
		final List<LogView> result = ds.find(LogView.class).offset(start).limit(limit).order("-created").asList();

		long count = ds.find(LogView.class).countAll();

		final HashMap m = new HashMap();
		m.put("items", result);
		m.put("success", true);
		m.put("totalCount", count);

		final Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=" + this.charset);
		out.println(gson.toJson(m));
		out.close();
	}
}
