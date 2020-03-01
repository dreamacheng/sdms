package com.pro.it.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 定义错误码
 *
 * @author xiaofei.xu
 */
@AllArgsConstructor
@ToString
public enum ErrorCode {

	// ============================================================
	// 共用错误码
	// 9 - 表示共通
	// 00 - 共用
	// XX - 错误编号

	/**
	 * XX参数是必须的
	 */
	E90001( 90001, "%s paramenter is required" ),

	/**
	 * 用户会话令牌无效
	 */
	E40001( 40001, "invalid user session token" ),

	/**
	 * 用户名或者密码错误
	 */
	E40101( 40101, "invalid credentials" );


	@Getter
	private int code;

	@Getter
	private String message;
}
