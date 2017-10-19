package com.gkuhn.messenger.model;


public class ConfirmMessage {
	
	private int id;
	private boolean received;
	
	public ConfirmMessage(){ }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}
	
	

}
