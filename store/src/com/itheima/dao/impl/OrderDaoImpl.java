package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {
/*private String oid;
	private Date ordertime;
	private Double total;
	
	private Integer state;//订单状态 0:未付款	1:已付款	2:已发货	3.已完成
	private String address;
	private String name;
	
	private String telephone;
	//用户id
	private String uid;*/
	@Override
	public void saveOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getUid()};
		qr.update(JDBCUtils.getConnection(), sql, params);
	}

	@Override
	public void saveOrderItem(OrderItem item) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql="insert into orderitem values(?,?,?,?,?)";
		Object[] params={item.getItemid(),item.getCount(),item.getSubtotal(),item.getPid(),item.getOid()};
		qr.update(JDBCUtils.getConnection(), sql, params);
	}

	@Override
	/*
	 * List<Order>
	 * 		Order ---> List<OrderItem>
	 * 						OrderItem ----> product
	 * */
	public List<Order> findByPage(String uid, int startIndex, int pageSize) throws Exception  {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from orders where uid=? limit ?,?";
		List<Order>  orders = qr.query(sql, new BeanListHandler<Order>(Order.class),uid,startIndex,pageSize);
		
		//遍历所有的订单,封装每个订单中List<OrderItem>
		for (Order order : orders) {
			//根据oid 去orderitem查询所有的订单项OrderItem
			String oid = order.getOid();
			String sql2="select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?";
			
			//获取所有的订单项和对应的商品信息
			List<Map<String, Object>> listMap = qr.query(sql2, new MapListHandler(),oid);
			
			//遍历所有的订单项listMap
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			for (Map<String, Object> map : listMap) {
				// 根据map封装OrderItem Product
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				//封装Product
				Product product = new Product();
				BeanUtils.populate(product, map);
				
				//将Product封装到OrderItem中
				orderItem.setProduct(product);
				//将查询出来的每一个订单项保存到List<OrderItem>中
				orderItems.add(orderItem);
			}
			//将所有的订单项封装到Order
			order.setOrderItems(orderItems);
		}
		return orders;
	}

	@Override
	/*
	 * Order
	 * 		List<OrderItem>
	 * 				 Product
	 * */
	public Order findByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from orders where oid=?";
		//查询Order对象
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		//封装List<OrderItem>
		String sql2="select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?";
		List<Map<String, Object>> listMap = qr.query(sql2, new MapListHandler(),oid);
		
		//循环遍历List
		List<OrderItem> items = new ArrayList<OrderItem>();
		for (Map<String, Object> map : listMap) {
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			
			Product product = new Product();
			BeanUtils.populate(product, map);
			//将product封装到OrderItem中
			orderItem.setProduct(product);
			
			items.add(orderItem);
		}
		
		//将items保存到Order中
		order.setOrderItems(items);
		return order;
	}
	
	
	@Override
	public int getTotalRecord(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="select count(*) from orders where uid=?";
		Long obj = (Long) qr.query(sql, new ScalarHandler(),uid);
		return obj.intValue();
	}

	@Test
	public void test1() throws Exception{
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String oid = "A233FCD0B44C4CC3BF566C72D5B1B237";
		String sql="select * from orderitem oi,product p where oi.pid = p.pid and oi.oid=?";
		
		/*List<OrderItem> query = qr.query(sql, new BeanListHandler<OrderItem>(OrderItem.class));
		List<Product> query2 = qr.query(sql, new BeanListHandler<Product>(Product.class));*/
		/*
		 * map<Sting,Object>  
		 * MapListHandler :将查询结果的每一条记录存放到一个Map集合中,然后将Map放到一个List
		 * */
		List<Map<String, Object>> maps = qr.query(sql, new MapListHandler(),oid);
		/*for (Map<String, Object> map : maps) {
			for (String key: map.keySet()) {
				System.out.println(key+"="+map.get(key));
			}
			
			System.out.println("===================================");
		}*/
		for (Map<String, Object> map : maps) {
			//封装OrderItem对象
			OrderItem item = new OrderItem();
			BeanUtils.populate(item, map);
			System.out.println(item);
			//封装Product对象
			Product product = new Product();
			BeanUtils.populate(product, map);
			
			//将product封装到Item中
			item.setProduct(product);
			System.out.println(product);
			
			System.out.println("===================================");
		}
	}

	
	
}
