package com.entity;

import java.util.List;

public class Page<T> {
	private int pageCount;//总页数
	private int pageIndex;//当前页
	private int pageSize;//每页的结果数
	private int totalSize;//总结果数
	private List<T> listResult;//每页的结果集
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		if(pageSize!=0){
			this.pageCount = totalSize%pageSize==0?(totalSize/pageSize==0?1:totalSize/pageSize):totalSize/pageSize+1;
		}
	}
	public List<T> getListResult() {
		return listResult;
	}
	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}
	
	
}
