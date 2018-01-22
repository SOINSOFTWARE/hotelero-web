package com.soinsoftware.hotelero.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.soinsoftware.hotelero.core.controller.UserController;
import com.soinsoftware.hotelero.persistence.entity.Role;
import com.soinsoftware.hotelero.persistence.entity.User;

import lombok.extern.log4j.Log4j;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns = "/login")
@Log4j
public class LoginServlet extends AbstractServlet {

	private static final long serialVersionUID = 7094727863041162821L;

	private static final String MSG_NOT_LOGIN = "Usuario y/o contrase&ntilde;a no validos";

	protected static final String VIEW = "/login.jsp";

	protected static final String ATTRIBUTE_USERNAME = "username";

	protected static final String ATTRIBUTE_PASSWORD = "password";

	protected static final String ATTRIBUTE_MESSAGE = "msg";

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
		final RequestDispatcher distpatcher = hasLogged(request) ? setDistpatcherToHome(request, response)
				: setDistpatcherToLogin(request, "", "", "");
		distpatcher.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String username = getParameter(request, ATTRIBUTE_USERNAME);
		final String password = getParameter(request, ATTRIBUTE_PASSWORD);
		final User user = select(username, password);
		final boolean isValidLogin = isValidLogin(user);
		final RequestDispatcher distpatcher = isValidLogin ? setDistpatcherToHome(request, response)
				: setDistpatcherToLogin(request, username, password, MSG_NOT_LOGIN);
		if (!isValidLogin) {
			log.info(MSG_NOT_LOGIN);
			distpatcher.forward(request, response);
		} else {
			log.info("Login data accepted...");
			addUserToSession(request, user);
		}
	}

	private User select(final String username, final String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException ex) {
			log.error(ex);
		}
		return controller.select(username, password.toCharArray());
	}

	private boolean isValidLogin(final User user) {
		boolean isValid = false;
		if (user != null && user.isEnabled()) {
			final Role role = user.getRole();
			isValid = role != null && role.isEnabled();
		}
		return isValid;
	}

	private RequestDispatcher setDistpatcherToHome(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		final RequestDispatcher distpatcher = getRequestDispatcher(request, HomeServlet.VIEW);
		response.sendRedirect(request.getContextPath() + "/");
		return distpatcher;
	}
}
