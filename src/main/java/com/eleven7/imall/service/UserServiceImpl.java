package com.eleven7.imall.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.bean.Userinfo;
import com.eleven7.imall.dao.IAddressDao;
import com.eleven7.imall.dao.IUserinfoDao;
import com.eleven7.imall.dao.base.PageBean;
import com.eleven7.imall.dao.base.sqlcondition.LessThanCondition;

@Service
public class UserServiceImpl implements IUserService {
	
	
	@Autowired
	private IAddressDao addressDao;
	
	@Autowired
	private IUserinfoDao userinfoDao;
	
	public IAddressDao getAddressDao() {
		return addressDao;
	}

	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}
	public IUserinfoDao getUserinfoDao() {
		return userinfoDao;
	}

	public void setUserinfoDao(IUserinfoDao userinfoDao) {
		this.userinfoDao = userinfoDao;
	}

	public Userinfo getUserbyEmail(String email)
	{
		List<Userinfo> list = this.userinfoDao.findByProperty("email", email);
		if(CollectionUtils.isEmpty(list))
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
	public void saveOrUpdate(Userinfo ui)
	{
		this.userinfoDao.saveOrUpdate(ui);
	}
	
	public Userinfo getUserById(int userid)
	{
		return this.userinfoDao.get(userid);
	}
	
	public void saveOrUpdateAddress(Address address)
	{
		this.addressDao.saveOrUpdate(address);
	}
	public List<Address> listAddressByUserid(Integer userid)
	{
		PageBean pb = new PageBean();
		pb.setShowAll(true);
		pb.addDescOrder("last_access_time");
		return this.addressDao.findByProperty(pb,"userid", userid);
	}
	public Address getAddress(Integer id)
	{
		return this.addressDao.get(id);
	}
	public void deleteAddress(Integer id)
	{
		this.addressDao.delete(id);
	}
	public List<Userinfo> listUnactiveUser(Date beforeDate)
	{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		LessThanCondition con = new LessThanCondition("createtime",beforeDate);
		paramMap.put("active", Boolean.FALSE);
		paramMap.put("createtime", con);
		return this.userinfoDao.findByProperties(paramMap);
	}
	public void deleteUsers(List<Userinfo> uiList)
	{
		this.userinfoDao.deleteBatch(uiList);
	}

}
