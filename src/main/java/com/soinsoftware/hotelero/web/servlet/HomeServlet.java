package com.soinsoftware.hotelero.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(name = "home", urlPatterns = "/home")
public class HomeServlet extends AbstractServlet {

	private static final long serialVersionUID = 7349453200699301557L;

	protected static final String VIEW = "/home.jsp";

	public HomeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher distpatcher = null;
		if (hasLogged(request)) {
			distpatcher = getRequestDispatcher(request, VIEW);
			distpatcher.include(request, response);
		} else {
			distpatcher = setDistpatcherToLogin(request, "", "", "");
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
