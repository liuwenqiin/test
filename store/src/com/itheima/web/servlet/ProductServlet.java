package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/**
 * 商品模块: 添加商品, 查询商品, 修改商品
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.接收参数
			String pid = request.getParameter("pid");
			
			//2.调用Service查询商品
			ProductService ps = new ProductServiceImpl();
			Product product = ps.findByPid(pid);
			
			//3. 转发
			request.setAttribute("p", product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/product_info.jsp";
	}
	
	//分页查询分类商品
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.接收参数
			String cid = request.getParameter("cid");
			//当前页码
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			
			//定义每页显示的数量
			int pageSize = 12;
			
			//2.调用service获取pageBean
			ProductService ps = new ProductServiceImpl();
			
			PageBean pageBean = ps.findByPage(pageNumber,pageSize,cid);
			
			//3.转发
			request.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/product_list.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
