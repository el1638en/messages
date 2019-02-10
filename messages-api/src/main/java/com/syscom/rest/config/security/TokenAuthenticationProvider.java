package com.syscom.rest.config.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.syscom.beans.Token;
import com.syscom.service.TokenService;
import com.syscom.utils.Fonctions;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private TokenService tokenService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String accessToken = authentication.getName();
		Token token  = tokenService.findToken(accessToken);
		if (token == null) {
			throw new BadCredentialsException("Unvalid token. Unauthorized access.");
		}
		List<GrantedAuthority> grantedAuthorities = token.getUser().getRole().getFonctions().stream()
				.map(fonction -> new SimpleGrantedAuthority(Fonctions.ROLE_PREFIX + fonction.getCode()))
				.collect(Collectors.toList());
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(accessToken, grantedAuthorities);
		jwtAuthenticationToken.setAuthenticated(true);
		return jwtAuthenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
