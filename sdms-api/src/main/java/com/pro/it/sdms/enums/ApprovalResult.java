package com.pro.it.sdms.enums;

import lombok.Getter;

/**
 * 审批结果枚举
 */
public enum ApprovalResult implements BaseCodeEnum {

	/**
	 * 待审核
	 */
	WaitForApproval( (short) 0),

	/**
	 * 拒绝
	 *
	 */
	Rejected( (short) 1),

	/**
	 * 批准
	 */
	Approved( (short) 2);

	private short code;

	ApprovalResult(final short code){
		this.code = code;
	}

	@Override
	public short getCode() {
		return this.code;
	}

}
