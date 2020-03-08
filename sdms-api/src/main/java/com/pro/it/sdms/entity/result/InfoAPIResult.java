package com.pro.it.sdms.entity.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.pro.it.sdms.entity.APIResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class InfoAPIResult<T> extends APIResult {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T info;

	public InfoAPIResult(int code, String errMsg) {
		super(code, errMsg);
	}
	
	public InfoAPIResult(int code, T info) {
		super(code, null);
		this.info = info;
	}
	
}
