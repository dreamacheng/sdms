package com.pro.it.sdms.entity;

import com.pro.it.common.utils.BeanUtil;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode()
public class BeanBase {
	
	@Override
	public String toString() {
		String json = null;
		try {
			json = toJson();
		}
		catch (Exception e) {
			log.debug("toJosn failed", e);
		}
		return json;
	}

	public String toJson() throws Exception {
		return BeanUtil.toJson(this);
	}
}
