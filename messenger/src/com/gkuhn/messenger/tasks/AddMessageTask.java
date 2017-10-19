package com.gkuhn.messenger.tasks;

import com.gkuhn.messenger.dao.DatabaseHandlerMessage;
import com.gkuhn.messenger.model.MessageEvent;

import android.content.Context;
import android.os.AsyncTask;

public class AddMessageTask extends AsyncTask<MessageEvent, Void, MessageEvent>{
	private DatabaseHandlerMessage db;
	private Context context;
	MessageEvent taskResponse;
	
	public AddMessageTask(Context context, MessageEvent response) {
		this.context = context;
		this.taskResponse = response;
	}
	
	@Override
	protected MessageEvent doInBackground(MessageEvent ... messageEvent) {
		MessageEvent param = messageEvent[0];
		db = new DatabaseHandlerMessage(context);
		Long id = db.addMessage(param);
		MessageEvent response = db.getMessage(id);
		return response;
		
	}
	
	@Override
	protected void onPostExecute(MessageEvent message) {
		taskResponse = message;
	}

}
