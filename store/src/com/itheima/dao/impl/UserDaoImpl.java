package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {
/*
 * 
 * 	private String uid;
	private String username;
	private String password;

	private String name;
	private String email;
	private String telephone;

	private String birthday;//如果表单里面有和时间相关的标签,通常就用String
	private String sex;
	private int state;  //1代表已经激活  0代表没有激活

	private String code; //激活码
 * */
	@Override
	public void save(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),
				user.getCode());
	}

	@Override
	public void updateState(String code, int state) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="update user set state=? where code=?";
		qr.update(sql,state,code);
	}

	@Override
	public void clearCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="update user set code = null where code=?";
		qr.update(sql,code);
	}

	@Override //查询用户
	public User findUser(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql ="select * from user where username=? and password=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class),username,password);
		return user;
	}

}
