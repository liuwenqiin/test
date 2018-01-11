package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override // 最新商品
	public List<Product> findNew(int count) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product order by pdate desc limit ?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),count);
	}

	@Override
	public List<Product> findHot(int count) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product where is_hot=1 limit ?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),count);
	}

	@Override  //根据pid查找商品
	public Product findByPid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product where pid = ?";
		return qr.query(sql, new BeanHandler<Product>(Product.class),pid);
	}

	@Override
	public List<Product> findByCid(String cid, int startIndex, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
	}

	@Override // 获取总记录数
	public int getTotalRecord(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select count(*) from product where cid=?";
		Long object = (Long) qr.query(sql, new ScalarHandler(),cid);
		return object.intValue();
	}

}
