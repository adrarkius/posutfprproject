package com.gkuhn.messenger.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gkuhn.messenger.dao.DatabaseHandlerMessage;
import com.gkuhn.messenger.model.MessageEvent;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ReadMessagesByChatFromDbTask extends AsyncTask<String, Void, String>{
		  private Context context;
		  private HashMap<String, String> params;
		  private List<MessageEvent> messageEventList;
		  
		  public ReadMessagesByChatFromDbTask(Context context, ArrayList<HashMap<String,String>> messagesMapList, HashMap<String, String> map, List<MessageEvent> messageList) {
			  this.context = context;
			  this.params = map;
			  this.messageEventList = messageList;
		  }
		  protected String doInBackground(String... usersId) {
	            return usersId[0];
	      }
		  
		  @Override
		  protected void onPostExecute(String usersId){
			  DatabaseHandlerMessage db = new DatabaseHandlerMessage(context);
			  
			  try {
				  List<MessageEvent> messages = db.getChatByUserFromAndUserTo(Integer.parseInt(params.get("from")), Integer.parseInt(params.get("user")));
				  messageEventList.clear();
				  messageEventList.addAll(messages);
				  
				  } catch (Exception e) {
					  Log.d("ReadMessageByChatFromDbTask", e.getMessage());
					  
				  } finally {
					  db.close();
				  }
		  }
}

