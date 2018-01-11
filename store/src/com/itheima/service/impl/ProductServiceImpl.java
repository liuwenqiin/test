package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findNew(int count) throws SQLException {
		ProductDao pd = new ProductDaoImpl();
		return pd.findNew(count);
	}

	@Override
	public List<Product> findHot(int count) throws SQLException {
		ProductDao pd = new ProductDaoImpl();
		return pd.findHot(count);
	}

	
	@Override 
	public Product findByPid(String pid) throws SQLException {
		ProductDao pd = new ProductDaoImpl();
		Product product = pd.findByPid(pid);
		
		//根据cid查询分类信息
		CategoryDao cd = new CategoryDaoImpl();
		Category category = cd.findbyCid(product.getCid());
		
		product.setCategory(category);
		
		return product;
	}

	@Override //分页查询
	public PageBean findByPage(int pageNumber, int pageSize, String cid) throws SQLException {
		PageBean<Product> pageBean = new PageBean();
		pageBean.setPageNumber(pageNumber);
		pageBean.setPageSize(pageSize);

		ProductDao pd = new ProductDaoImpl();
		
		//设置分页数据 select * from product where cid=? limit ?,?;
		List<Product> data = pd.findByCid(cid,pageBean.getStartIndex(),pageSize);
		pageBean.setData(data);
		
		//设置总记录数 select count(*) from product where cid=? 
		int totalRecord = pd.getTotalRecord(cid);
		pageBean.setTotalRecord(totalRecord);
		
		return pageBean;
	}

}
