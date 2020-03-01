package com.pro.it.sdms.entity;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class APIResult<T> extends BeanBase {
	private int code;
	private String errMsg;
	private T info;

	public APIResult(int code, T into) {
		this.code = code;
		this.info = into;
	}

}
