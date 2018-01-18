package com.soinsoftware.hotelero.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soinsoftware.hotelero.core.controller.UserController;
import com.soinsoftware.hotelero.persistence.entity.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PAGE = "./login.jsp";

	private final UserController controller;

	public LoginServlet() {
		super();
		controller = new UserController();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("username", "");
		request.setAttribute("password", "");
		request.setAttribute("msg", "");
		getRequestDispatcher(request).include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		System.out.println(username + " " + password);
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		final User user = controller.select(username, password.toCharArray());
		if (user != null && user.isEnabled()) {
			System.out.println("Login completed");
			request.setAttribute("msg", "Login completed");			
		} else {
			System.out.println("Usuario y/o contrase&ntilde;a no validos");
			request.setAttribute("msg", "Usuario y/o contrase&ntilde;a no validos");
		}
		getRequestDispatcher(request).forward(request, response);
	}

	private RequestDispatcher getRequestDispatcher(final HttpServletRequest request) {
		return request.getRequestDispatcher(PAGE);
	}
}
