package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Product;

public interface ProductDao {

	List<Product> findNew(int count) throws SQLException;

	List<Product> findHot(int count) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	List<Product> findByCid(String cid, int startIndex, int pageSize) throws SQLException;

	int getTotalRecord(String cid) throws SQLException;

}
