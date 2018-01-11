package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserDao {

	void save(User user) throws SQLException;

	void updateState(String code, int aCTIVED) throws SQLException;

	void clearCode(String code) throws SQLException;

	User findUser(String username, String password) throws SQLException;

}
