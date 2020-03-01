package com.pro.it.common.controller;

import com.pro.it.common.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {

	/**
	 * 处理请求参数错误异常
	 *
	 * @param ex 请求参数错误异常
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleBadRequestException( final Exception ex ) {

		// 400 Bad Request
		if ( ex instanceof BadRequestException) {

			final BadRequestException bdEx = (BadRequestException) ex;

			// info log
			log.info( "===> Result=fail, HttpStatus:400, ErrorCode:{}", bdEx.getCode() );

			return ResponseEntity
					.ok()
					.body( BaseResponseEntity.builder()
							.code( bdEx.getCode() )
							.errMsg( bdEx.getErrMsg() )
							.build() );
		}

		// 401 Unauthorized
		if ( ex instanceof AuthenticationException) {

			final AuthenticationException bdEx = (AuthenticationException) ex;

			// info log
			log.info( "===> Result=fail, HttpStatus:401, ErrorCode:{}", bdEx.getCode() );

			return ResponseEntity
					.status( HttpStatus.UNAUTHORIZED )
					.body( BaseResponseEntity.builder()
							.code( bdEx.getCode() )
							.errMsg( bdEx.getErrMsg() )
							.build() );
		}

		// 403 Forbidden
		if ( ex instanceof ForbiddenException) {

			final ForbiddenException bdEx = (ForbiddenException) ex;

			// info log
			log.info( "===> Result=fail, HttpStatus:403, ErrorCode:{}", bdEx.getCode() );

			return ResponseEntity
					.status( HttpStatus.FORBIDDEN )
					.body( BaseResponseEntity.builder()
							.code( bdEx.getCode() )
							.errMsg( bdEx.getErrMsg() )
							.build() );
		}

		// 404 Not Found
		if ( ex instanceof ResourceNotFoundException) {
			return ResponseEntity
					.status( HttpStatus.NOT_FOUND )
					.build();
		}

		// 500
		if ( ex instanceof SystemErrorException) {

			// output error log
			log.error( "Un-Excepted Error occured.", ex.getCause() );

			// info log
			log.info( "===> Result=fail, HttpStatus:500" );

			return ResponseEntity
					.status( HttpStatus.INTERNAL_SERVER_ERROR )
					.build();
		}

		// output error log
		log.error( "Un-Excepted Error occured.", ex );

		// 500 Internal Server Error
		return ResponseEntity
				.status( HttpStatus.INTERNAL_SERVER_ERROR )
				.build();
	}

}
