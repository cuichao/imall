package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.Address;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class AddressDaoImpl extends GenericDaoImpl<Address, Integer> implements IAddressDao{

	public AddressDaoImpl()
	{
		super(Address.class);
	}
}
