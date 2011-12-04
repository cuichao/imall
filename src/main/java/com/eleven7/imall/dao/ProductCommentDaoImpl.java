package com.eleven7.imall.dao;

import org.springframework.stereotype.Repository;

import com.eleven7.imall.bean.ProductComment;
import com.eleven7.imall.dao.base.GenericDaoImpl;

@Repository
public class ProductCommentDaoImpl extends GenericDaoImpl<ProductComment, Integer> implements IProductCommentDao {

	public ProductCommentDaoImpl()
	{
		super(ProductComment.class);
	}
}
