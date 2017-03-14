package com.renlink.push.msg.lang;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope
public class ServiceProperty implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${batch.size}")
	private Integer batchSize;

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	

}
