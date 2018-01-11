package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public String findAll() throws SQLException {
		
		/*
		 * 1. 去redis看有没有缓存
		 * 2. 若有
		 * 	   直接取,并且返回		
		 * 3. 若无
		 *   去MYSQL中查询
		 *   转成json字符串
		 *   保存到Redis中
		 * */
		Jedis jedis = null;
		try {
			//1. 去redis看有没有缓存
			jedis = JedisUtils.getJedis();
			String value = jedis.get(Constant.ALL_CATEGORY);
			
			// 2. 若有
			if(value != null){
				System.out.println("redis中有数据");
				return value;
			}
			// 3.若无
				// 3.1 去MYSQL中查询
				//调用dao查询所有分类
				List<Category> list = getCategoryFromMysql();
				
				//将list转成json字符串
				value = new Gson().toJson(list);
				//将查询出的数据保存到Redis中
				jedis.set(Constant.ALL_CATEGORY, value);
				System.out.println("redis中没有,从mysql中查询");
			return value;
		} catch (Exception e) {
			// 证明redis没有开启,连接出错
			e.printStackTrace();
			List<Category> list = getCategoryFromMysql();
			//将list转成json字符串
			String value = new Gson().toJson(list);
			return value;
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
	}
	
	private List<Category> getCategoryFromMysql() throws SQLException{
		// 3.1 去MYSQL中查询
		//调用dao查询所有分类
		CategoryDao cd = new CategoryDaoImpl();
		List<Category> list = cd.findAll();
		return list;
	}
}
