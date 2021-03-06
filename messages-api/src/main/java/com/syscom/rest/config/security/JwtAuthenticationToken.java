package com.syscom.rest.config.security;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;

	public JwtAuthenticationToken(String token) {
		super(null);
		this.token = token;
	}

	public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	public JwtAuthenticationToken(String token, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = token;
	}

	@Override
	public Object getCredentials() {
		return StringUtils.EMPTY;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
