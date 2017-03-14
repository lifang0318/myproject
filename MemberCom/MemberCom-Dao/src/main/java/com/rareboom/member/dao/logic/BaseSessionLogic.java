package com.rareboom.member.dao.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;


public abstract class BaseSessionLogic {

	@Autowired
	private ApplicationContext applicationContext;

	public <T extends ApplicationEvent> void sendEvent(T event) {
		applicationContext.publishEvent(event);
	}

}
