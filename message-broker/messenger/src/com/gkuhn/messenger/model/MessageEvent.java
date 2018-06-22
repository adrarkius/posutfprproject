package com.gkuhn.messenger.model;

public class MessageEvent {
	
	private int _messageid;
	private int _usermessageid;
	private int _from;
	private int _to;
	private String _message;
	private String _senttime;
	private boolean _received;
	
	
	public MessageEvent() {
		
	}
	
	public MessageEvent(int _messageid, int _usermessageid, int _from, int _to, String _message,
			String _senttime, boolean _received) {
		super();
		this._messageid = _messageid;
		this._usermessageid = _usermessageid;
		this._from = _from;
		this._to = _to;
		this._message = _message;
		this._senttime = _senttime;
		this._received = _received;
	}

	public int get_messageid() {
		return _messageid;
	}

	public void set_messageid(int _messageid) {
		this._messageid = _messageid;
	}
	
	public int get_usermessageid() {
		return _usermessageid;
	}

	public void set_usermessageid(int _usermessageid) {
		this._usermessageid = _usermessageid;
	}

	public int get_from() {
		return _from;
	}

	public void set_from(int _from) {
		this._from = _from;
	}

	public int get_to() {
		return _to;
	}

	public void set_to(int _to) {
		this._to = _to;
	}

	public String get_message() {
		return _message;
	}

	public void set_message(String _message) {
		this._message = _message;
	}

	public String get_senttime() {
		return _senttime;
	}

	public void set_senttime(String _senttime) {
		this._senttime = _senttime;
	}

	public boolean is_received() {
		return _received;
	}

	public void set_received(boolean _received) {
		this._received = _received;
	}



	
	
	
	

}
