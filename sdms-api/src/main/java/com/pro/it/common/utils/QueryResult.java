package com.pro.it.common.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class QueryResult<T> {
	private List<T> resultList;
	private long totalRecord;
}
