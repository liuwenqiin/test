package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取参数method
		String methodName = request.getParameter("method");
		
		// 需求: 给定指定方法名称字符串,调用指定的方法
		/*
		 * 反射的步骤:
		 * 	1. 获取字节码对象 : 当前类
		 *  2. 根据指定名称(methodName)获取指定方法对象method
		 *  3. 调用方法
		 * */
		try {
			//1. 获取字节码对象 : 当前类
			Class clazz = this.getClass();
			//2. 根据指定名称(methodName)获取指定方法对象method
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//3. 调用方法
			String path = (String) method.invoke(this, request,response);
			
			//4. 判断是否是转发
			if(path != null){
				 request.getRequestDispatcher(path).forward(request, response); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("请检查路径是否携带method参数");
		}
	}
}
