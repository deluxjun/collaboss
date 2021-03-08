package com.jj.sbp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * 
 * @author deluxjun
 *
 */
@Component
@Slf4j
public class Context {

	// Singleton instance
	private static Context instance;

	// The Spring's application context
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier("messages")
	private MessageSource messageSource;

	@PostConstruct
	public void registerInstance() {
		instance = this;
	}


	public static <T> T getBean(Class<T> clazz) {
		return instance.applicationContext.getBean(clazz);
	}


	// ================================================================
	// messages 
	
	public static String getMessage(String code, Object[] args, Locale locale) {
//		if (instance.messageSource == null)
//			instance.messageSource = instance.getBean(MessageSource.class);

		if (code == null)
			return "";
		try {
			return instance.messageSource.getMessage(code, args, locale);
//			return instance.applicationContext.getMessage(code, args, locale);
		} catch (NoSuchMessageException e) {
			log.warn(e.getMessage());
			return code;
		}
	}

	public static String getMessage(String code, Locale locale) {
		return getMessage(code, null, locale);
	}

	public static String getMessage(String key) {
		return getMessage(key, null, LocaleContextHolder.getLocale());
	}

	public static String getMessage(String code, String lang) {
		if (lang == null || lang.length() < 1)
			return getMessage(code);

		return getMessage(code, null, new Locale(lang));
	}
	
	public static String getMessage(String key, Object[] values, String lang) {
		if (lang == null || lang.length() < 1)
			return getMessage(key, values, LocaleContextHolder.getLocale());
		return getMessage(key, values, new Locale(lang));
	}
}
