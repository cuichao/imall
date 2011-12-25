package com.eleven7.imall.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.common.CommonConfig;
import com.eleven7.imall.service.IUserService;

public class UserDetailsServiceImpl implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		Userinfo db_user = this.userService.getUserbyEmail(userName);
		if (db_user == null) {
			throw new UsernameNotFoundException(userName + " is not exist");
		}
		List<GrantedAuthority> authList = getDefaultAuthorities();
		if (CommonConfig.isAdmin(userName)) {
			authList = getAdminAuthorities();
		}
		User user = new User(userName, db_user.getPassword(),
				db_user.isActive(), true, true, true, authList);
		return user;

	}

	/**
	 * 获取默认权限组.
	 * 
	 * @return
	 */
	protected static List<GrantedAuthority> getDefaultAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return authorities;
	}

	/**
	 * admin
	 * 
	 * @return
	 */
	protected static List<GrantedAuthority> getAdminAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ADMIN_USER"));
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return authorities;
	}

}
