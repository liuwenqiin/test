package com.itheima.service;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserService {
	void register(User user) throws Exception;

	void doActive(String code) throws SQLException;

	User doLogin(String username, String password) throws SQLException;
}
