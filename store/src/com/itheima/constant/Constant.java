package com.itheima.constant;

public class Constant {
	
	//1代表已经激活  0代表没有激活
	public static int ACTIVED = 1;
	public static int UNACTIVED = 0;
	
	public static String SALT="a#$@*";
	
	public static String ALL_CATEGORY="ALL_CATEGORY";
	
	//定义最新商品显示数量
	public static int NEW_COUNT = 9;
	//定义热门商品显示数量
	public static int HOT_COUNT = 9;
	
	//订单状态 0:未付款	1:已付款	2:已发货	3.已完成
	// 0:未付款
	public static int UNPAY = 0;
	// 1:已付款
	public static int PAIED = 1;
	// 2:已发货
	public static int SENT = 1;
	// 3.已完成  
	public static int COMPLETED = 1;
	
}
