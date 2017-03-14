package com.rareboom.member.scheme;

import java.io.Serializable;

import com.rd.jbasis.core.utils.JsonUtils;

public class BaseScheme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toJson() {
		return JsonUtils.toJSONString(this);
	}

}
