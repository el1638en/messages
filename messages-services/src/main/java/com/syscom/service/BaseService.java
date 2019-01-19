package com.syscom.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class BaseService {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		return this.getMessage(key, locale, new Object[] {});
	}

	public String getMessage(String key, Locale locale) {
		return getMessage(key, locale, new Object[] {});
	}

	public String getMessage(String key, Object... args) {
		return getMessage(key, LocaleContextHolder.getLocale(), args);
	}

	public String getMessage(String key, Locale locale, Object... args) {
		return messageSource.getMessage(key, args, locale);
	}

}
