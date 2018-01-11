package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.service.OrderService;
import com.itheima.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override  // 声明这个方法可能会抛出异常
	public void save(Order order) throws Exception {
		try {
			// 1.开启事务
			JDBCUtils.beginTransaction();	
			
			OrderDao od = new OrderDaoImpl();
			//2.向orders表插入数据 Order
			od.saveOrder(order);
			
			//3.向OrderItem表插入数据 
			for (OrderItem item : order.getOrderItems()) {
				od.saveOrderItem(item);
			}
			//4.提交事务
			JDBCUtils.commitAndClose();
		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtils.rollbackAndClose();
			// 抛出异常
			throw new RuntimeException("数据插入有问题,失败");
		}
		
	}

	@Override  //分页查询所有订单
	public PageBean<Order> findByPage(String uid, int pageNumber, int pageSize) throws Exception {
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setPageNumber(pageNumber);
		pageBean.setPageSize(pageSize);
		
		OrderDao od = new OrderDaoImpl();
		// select xxx from xxx where uid=? limit ?,?
		List<Order> data = od.findByPage(uid,pageBean.getStartIndex(),pageSize);
		//查询当前用户所有的订单
		pageBean.setData(data);
		//查询当前用户订单总数量
		int totalRecord= od.getTotalRecord(uid);
		pageBean.setTotalRecord(totalRecord);
		
		return pageBean;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao od = new OrderDaoImpl();
		
		return od.findByOid(oid);
	}

	@Override
	public void update(Order order) {
		System.out.println(order);
	}

}
