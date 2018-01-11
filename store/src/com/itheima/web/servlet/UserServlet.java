package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.UUIDUtils;

/**
 * 用户模块
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	//注册业务
	public String register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1.接收参数
			Map<String, String[]> map = request.getParameterMap();
			
			// 2.封装User对象
			User user = new User();
			BeanUtils.populate(user, map);
			
			//3. 封装user没有的参数
			user.setUid(UUIDUtils.getId());
			// 设置未激活的状态
			user.setState(Constant.UNACTIVED);
			// 设置激活码
			user.setCode(UUIDUtils.getCode());
			
			// 2. 调用service处理业务
			UserService us = new UserServiceImpl();
			us.register(user);
			
			// 3. 输出结果: 重定向/请求转发 msg.jsp
			request.setAttribute("message", "恭喜您注册成功,请登录您的邮箱去完成激活操作");
			// 转发
			return "/jsp/message.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "注册失败,请检查您输入的信息"+e.getMessage());
			return "/jsp/message.jsp";
		}
	}
	//激活
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1.接收参数
			String code = request.getParameter("code");
			// 2. 调用service处理业务
			UserService us = new UserServiceImpl();
			us.doActive(code);
			// 3. 输出结果: 重定向/请求转发
			// 重定向
			//System.out.println("执行激活业务逻辑");
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//登录
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("执行登录业务逻辑");
			// 1.接收参数
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			// 2. 调用service处理业务
			UserService us = new UserServiceImpl();
			User user = us.doLogin(username,password);
			
			//3. 判断是否查到
			if(user != null){
				//保持用户登录状态
				request.getSession().setAttribute("user", user);	
				//登录成功:重定向去首页
				response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
				return null;
			}else{
				//登录失败:携带错误信息重新回到登录页面 转发
				request.setAttribute("message", "用户名或密码错误!");
				return "/jsp/login.jsp";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//用户注销,退出
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//销毁当前session,或者从当前session中移除user
		request.getSession().invalidate();
		
		// 清除自动登录的cookie
		
		
		//重定向去首页
		response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		return null;
	}
	
	
	
}
