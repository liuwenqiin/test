package com.itheima.domain;

import java.util.List;
// 泛型, T看成是占位位
public class PageBean<T> {
	//分页需要显示的数据
	private List<T> data;
	
	//当前页码
	private int pageNumber;
	
	//每页显示的数量
	private int pageSize;
	
	//总页码
	//private int totalPage;
	
	// 总记录数
	private int totalRecord;
	
	//计算总页码 totalRecord/pageSize  8/3 3
	public int getTotalPage(){  //属性 : totalPage  内省
		return (int)Math.ceil(totalRecord*1.0/pageSize);
	}
	
	// 计算起始索引的方法
	public int getStartIndex(){
		return (pageNumber-1)*pageSize ;
	}
	
	
	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() { 
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}


	@Override
	public String toString() {
		return "PageBean [data=" + data + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalPage="
				 + ", totalRecord=" + totalRecord + "]";
	}
}
