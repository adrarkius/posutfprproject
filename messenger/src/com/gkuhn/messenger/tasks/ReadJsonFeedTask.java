package com.gkuhn.messenger.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gkuhn.messenger.activities.ChatRoomActivity;
import com.gkuhn.messenger.dao.DatabaseHandlerChatRoom;
import com.gkuhn.messenger.dao.DatabaseHandlerMessage;
import com.gkuhn.messenger.model.ChatRoom;
import com.gkuhn.messenger.model.ConfirmMessage;
import com.gkuhn.messenger.model.MessageEvent;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ReadJsonFeedTask extends AsyncTask
<String, Void, String> {

		private Context context;
		private List<Integer> receivedMessages = new ArrayList<Integer>();
		private List<ConfirmMessage> confirmedMessagesList = new ArrayList<ConfirmMessage>();
		private int userId;
		
		public ReadJsonFeedTask(Context context, int userId) {
			this.context = context;
			this.userId = userId;
		}
	
		
		public String readJSONFeed(String URL) {
			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			// The default value is zero, that means the timeout is not used. 
			int timeoutConnection = 5000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			
			
			String result = "";
	        StringBuilder stringBuilder = new StringBuilder();
	        HttpClient httpClient = new DefaultHttpClient(httpParameters);
	        HttpGet httpGet = new HttpGet(URL);
	        try {
	            HttpResponse response = httpClient.execute(httpGet);
	            StatusLine statusLine = response.getStatusLine();
	            int statusCode = statusLine.getStatusCode();
	            if (statusCode == 200) {
	                HttpEntity entity = response.getEntity();
	                InputStream inputStream = entity.getContent();
	                BufferedReader reader = new BufferedReader(
	                        new InputStreamReader(inputStream));
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    stringBuilder.append(line);
	                }
	                inputStream.close();
	                
	                result = stringBuilder.toString();
	            } else {
	  
	                Log.d("JSON", "Failed to download file");
	            }
	            
	        } catch (Exception e) {
	            Log.d("readJSONFeed", e.getLocalizedMessage());
	            Log.d("readJsonFeed", result);
	            
	        }        
	        return result;
	    }
		
        protected String doInBackground(String... urls ) {
            return readJSONFeed(urls[0]);
        }
        
		protected void onPostExecute(String result) {
        	if(!result.isEmpty()) {
 
        		updateMessages(result);
        		
        	} else {
        		
        		Toast.makeText(context, "Messenger: Falha na conexão com servidor", Toast.LENGTH_SHORT).show();
        	}
        	
        }
        
        @SuppressWarnings("unchecked")
		public void updateMessages(String result) {
        	DatabaseHandlerMessage db = new DatabaseHandlerMessage(context);
        	DatabaseHandlerChatRoom dbChatRoom = new DatabaseHandlerChatRoom(context);
        	try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray messageList = new 
                    JSONArray(jsonObject.getString("messages"));
                
                //---print out the content of the json feed---
                for (int i = 0; i < messageList.length(); i++) {
                    JSONObject messageItem = 
                    		messageList.getJSONObject(i);        
                   
              
	                    int messageid = messageItem.getInt("messageid");
                    
                    if(!receivedMessages.contains(messageid)) {
	                    int usermessageid = messageItem.getInt("userMessageId");
                    	String from = messageItem.getString("from");
	                    String to = messageItem.getString("to");
	                    String message = messageItem.getString("message");
	                    String senttime = messageItem.getString("senttime");
	                    String received = messageItem.getString("received");
	                    
	                    message = URLDecoder.decode(message, "UTF-8");
	                    
	                    MessageEvent messageObj = new MessageEvent(messageid, usermessageid, Integer.parseInt(from),
	                    		Integer.parseInt(to), message, senttime, Boolean.parseBoolean(received));
	                    
	                    
	                   Activity origin = (Activity) context;
	                   
	                    if(!(origin instanceof ChatRoomActivity)) {
		                    if(messageObj.get_from() != userId) {
		                    	db.addMessage(messageObj);
			                    ConfirmMessage cm = new ConfirmMessage();
			                    cm.setId(messageid);
			                    cm.setReceived(true);
			                    
			                    confirmedMessagesList.add(cm);
			                    receivedMessages.add(messageid);
		                    }
	                    }	                    
	                    //ChatRoom existChatRoom = dbChatRoom.getRoomByOwnerAndGuest(userId, messageObj.get_from());
	                    
	       
	                    	ChatRoom chatRoom = new ChatRoom((userId*10)+messageObj.get_from(), userId, messageObj.get_from(), messageObj.get_from()+": "+messageObj.get_message() );
	                    	dbChatRoom.addChatRoom(chatRoom);
	                   
                    }
           
                }
                
                ConfirmMessageTask confirmTask = new ConfirmMessageTask();
                confirmTask.execute(confirmedMessagesList);
                
            } catch (Exception e) {
                Log.d("ReadMessageFeedTask", e.getLocalizedMessage());
            } finally {
            	db.close();
            }
        }
}
