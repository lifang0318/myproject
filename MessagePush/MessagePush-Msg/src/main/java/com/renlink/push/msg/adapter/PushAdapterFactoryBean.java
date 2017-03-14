package com.renlink.push.msg.adapter;

import org.springframework.beans.factory.FactoryBean;


public class PushAdapterFactoryBean implements FactoryBean<IPushAdapter> {

	private IPushAdapter pushAdapter;

	public IPushAdapter getPushAdapter() {
		return pushAdapter;
	}

	public void setPushAdapter(IPushAdapter pushAdapter) {
		this.pushAdapter = pushAdapter;
	}

	@Override
	public IPushAdapter getObject() throws Exception {
		return this.pushAdapter;
	}

	@Override
	public Class<?> getObjectType() {
		return (this.pushAdapter != null ? this.pushAdapter.getClass() : IPushAdapter.class);
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
