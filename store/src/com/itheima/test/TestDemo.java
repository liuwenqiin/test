package com.itheima.test;

import org.junit.Test;

import com.itheima.utils.MD5Utils;

public class TestDemo {
	
	@Test
	public void test5(){
		// 向下取整, 向小值取整
		System.out.println(Math.floor(-12.9));
		// 向上取整, 向大值取整
		System.out.println(Math.ceil(-12.9));
	}
	@Test
	public void test4(){
		int i = 8;
		int j =3;
		// 2.1. 2.2
		System.out.println((int)Math.ceil(i*1.0/j));
	}
	@Test
	public void test3(){
		// 8  3 
		int i = 8;
		int j =3;
		if(i%j!=0){
			System.out.println(i/j+1);
		}else{
			System.out.println(i/j);
		}
	}
	
	
	@Test 
	public void test2(){
		// 明文
		String password="123@qq.com";
		// 加密: 变成乱码
		String encode = MD5Utils.encode(password);
		// 密文
		//不可逆:  fcea920f7412b5da7be0cf42b8c93759  --- > 1234567
		System.out.println(encode);
	}
	@Test 
	public void test1(){
		// 明文
		String password="1234567";
		
		// 加密: 变成乱码
		String encode = MD5Utils.encode(password);
		// 密文
		//不可逆:  fcea920f7412b5da7be0cf42b8c93759  --- > 1234567
		System.out.println(encode);
	}
}
