package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

public interface ProductService {

	List<Product> findNew(int nEW_COUNT) throws SQLException;

	List<Product> findHot(int hOT_COUNT) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	PageBean findByPage(int pageNumber, int pageSize, String cid) throws SQLException;

}
