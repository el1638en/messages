package com.syscom.rest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {
	
	/**
	 * Configuration de sécurité de l'API Rest secured dont l'acces est securisé
	 * par Token
	 */
	@Configuration
	@Order(1)
	public static class SecuredApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private TokenAuthenticationProvider tokenAuthenticationProvider;

		@Bean
		public AuthenticationProvider authenticationProvider() {
			return tokenAuthenticationProvider;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			StatelessAuthenticationFilter statelessAuthenticationFilter = new StatelessAuthenticationFilter();
			statelessAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
			http.anonymous().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.cors().and().csrf().disable()
					.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
					.antMatcher("/api/secured/**").authorizeRequests().anyRequest().authenticated();

		}
	}

	/**
	 * Configuration de sécurité de l'API des utilisateurs pour s'enregistrer. API
	 * ouvert intégralement.
	 */
	@Configuration
	@Order(2)
	public static class UserApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().antMatcher("/api/user/**")
					.authorizeRequests().anyRequest().anonymous();
		}

	}

	/**
	 * Configuration de sécurité de l'authentification à l'api par la méthode
	 * BASIC
	 */
	@Configuration
	public static class BasicSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthUserDetailsService userDetailsService;

		@Bean
		public DaoAuthenticationProvider daoAuthenticationProvider() {
			final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(userDetailsService);
			daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
			return daoAuthenticationProvider;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(daoAuthenticationProvider());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(
					super.authenticationManager(), new BasicAuthenticationEntryPoint());

			http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
					.disable().addFilterAt(basicAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
					.anonymous().disable().authorizeRequests().antMatchers("/api/login").permitAll();

		}
	}

}
