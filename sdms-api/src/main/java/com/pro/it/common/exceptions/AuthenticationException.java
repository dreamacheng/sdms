package com.pro.it.common.exceptions;


import com.pro.it.common.ErrorCode;

@SuppressWarnings("serial")
public class AuthenticationException extends BaseException {

	public AuthenticationException( ErrorCode code ) {
		super( code );
	}

	public AuthenticationException( Integer code ) {
		super( code );
	}

	public AuthenticationException( Integer code, String msg ) {
		super( code, msg );
	}

}
