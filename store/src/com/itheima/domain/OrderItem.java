package com.itheima.domain;

public class OrderItem {
	
	private String itemid;
	
	//购买数量
	private int count;
	//小计
	private double subtotal;
	
	private String pid;
	
	private String oid;
	
	//商品信息
	private Product product;
	
	//计算小计
	public double getSubtotal() {
		return product.getShop_price()*count;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", count=" + count + ", subtotal=" + subtotal + ", pid=" + pid + ", oid="
				+ oid + ", product=" + product + "]";
	}
}
