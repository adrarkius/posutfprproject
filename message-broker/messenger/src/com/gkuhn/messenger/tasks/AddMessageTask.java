package com.gkuhn.messenger.tasks;

import com.gkuhn.messenger.dao.DatabaseHandlerChatRoom;
import com.gkuhn.messenger.dao.DatabaseHandlerMessage;
import com.gkuhn.messenger.model.ChatRoom;
import com.gkuhn.messenger.model.MessageEvent;

import android.content.Context;
import android.os.AsyncTask;

public class AddMessageTask extends AsyncTask<MessageEvent, Void, MessageEvent>{
	private DatabaseHandlerMessage dbMessage;
	private DatabaseHandlerChatRoom dbChat;
	private Context context;
	MessageEvent taskResponse;
	
	public AddMessageTask(Context context, MessageEvent response) {
		this.context = context;
		this.taskResponse = response;
	}
	
	@Override
	protected MessageEvent doInBackground(MessageEvent ... messageEvent) {
		MessageEvent param = messageEvent[0];
		dbMessage = new DatabaseHandlerMessage(context);
		Long id = dbMessage.addMessage(param);
		MessageEvent response = dbMessage.getMessage(id);
		
		
		dbChat = new DatabaseHandlerChatRoom(context);
		ChatRoom chatRoom = new ChatRoom((param.get_from()*10)+param.get_to(), param.get_from(), param.get_to(), param.get_from()+": "+param.get_message() );
		dbChat.addChatRoom(chatRoom);
		return response;
		
	}
	
	@Override
	protected void onPostExecute(MessageEvent message) {
		taskResponse = message;
	}

}
