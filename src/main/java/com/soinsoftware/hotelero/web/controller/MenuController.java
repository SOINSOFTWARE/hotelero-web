/**
 * 
 */
package com.soinsoftware.hotelero.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.soinsoftware.hotelero.core.controller.AccessController;
import com.soinsoftware.hotelero.core.controller.RoleAccessController;
import com.soinsoftware.hotelero.persistence.entity.Access;
import com.soinsoftware.hotelero.persistence.entity.Role;
import com.soinsoftware.hotelero.persistence.entity.RoleAccess;
import com.soinsoftware.hotelero.persistence.entity.User;

import lombok.extern.log4j.Log4j;

/**
 * @author Carlos Rodriguez
 * @since 1.0.0
 *
 */
@Log4j
public class MenuController {

	private static final String ATTRIBUTE_ROLE = "showRoleMenu";

	private final AccessController controller;

	public MenuController(final User loggedUser) {
		super();
		final List<Access> accesses = buildAccesses(loggedUser);
		controller = new AccessController(accesses);
	}

	public void addAttributesForMenu(final HttpServletRequest request) {
		request.setAttribute(ATTRIBUTE_ROLE, controller.canAccessRoleMenu());
	}

	private List<Access> buildAccesses(final User loggedUser) {
		List<Access> accesses = new ArrayList<>();
		final Role role = loggedUser.getRole();
		final RoleAccessController controller = new RoleAccessController();
		final List<RoleAccess> accesesByRole = controller.select(role);
		if (accesesByRole != null && !accesesByRole.isEmpty()) {
			log.info("Building accesses for role: " + role);
			for (final RoleAccess roleXAccess : accesesByRole) {
				log.info("Adding access: " + roleXAccess.getAccess().getCode());
				accesses.add(roleXAccess.getAccess());
			}
		} else {
			log.info("There is not accesses defined for role: " + role);
		}
		return accesses;
	}
}