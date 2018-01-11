package com.itheima.domain;

import java.util.Date;
import java.util.List;

//订单对象
public class Order {
	private String oid;
	private Date ordertime;
	private Double total=0d;
	
	private Integer state;//订单状态 0:未付款	1:已付款	2:已发货	3.已完成
	private String address;
	private String name;
	
	private String telephone;
	//用户id
	private String uid;
	
	//需要知道购买了哪些商品
	List<OrderItem> orderItems;

	//计算订单总金额
	public Double getTotal() {
		total= 0d;
		for (OrderItem orderItem : orderItems) {
			total += orderItem.getSubtotal();
		}
		
		return total;
	}
	
	
	
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", uid=" + uid + ", orderItems="
				+ orderItems + "]";
	}



	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	
	
}
