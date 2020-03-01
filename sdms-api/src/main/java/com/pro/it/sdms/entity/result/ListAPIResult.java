package com.pro.it.sdms.entity.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pro.it.sdms.entity.APIResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
