package com.pro.it.common.exceptions;


import com.pro.it.common.ErrorCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("serial")
@ToString
public class BaseException extends RuntimeException {

	@Getter
	private ErrorCode errorCode;

	@Getter
	private final Integer code;

	@Getter
	private final String errMsg;

	public BaseException(ErrorCode errorCode ) {
		this.errorCode = errorCode;

		this.code = errorCode.getCode();
		this.errMsg = errorCode.getMessage();
	}

	public BaseException(Throwable ex ) {
		super( ex );

		this.code = null;
		this.errMsg = null;
	}

	public BaseException(final Integer code, final String errMsg ) {
		this.code = code;
		this.errMsg = errMsg;
	}

	public BaseException(final Integer code ) {
		this.code = code;
		this.errMsg = null;
	}

}
