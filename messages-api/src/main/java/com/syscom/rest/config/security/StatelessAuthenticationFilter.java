package com.syscom.rest.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class StatelessAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String TOKEN_PREFIX = "Bearer";

	public StatelessAuthenticationFilter() {
		super("/api/secured/**");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	protected StatelessAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String tokenValue = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (tokenValue == null) {
			throw new PreAuthenticatedCredentialsNotFoundException(
					"No token found in request headers. Unauthorized access.");
		}
		if (!StringUtils.startsWith(tokenValue, TOKEN_PREFIX)) {
			throw new PreAuthenticatedCredentialsNotFoundException(
					"Couldn't find Bearer string, will ignore the token. Unauthorized access.");
		}
		tokenValue = StringUtils.replace(tokenValue, TOKEN_PREFIX, StringUtils.EMPTY);
		tokenValue = StringUtils.deleteWhitespace(tokenValue);
		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(tokenValue);
		return getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}

}
