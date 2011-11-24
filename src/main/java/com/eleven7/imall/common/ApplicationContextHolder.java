package com.eleven7.imall.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 支持以静态方法取出Spring中的applicationContext并对其操作.
 * 
 * @author GuoLin
 * 
 */
@SuppressWarnings("unchecked")
public class ApplicationContextHolder implements ApplicationContextAware {
	
	/** Spring应用上下文环境 */
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境.
	 * @param applicationContext Spring ApplicationContext上下文
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		ApplicationContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 获取一个ApplicationContext.
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据名称获取一个对象.
	 * @param name bean名称
	 * @return Object 指定的bean
	 * @throws BeansException 如果找不到bean
	 */
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 获取名称为name的bean，自动转为所需类型.
	 * @param <T> 需求的bean类型
	 * @param name bean名称
	 * @param requiredType 需求的bean类型
	 * @return 指定类型的bean
	 * @throws BeansException 如果找不到匹配的类型，或是类型不能被转换，或是bean实例化失败
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}
	
	/**
	 * 获取类型为requiredType的对象.
	 * @param <T> 需求的bean类型
	 * @param requiredType 需求的bean类型
	 * @return 指定类型的bean
	 * @throws BeansException 如果找不到匹配的类型
	 */
	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 检测一个bean是否已经被定义.
	 * @param name bean名称
	 * @return boolean 如果bean已经被定义，则返回true，否则返回false
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype.
	 * @param name bean名称
	 * @return boolean 如果是singleton则返回true
	 * @throws NoSuchBeanDefinitionException 如果bean名称不存在
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}
	
	/**
	 * 获取给定名字的bean的类型.
	 * @param name bean名称
	 * @return Class bean类型
	 * @throws NoSuchBeanDefinitionException 如果bean名称不存在
	 */
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 取出指定bean的别名列表.
	 * @param name bean名称
	 * @return 如果有别名，返回别名，否则返回空数组.
	 * @throws NoSuchBeanDefinitionException 如果bean名称不存在
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}

}
