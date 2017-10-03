package io.gkuhn.messagebroker.model;

public class ConfirmMessageEvent {

	private int id;
	private boolean received;
	
	public ConfirmMessageEvent() {

	}
	
	public ConfirmMessageEvent(int id, boolean received) {
		this.id = id;
		this.received = received;
	}
	
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