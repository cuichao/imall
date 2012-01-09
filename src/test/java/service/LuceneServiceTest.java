package service;

import java.util.List;

import org.junit.Test;

import com.eleven7.imall.bean.Product;
import com.eleven7.imall.dao.base.PageBean;
import com.eleven7.imall.service.ILuceneService;
import com.eleven7.imall.service.LuceneServiceImpl;

public class LuceneServiceTest {
	
	//@Test
	public void testBuildProductIndex()
	{
		ILuceneService indexService = new LuceneServiceImpl();
		//first 
		Product p = new Product();
		p.setId(1);
		p.setDescription("long desc");
		p.setShortDesc("haah ,fcsy ,bey");
		p.setName("iphone 4s");
		indexService.buildProductIndex(p);
		//second
		p = new Product();
		p.setId(2);
		p.setDescription("second: 2");
		p.setShortDesc("haah ,fsaf ,bey");
		p.setName("xiaomi");
		indexService.buildProductIndex(p);
		PageBean pb = new PageBean();
		List<Product> pList = indexService.queryProduct("fcsy",pb);
		System.out.println(pList.size()+"\t"+pb.getTotal());
		
	}

}
