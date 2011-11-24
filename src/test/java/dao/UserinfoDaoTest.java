package dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.dao.IUserinfoDao;

public class UserinfoDaoTest extends BaseTest{
	
	@Autowired
	private IUserinfoDao userinfoDao;

	@Test
	public void testSaveUser()
	{
		Userinfo ui = new Userinfo();
		ui.setNickname("崔超");
		this.userinfoDao.save(ui);
	}

}
