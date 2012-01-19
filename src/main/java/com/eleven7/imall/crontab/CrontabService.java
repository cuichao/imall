package com.eleven7.imall.crontab;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.common.DateUtil;
import com.eleven7.imall.constant.Constant;
import com.eleven7.imall.service.IUserService;

@Service
public class CrontabService {
	
	private static Log log = LogFactory.getLog(CrontabService.class);
	@Autowired
	public IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public void deleteUnActiveUsers()
	{
		Date date = new Date();
		date.setTime(date.getTime() - Constant.MILLISECONDS_ONE_DAY);
		List<Userinfo> uiList = this.userService.listUnactiveUser(date);
		if(CollectionUtils.isNotEmpty(uiList))
		{
			this.userService.deleteUsers(uiList);
		}
		log.info("[del unactive user ]: unactive,registered before " + DateUtil.formatDate(date));
	}

}
