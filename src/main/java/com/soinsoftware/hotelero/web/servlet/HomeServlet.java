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
@WebServlet(name = "home", urlPatterns = { "/home", "/" })
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
		final RequestDispatcher distpatcher = hasLogged(request) ? getRequestDispatcher(request, VIEW)
				: setDistpatcherToLogin(request, "", "", "");
		distpatcher.include(request, response);
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
