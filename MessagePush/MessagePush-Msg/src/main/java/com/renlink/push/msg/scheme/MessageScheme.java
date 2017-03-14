package com.renlink.push.msg.scheme;


public class MessageScheme extends PushScheme {

	private static final long serialVersionUID = 1L;

	private String msgType;
	private String msgContent;
	
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
}
