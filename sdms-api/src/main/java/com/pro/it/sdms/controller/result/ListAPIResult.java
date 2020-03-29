package com.pro.it.sdms.controller.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ListAPIResult<T> extends APIResult {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<T> list;

	public ListAPIResult(int code, String errMsg) {
		super(code, errMsg);
	}
	
	public ListAPIResult(int code, List<T> list) {
		super(code, null);
		this.list = list;
	}


}
