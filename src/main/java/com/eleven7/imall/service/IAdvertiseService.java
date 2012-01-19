package com.eleven7.imall.service;

import java.util.List;

import com.eleven7.imall.bean.Advertise;

public interface IAdvertiseService {
	
	public void saveOrUpdate(Advertise ad);
	public List<Advertise> findAll();
	public void deleteById(Integer id);
	public void saveOrUpdateList(List<Advertise> adList);
	public List<Advertise> findByType(int type);
	

}
