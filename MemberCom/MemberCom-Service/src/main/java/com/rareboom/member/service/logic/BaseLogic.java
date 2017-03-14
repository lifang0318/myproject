package com.rareboom.member.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

public abstract class BaseLogic {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	public<T extends ApplicationEvent> void sendEvent(T event) {
		applicationContext.publishEvent(event);
	}
}
