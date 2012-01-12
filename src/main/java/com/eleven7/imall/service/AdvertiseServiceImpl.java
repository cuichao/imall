package com.eleven7.imall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleven7.imall.bean.Advertise;
import com.eleven7.imall.dao.IAdvertiseDao;
@Service
public class AdvertiseServiceImpl implements IAdvertiseService {
	
	@Autowired
	private IAdvertiseDao advertiseDao;
	
	public IAdvertiseDao getAdvertiseDao() {
		return advertiseDao;
	}
	public void setAdvertiseDao(IAdvertiseDao advertiseDao) {
		this.advertiseDao = advertiseDao;
	}
	
	public void saveOrUpdate(Advertise ad)
	{
		this.advertiseDao.saveOrUpdate(ad);
	}
	public List<Advertise> findAll()
	{
		return this.advertiseDao.findAll();
	}
	public void deleteById(Integer id)
	{
		this.advertiseDao.delete(id);
	}
	public void saveOrUpdateList(List<Advertise> adList)
	{
		this.advertiseDao.saveOrUpdateAll(adList);
	}
	public List<Advertise> findByType(int type)
	{
		return this.advertiseDao.findByProperty("type", type);
	}

}
