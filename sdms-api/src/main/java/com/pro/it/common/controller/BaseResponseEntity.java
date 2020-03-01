package com.pro.it.common.controller;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseEntity<T> {

	/** 错误码 */
	private Integer code;

	/** 错误提示信息 */
	private String errMsg;

	/** 信息节点 */
	private T info;
}
