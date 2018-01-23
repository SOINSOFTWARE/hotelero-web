package com.soinsoftware.hotelero.web.servlet;

import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.soinsoftware.hotelero.persistence.entity.User;
import com.soinsoftware.hotelero.web.controller.MenuController;

/**
 * 
 * @author Carlos Rodriguez
 * @since 1.0.0
 *
 */
public class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = -7904234818630740985L;

	private static final String ATTRIBUTE_USER = "user";

	private static final String ATTRIBUTE_MENU_CONTROLLER = "menuController";

	protected RequestDispatcher getRequestDispatcher(final HttpServletRequest request, final String view) {
		return request.getRequestDispatcher(view);
	}

	protected void setAttribute(final HttpServletRequest request, final String key, final Object value) {
		request.setAttribute(key, value);
	}

	protected String getParameter(final HttpServletRequest request, final String key) {
		return request.getParameter(key);
	}

	protected List<String> getParameterValues(final HttpServletRequest request, final String key) {
		final String[] values = request.getParameterValues(key);
		return values != null ? Arrays.asList(values) : null;
	}

	protected void addUserToSession(final HttpServletRequest request, final User user) {
		final HttpSession session = request.getSession();
		session.setAttribute(ATTRIBUTE_USER, user);
	}

	protected User getUserFromSession(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		return (session != null) ? (User) session.getAttribute(ATTRIBUTE_USER) : null;
	}

	protected boolean hasLogged(final HttpServletRequest request) {
		return getUserFromSession(request) != null;
	}

	protected void buildMenu(final HttpServletRequest request) {
		MenuController controller = getMenuControllerFromSession(request);
		if (controller == null) {
			final User loggedUser = getUserFromSession(request);
			controller = new MenuController(loggedUser);
			addMenuControllerToSession(request, controller);
		}
		controller.addAttributesForMenu(request);
	}

	protected void addMenuControllerToSession(final HttpServletRequest request, final MenuController controller) {
		final HttpSession session = request.getSession();
		session.setAttribute(ATTRIBUTE_MENU_CONTROLLER, controller);
	}

	protected MenuController getMenuControllerFromSession(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		return (session != null) ? (MenuController) session.getAttribute(ATTRIBUTE_MENU_CONTROLLER) : null;
	}

	protected void invalidateSession(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	protected RequestDispatcher setDistpatcherToLogin(final HttpServletRequest request, final String username,
			final String password, final String msg) {
		final RequestDispatcher distpatcher = getRequestDispatcher(request, LoginServlet.VIEW);
		setAttribute(request, LoginServlet.ATTRIBUTE_USERNAME, username);
		setAttribute(request, LoginServlet.ATTRIBUTE_PASSWORD, password);
		setAttribute(request, LoginServlet.ATTRIBUTE_MESSAGE, msg);
		return distpatcher;
	}
}