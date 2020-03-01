package com.pro.it.sdms.enums;

import lombok.Getter;

/**
 * 审批结果枚举
 */
public enum ApprovalResult implements BaseCodeEnum {

	/**
	 * 待审核
	 */
	WaitForApproval( (short) 0, "rejected" ),

	/**
	 * 拒绝
	 */
	Rejected( (short) 1, "rejected" ),

	/**
	 * 批准
	 */
	Approved( (short) 2, "approved" );

	private short code;

	ApprovalResult(final short code,final String result){
		this.code = code;
		this.result = result;
	}

	@Getter
	private String result;

	@Override
	public short getCode() {
		return this.code;
	}

}
