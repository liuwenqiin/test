package com.itheima.domain;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/*
 * 保存所有的购物项  map<pid,CartItem>
 * 购物车总金额
 * 
 * 方法:
 * 	  添加
 * 	 删除 
 *   清空
 */
public class Cart {
	//保存所有的购物项
	private Map<String,CartItem> maps = new HashMap<String,CartItem>();
	
	// 总金额     //字段
	private double total;

	//提供一个方法, 便于jsp页面获取所有的cartItem
	public Collection<CartItem> getCartItems(){  //  cartItems
		return maps.values();
	}
	
	// 提供gettotal的方法
	public double getTotal(){
		total = 0;
		//计算总金额
		for (CartItem item : getCartItems()) {
			total += item.getSubTotal();
		}
		return total;
	}
	
	
	//添加商品的方法
	public void addProduct(String pid,int count) throws SQLException{
		//判断是否已经存在
		//若有
		if(maps.containsKey(pid)){
			//只需要添加数量
			CartItem cartItem = maps.get(pid);
			int newCount = cartItem.getCount()+count;
			//保存最新的数量
			cartItem.setCount(newCount);
		}else{
			//若无
			//1.构建CartItem对象
			CartItem cartItem = new CartItem();
			cartItem.setCount(count);
			
			//根据pid去查询一个product对象出来
			ProductService ps = new ProductServiceImpl();
			Product product = ps.findByPid(pid);
			cartItem.setProduct(product);
			
			//保存到map中
			maps.put(pid, cartItem);
		}
	}
	
	//删除的方法
	public void deleteProduct(String pid){
		maps.remove(pid);
	}
	
	//清空购物车的方法
	public void clearCart(){
		maps.clear();
	}
}
