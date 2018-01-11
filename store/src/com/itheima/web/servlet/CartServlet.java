package com.itheima.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Cart;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			///cart?method=add&pid="+pid+"&count="+count;
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
//		System.out.println(pid+"==="+count);
			
			//添加商品到购物车
			////1.获取购物车
			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			
			////2.若购物车不存在
			if(cart == null){
				cart = new Cart();
				session.setAttribute("cart", cart);
			}
			
			////3.购物车肯定存在
			cart.addProduct(pid, count);
			
			
			//跳转去购物车页面
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	//根据pid删除某个商品
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.接收参数
		String pid = request.getParameter("pid");
		
		//2.获取购物车对象
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		//3.调用购物车对象删除的方法
		cart.deleteProduct(pid);
		
		//4.重新跳转去cart.jsp
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	//清空购物车
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取购物车对象
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		//调用方法清空购物车
		cart.clearCart();
		
		//跳转去购物车页面
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
}
















