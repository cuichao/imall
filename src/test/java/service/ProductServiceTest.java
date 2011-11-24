package service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.dao.base.PageBean;
import com.eleven7.imall.service.IProductService;

public class ProductServiceTest extends BaseTest{
	
	@Autowired
	private IProductService productService;
	
//	@Test
//	public void testSave()
//	{
//		Product t = new Product();
//		t.setDescription("desc");
//		t.setName("iphone4s");
//		t.setPrice(123.0);
//		this.productService.saveProduct(t);
//	}
//	@Test
//	public void testGet()
//	{
//		PageBean pb = new PageBean();
//		pb.setPage(1);
//		pb.setSize(PageBean.MIN_PAGESIZE);
//		pb.addDescOrder("id");
//		List<Product> list = this.productService.getList(pb);
//		System.out.println("=============="+ list.get(0));
//	}
	
	

}
