package com.wallet.app.constants;

import com.wallet.app.persist.entity.UserAccount;
import com.wallet.app.persist.entity.UserAuthority;

public enum UserRole {
	USER, MARCHANT, ADMIN;

	public UserAuthority asAuthorityFor(final UserAccount user) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setUser(user);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_USER":
			return USER;
		case "ROLE_MARCHANT":
			return MARCHANT;
		case "ROLE_ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}