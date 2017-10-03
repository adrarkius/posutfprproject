package io.gkuhn.messagebroker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="messageevent")
public class MessageEvent {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="`messageid`")
	private int messageid;
	
	@Column(name="`from`")
	private int from;
	
	@Column(name="`to`")
	private int to;
	
	@Column(name="`senttime`")
	private Date senttime;
	
	@Column(name="`message`")
	private String message;
	
	@Column(name="`received`")
	private boolean received;
	
	
	
	public MessageEvent(int from, int to, Date senttime, String message, boolean received) {
		this.from = from;
		this.to = to;
		this.senttime = senttime;
		this.message = message;
		this.received = received;
	}
	
	public MessageEvent() { }
	
	
	public Integer getMessageid() {
		return messageid;
	}
	
	public void setMessageid(int i) {
		this.messageid = i;
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
	public Date getSenttime() {
		return senttime;
	}
	public void setSenttime(Date senttime) {
		this.senttime = senttime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}
	
	
}
