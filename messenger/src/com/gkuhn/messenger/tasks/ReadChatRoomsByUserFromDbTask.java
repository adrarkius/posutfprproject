package com.gkuhn.messenger.tasks;

import java.util.List;

import com.gkuhn.messenger.dao.DatabaseHandlerChatRoom;
import com.gkuhn.messenger.model.ChatRoom;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
	
public class ReadChatRoomsByUserFromDbTask extends AsyncTask <String, Void, String> {
	  
	  private Context context;
	  private List<ChatRoom> chatRoomList;
	  
	  public ReadChatRoomsByUserFromDbTask(Context context, List<ChatRoom> chatRoomList) {
		  this.context = context;
		  this.chatRoomList = chatRoomList;
		  
	  }
	  protected String doInBackground(String... userId) {
		  DatabaseHandlerChatRoom db = new DatabaseHandlerChatRoom(context);
		  try {
			  List<ChatRoom> rooms = db.getRoomsByOwner(Integer.parseInt(userId[0]));
			  chatRoomList.clear();
			  chatRoomList.addAll(rooms);
		  } catch (Exception e) {
			  Log.d("ReadChatRoomsByUserFromDbTask", e.getMessage());
		  } finally {
			  db.close();
		  }
		  
		  return userId[0];
        }
	  
	  protected void onPostExecute(String userId){
		

	  }
}
