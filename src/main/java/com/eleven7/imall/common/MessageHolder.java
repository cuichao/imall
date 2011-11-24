package com.eleven7.imall.common;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * 国际化静态工具类.
 * <p>
 * 用于简化对{@link MessageSource}的调用.
 * 解决每次在Controller中调用MessageSource必须注入{@link MessageSource}的麻烦,
 * 同时也可以提供给非HTTP请求或者不经过Controller的类（比如VO或工具类等）使用.
 * <p>
 * 
 * @author GuoLin
 *
 */
public class MessageHolder {
	
	/** 存储Locale对象的本地线程变量. */
	private static ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

	/** Spring的MessageSource. */
	private static MessageSource messageSource;
	
	/**
	 * 获取指定标识的国际化消息，并将参数注入进去.
	 * <p>
	 * 参数注入方式依据于{@link java.text.MessageFormat}
	 * </p>
	 * @param code 国际化消息标识
	 * @param arguments 参数
	 * @return 完成后的国际化消息
	 * @see java.text.MessageFormat
	 */
	public static String get(String code, Object... arguments) {
		Locale locale = MessageHolder.locale.get();
		return messageSource.getMessage(code, arguments, (locale != null) ? locale : Locale.getDefault());
	}
	
	/**
	 * 设置Locale.
	 * <p>
	 * 设置的locale将存储到ThreadLocal中供线程调用.
	 * </p>
	 * @param locale 需设定的Locale对象
	 */
	public static void setLocale(Locale locale) {
		MessageHolder.locale.set(locale);
	}

	/**
	 * 设置消息源.
	 * <p>
	 * 一般而言应该注入{@link org.springframework.context.support.ResourceBundleMessageSource}
	 * </p>
	 * @param messageSource 消息源
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		MessageHolder.messageSource = messageSource;
	}
	
}
