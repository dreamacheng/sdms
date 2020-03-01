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


	public static class Code {
		private Code() {
		}

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

	}
}
