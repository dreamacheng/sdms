package com.pro.it.common;

public final class Constants {
	private Constants() {
	}

	//请求成功
	public static final short SUCCESS = 200;

	// ==================================================================================
	// 非法文件上传错误定义

	/**
	 * 1001: 文件大小超过限制
	 */
	public static final short VIOLATE_FILE_MAX_SIZE = 1001;
	/**
	 * 1002: 文件数超过限制
	 */
	public static final short VIOLATE_FILE_MAX_NUM = 1002;
	/**
	 * 1003: 上传了受限的文件
	 */
	public static final short VIOLATE_FILE_RESTRICT_EXTENSION = 1003;

	/**
	 * 用户不存在
	 */
	public static final int ACCOUNT_NOT_EXIST = 90201;

	/**
	 * 密码错误
	 */
	public static final int PWD_ERROR = 91005;

	public static class Register {
		private Register(){}

		/**
		 * 身份证已存在
		 */
		public static final int IDENTITY_CARD_EXIST = 90101;

		/**
		 * 学号或职工号已存在
		 */
		public static final int ACCOUNT_NO_EXIST = 90102;

		/**
		 * 注册码错误或已失效
		 */
		public static final int REGISTER_CODE_INVALID = 90103;

		/**
		 * 系统不存在该角色
		 */
		public static final int ROLE_NOT_EXIST = 90104;

	}

	public static class Code {
		private Code() {}

		/**
		 * XX参数必须设置
		 */
		public static final int PARAM_REQUIRED = 90002;
		/**
		 * XX参数格式错误
		 */
		public static final int PARAM_FORMAT_ERROR = 90004;
		/**
		 * XX参数值非法
		 */
		public static final int PARAM_ILLEGAL_VALUE = 90005;

		/**
		 * 远程调用失败
		 */
		public static final int REMOTE_INVOKING_ERROR = 91111;

	}
}
