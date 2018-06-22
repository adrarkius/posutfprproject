package com.gkuhn.messenger.model;

public class ChatRoom {
	
	private int _roomId;
	private int _ownerUser;
	private int _guestUser;
	private String _lastMessage;
	
	public ChatRoom (int roomId, int owner, int guest, String lastMessage) {
		this._roomId = roomId;
		this._ownerUser = owner;
		this._guestUser = guest;
		this._lastMessage = lastMessage;
	}

	public int get_OwnerUser() {
		return _ownerUser;
	}

	public void set_OwnerUser(int ownerUser) {
		this._ownerUser = ownerUser;
	}

	public int get_GuestUser() {
		return _guestUser;
	}

	public void set_GuestUser(int guestUser) {
		this._guestUser = guestUser;
	}

	public String get_LastMessage() {
		return _lastMessage;
	}

	public void set_LastMessage(String lastMessage) {
		this._lastMessage = lastMessage;
	}

	public int get_roomId() {
		return _roomId;
	}

	public void set_roomId(int _roomId) {
		this._roomId = _roomId;
	}
	
	
	
	

}
