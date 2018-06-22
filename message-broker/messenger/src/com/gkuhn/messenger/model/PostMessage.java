package com.gkuhn.messenger.model;

public class PostMessage {
	
	private int from;
	private int to;
	private int userMessageId;
	private String message;
	
	public PostMessage(int from, int to, int userMessageId ,String message) {
		this.from = from;
		this.to = to;
		this.userMessageId = userMessageId;
		this.message = message;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
	
	

	public int getUserMessageId() {
		return userMessageId;
	}

	public void setUserMessageId(int userMessageId) {
		this.userMessageId = userMessageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
