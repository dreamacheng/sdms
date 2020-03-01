package com.pro.it.common.exceptions;


import com.pro.it.common.ErrorCode;

@SuppressWarnings("serial")
public class ForbiddenException extends BaseException {

	public ForbiddenException( ErrorCode code ) {
		super( code );
	}

	public ForbiddenException( Integer code ) {
		super( code );
	}

	public ForbiddenException( Integer code, String msg ) {
		super( code, msg );
	}

}
