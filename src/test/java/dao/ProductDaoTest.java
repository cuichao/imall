package dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.dao.IProductDao;
import com.eleven7.imall.dao.base.PageBean;

public class ProductDaoTest extends BaseTest{
	@Autowired
	private IProductDao productDao;

	public IProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	
	
}
