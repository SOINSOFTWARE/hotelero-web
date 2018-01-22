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
import com.soinsoftware.hotelero.web.exception.LoginException;

import lombok.extern.log4j.Log4j;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns = "/login")
@Log4j
public class LoginServlet extends AbstractServlet {

	private static final long serialVersionUID = 7094727863041162821L;

	private static final String MSG_NOT_LOGIN_1 = "Usuario y/o contrase&ntilde;a no validos.";

	private static final String MSG_NOT_LOGIN_2 = "El usuario no tiene permiso para ingresar a Hotelero.";

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
		try {
			validateLoggedUser(user);
			log.info("Login data accepted...");
			addUserToSession(request, user);
			setDistpatcherToHome(request, response);
		} catch (final LoginException ex) {
			log.info(ex.getMessage());
			setDistpatcherToLogin(request, username, password, ex.getMessage()).forward(request, response);
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

	private void validateLoggedUser(final User user) throws LoginException {
		if (user != null && user.isEnabled()) {
			final Role role = user.getRole();
			if (role == null || !role.isEnabled()) {
				throw new LoginException(MSG_NOT_LOGIN_2);
			}
		} else {
			throw new LoginException(MSG_NOT_LOGIN_1);
		}
	}

	private RequestDispatcher setDistpatcherToHome(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		final RequestDispatcher distpatcher = getRequestDispatcher(request, HomeServlet.VIEW);
		response.sendRedirect(request.getContextPath() + "/");
		return distpatcher;
	}
}
