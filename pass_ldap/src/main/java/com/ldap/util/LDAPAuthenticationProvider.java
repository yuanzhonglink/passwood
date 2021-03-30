package com.ldap.util;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LDAPAuthenticationProvider extends LdapAuthenticationProvider {

	private final static String KEY_USERNAME = "uid";
	private final static String KEY_MAIL = "mail";
	private final static String KEY_MOBILE = "mobile";
	private final static String KEY_EMPLOYEE_NUMBER = "employeeNumber";
	private final static String KEY_NICK_NAME = "cn";

	public LDAPAuthenticationProvider(LdapAuthenticator authenticator) {
		super(authenticator);
	}

	@Override
	protected DirContextOperations doAuthentication(
		UsernamePasswordAuthenticationToken authentication) {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		return super.doAuthentication(authentication);
	}
	@Override
	protected Collection<? extends GrantedAuthority> loadUserAuthorities(
		DirContextOperations userData, String username, String password) {

		String uid = userData.getStringAttribute(KEY_USERNAME);
		String mail = userData.getStringAttribute(KEY_MAIL);
		String mobile = userData.getStringAttribute(KEY_MOBILE);
		String employeeNumber = userData.getStringAttribute(KEY_EMPLOYEE_NUMBER);
		String nickName = userData.getStringAttribute(KEY_NICK_NAME);

		List<GrantedAuthority> authorityList = new ArrayList<>();
		return authorityList;
	}
}
