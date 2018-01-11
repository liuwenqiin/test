package com.itheima.service;

import com.itheima.domain.Order;
import com.itheima.domain.PageBean;

public interface OrderService {

	void save(Order order) throws Exception;

	PageBean<Order> findByPage(String uid, int pageNumber, int pageSize) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order);

}
