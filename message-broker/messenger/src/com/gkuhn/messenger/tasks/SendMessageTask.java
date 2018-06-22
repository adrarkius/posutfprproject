package com.gkuhn.messenger.tasks;

import java.util.ArrayList;
import java.util.List;

import com.gkuhn.messenger.dao.DatabaseHandlerMessage;
import com.gkuhn.messenger.model.MessageEvent;
import com.gkuhn.messenger.model.PostMessage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendMessageTask extends AsyncTask<Void, Void, List<PostMessage>>{
	private Context context;
	
	public SendMessageTask (Context context) {
		this.context = context;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	protected List<PostMessage> doInBackground(Void... params) {
		DatabaseHandlerMessage db = new DatabaseHandlerMessage(context);
		List<PostMessage> postList = new ArrayList<PostMessage>();
		try {
			List<MessageEvent> notSentMessagesList = db.getMessagesByMessageId(0);
			
			if((notSentMessagesList != null) && (notSentMessagesList.size() > 0)) {
				for(MessageEvent messageElement : notSentMessagesList) {
					 
					  PostMessage postMessage = new PostMessage(messageElement.get_from(), messageElement.get_to(), messageElement.get_usermessageid(), messageElement.get_message());
					  postList.add(postMessage);
	       		      
				}
			}
			
			if(postList.size() > 0) {	
				PostMessageTask postMessageTask = new PostMessageTask(context);
				postMessageTask.execute(postList);
			}
		} catch (Exception e) {
			Log.d("SendMessageTask", e.getMessage());
		} finally {
			db.close();
		}
		
		return postList;
	}
	
	@Override
	protected void onPostExecute(List<PostMessage> postList) {
		
		
	}
	
	

}
