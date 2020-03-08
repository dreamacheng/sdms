package com.pro.it.common.utils;

import lombok.ToString;

import java.util.List;

@ToString
public class QueryResult<T> {
	private List<T> resultlist;
	private long totalrecord;
	
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
	
}
