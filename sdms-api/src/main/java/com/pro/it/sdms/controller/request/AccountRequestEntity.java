package com.pro.it.sdms.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestEntity {

	/** 用户名 */
	private String userName;

	/** 密码 */
	private String password;
}
