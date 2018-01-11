package com.itheima.service.impl;

import java.sql.SQLException;

import com.itheima.constant.Constant;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService {

	@Override
	public void register(User user) throws Exception {
		
		// 调用UserDao
		UserDao ud = new UserDaoImpl();
		// 123
		user.setPassword(MD5Utils.encode(user.getPassword(),Constant.SALT));
		//保存信息 
		ud.save(user);
		
		//发送激活邮件
		MailUtils.sendMail(user.getEmail(), "来自黑马商城的激活邮件",
				"恭喜您,注册成功,请点击如下连接完成激活<a href='http://localhost/hstore/user?method=active&code="+user.getCode()+"'>点我激活</a>"
				+"如果点击无效,请复制:http://localhost/hstore/user?method=active&code="+user.getCode());
	}

	@Override // 修改状态, 清空原来的激活码
	public void doActive(String code) throws SQLException {
		// 调用UserDao
		UserDao ud = new UserDaoImpl();
		// 更新激活状态
		ud.updateState(code,Constant.ACTIVED);
		
		// 清空激活码
		ud.clearCode(code);
	}

	@Override
	public User doLogin(String username, String password) throws SQLException {
		
		// 调用UserDao
		UserDao ud = new UserDaoImpl();
		
		//md5加密
		password = MD5Utils.encode(password,Constant.SALT);
		
		// 查询
		return ud.findUser(username,password);
	}

}
