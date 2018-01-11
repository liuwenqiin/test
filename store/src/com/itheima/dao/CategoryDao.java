package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws SQLException;

	Category findbyCid(String cid) throws SQLException;

}
