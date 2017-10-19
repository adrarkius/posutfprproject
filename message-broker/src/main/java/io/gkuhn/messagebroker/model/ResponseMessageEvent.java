package io.gkuhn.messagebroker.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseMessageEvent {
	
	List <MessageEvent> messages = new ArrayList<>();
	private String status;

	public ResponseMessageEvent(String status, List<MessageEvent> messages) {
		super();
		this.status = status;
		this.messages = messages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MessageEvent> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageEvent> messages) {
		this.messages = messages;
	}
}
