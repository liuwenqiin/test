package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

public interface OrderDao {

	void saveOrder(Order order) throws SQLException;

	void saveOrderItem(OrderItem item) throws SQLException;

	List<Order> findByPage(String uid, int startIndex, int pageSize) throws Exception;

	int getTotalRecord(String uid) throws SQLException;

	Order findByOid(String oid) throws Exception;

}
