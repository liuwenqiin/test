package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取所有分类信息
			CategoryService cs = new CategoryServiceImpl();
			String jsonArr = cs.findAll();
			
			//输出jsonArr字符串
			response.getWriter().print(jsonArr);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
