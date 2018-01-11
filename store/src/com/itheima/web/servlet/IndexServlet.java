package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.constant.Constant;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService ps = new ProductServiceImpl();
			
			//查询最新商品
			List<Product> newList = ps.findNew(Constant.NEW_COUNT);
			
			//查询热门商品
			List<Product> hotList = ps.findHot(Constant.HOT_COUNT);
			
			//请求转发
			request.setAttribute("newList", newList);
			request.setAttribute("hotList", hotList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}

}
