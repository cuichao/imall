package com.eleven7.imall.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.common.ApplicationContextHolder;
import com.eleven7.imall.service.IUserService;

/**
 * SpringSecurity工具类.
 *
 */
public class SpringSecurityUtils {

	/**
	 * 取得当前已登陆用户.
	 * @param <T> 用户对象类型
	 * @return {@link org.springframework.security.core.userdetails.User}或其子类用户对象，
	 *         如果当前用户未登录则返回<code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public static <T extends UserDetails> T getCurrentUser() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetails) {
				return (T) principal;
			}
		}
		return null;
	}

	/**
	 * 取得当前用户的登录名.
	 * @return 如果用户已登陆返回用户名，否则返回null
	 */
	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			return authentication.getName();
		}
		return null;
	}
	public static String getCurrentNickName() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			IUserService userService = ApplicationContextHolder.getBean(IUserService.class);
		    Userinfo ui = userService.getUserbyEmail(authentication.getName());
		    if(ui != null)
		    {
		    	return ui.getNickname();
		    }
		    else
		    {
		    	return null;
		    }
		    	
		}
		return null;
	}

	/**
	 * 判断用户是否拥有角色.
	 * @param roles 角色列表
	 * @return 如果用户拥有参数中的任意一个角色则返回<code>true</code>
	 */
	public static boolean hasAnyRole(String... roles) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<GrantedAuthority> granteds = authentication.getAuthorities();
		for (String role : roles) {
			for (GrantedAuthority authority : granteds) {
				if (role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将UserDetails保存到Security Context.
	 * 
	 * @param userDetails 已初始化好的用户信息.
	 * @param request 用于获取用户IP地址信息.
	 */
	public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = 
			new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 取得Authentication.
	 * @return  如当前SecurityContext为空时返回null
	 */
	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			return context.getAuthentication();
		}
		return null;
	}
	
}
