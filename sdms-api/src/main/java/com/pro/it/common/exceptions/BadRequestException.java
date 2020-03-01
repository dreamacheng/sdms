package com.pro.it.common.exceptions;

import com.pro.it.common.ErrorCode;

@SuppressWarnings("serial")
public class BadRequestException extends BaseException {
	
	public BadRequestException( ErrorCode code ) {
		super( code );
	}

	public BadRequestException( Integer code ) {
		super( code );
	}

	public BadRequestException( Integer code, String msg ) {
		super( code, msg );
	}

}
