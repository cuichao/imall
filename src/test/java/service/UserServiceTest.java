package service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.service.IUserService;

public class UserServiceTest extends BaseTest{
	
	@Autowired
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	

}
