package com.itheima.domain;
//购物项
public class CartItem {
	//商品信息
	private Product product;
	
	//购买数量
	private int count;
	
	//小计
	private double subTotal;
	
	//计算小计
	public double getSubTotal() {
		return product.getShop_price()*count;
	}
	
	
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	

	/*public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}*/

	@Override
	public String toString() {
		return "CartItem [product=" + product + ", count=" + count + ", subTotal=" + subTotal + "]";
	}
}
