package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	//提交订单
	public String submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//若用户没有登录,则跳转去登录页面
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			request.setAttribute("message", "请先登录再付款");
			return "/jsp/login.jsp";
		}
		
		//获取购物车对象
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		try {
			//最终的目的: Order对象
			//////////////  需要包含List<OrderItem>
			// 上述对象都需要根据购物车对象去封装
			//1.封装Order对象
			Order order = new Order();
			////1.1设置订单对象的基本信息
			order.setOid(UUIDUtils.getId());
			order.setOrdertime(new Date());
			order.setState(Constant.UNPAY);
			
			////从session中获取user
			order.setUid(user.getUid());
			
			//2.封装所有List<OrderItem> (根据CartItem)
			Collection<CartItem> cartItems = cart.getCartItems();
			////2.1遍历所有的购物项,封装订单项
			List<OrderItem> items= new ArrayList<OrderItem>();
			for (CartItem cartItem : cartItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setCount(cartItem.getCount());
				orderItem.setItemid(UUIDUtils.getId());
				orderItem.setOid(order.getOid());
				orderItem.setPid(cartItem.getProduct().getPid());
				orderItem.setProduct(cartItem.getProduct());
				
				//将OrderItem封装到集合中
				items.add(orderItem);
			}
			
			//3.将所有的List<OrderItem> 封装到Order对象中
			order.setOrderItems(items);
			
			// 4. 调用service保存Order对象
			OrderService os = new OrderServiceImpl();
			os.save(order);
			
			//////////清空购物车////////////
			cart.clearCart();
			
			// 5. 请求转发去订单详情页面
			request.setAttribute("order", order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}
	
	//分页查询所有我的订单
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.接收参数
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize = 4;
			
			//2.获取用户的uid
			User user = (User) request.getSession().getAttribute("user");
			String uid = user.getUid();
			
			//3.调用service获取pagebean  Order
			OrderService os = new OrderServiceImpl();
			PageBean<Order> pageBean = os.findByPage(uid,pageNumber,pageSize);
			
			//4.请求转发
			request.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_list.jsp";
	}
	
	//去付款, 订单详情页面
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.接收参数
			String oid = request.getParameter("oid");
			
			//2.根据oid调用service查询Order
			OrderService os = new OrderServiceImpl();
			Order order = os.findByOid(oid);
			
			//3.请求转发
			request.setAttribute("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}
	//确认支付
	public String surePay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.接收参数
			Map<String, String[]> map = request.getParameterMap();
			//2.封装tempOrder  address oid ,name , telephone
			Order tempOrder = new Order();
			BeanUtils.populate(tempOrder, map);
			
			//3.更新订单
			OrderService os = new OrderServiceImpl();
			
			////3.1 先从数据库中查询订单
			Order order = os.findByOid(tempOrder.getOid());
			order.setAddress(tempOrder.getAddress());
			order.setName(tempOrder.getName());
			order.setTelephone(tempOrder.getTelephone());
			////3.2 address name,telephone,state
			os.update(order);
			
			//4.获取用户选择的银行
			String pd_FrpId = request.getParameter("pd_FrpId");
			
			//5.封装数据去第三方公司支付
			//https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd=Buy&p1_MerId=10001126856
			// 组织发送支付公司需要哪些数据
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = order.getOid();
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址 http://localhost/hstore/order?method=callback
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);
		
			
			//发送给第三方
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			System.out.println(sb.toString());
			//重定向跳转去支付公司
			response.sendRedirect(sb.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 支付成功之后的回调
	 * http://localhost/hstore/order?method=callback
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//支付的结果
		String r1_Code = request.getParameter("r1_Code");
		// 订单编号
		String  r6_Order = request.getParameter("r6_Order");
		
		//更新订单
		
		//提示支付成功 message页面
		request.setAttribute("message", "结果:"+r1_Code +" 订单编号:"+r6_Order);
		
		return "/jsp/message.jsp";
	}
}














