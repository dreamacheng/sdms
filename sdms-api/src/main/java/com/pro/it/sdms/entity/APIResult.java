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

	public APIResult(int code) {
		this.code = code;
	}

}
