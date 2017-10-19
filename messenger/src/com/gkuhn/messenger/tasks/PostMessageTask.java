package com.gkuhn.messenger.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gkuhn.messenger.dao.DatabaseHandlerMessage;
import com.gkuhn.messenger.model.MessageEvent;
import com.gkuhn.messenger.model.PostMessage;
import com.google.gson.Gson;

public class PostMessageTask extends AsyncTask<PostMessage, Void, MessageEvent>{
	private static final String URL = "http://gustavo14779.cloudapp.net:8080/message/";
	ProgressDialog progDailog;
	private Context context;
	
	public PostMessageTask(Context context) {
		this.context = context;
	}
	
	
	 @Override
     protected void onPreExecute() {
         super.onPreExecute();
         progDailog = new ProgressDialog(context);
         progDailog.setMessage("Enviando mensagem...");
         progDailog.setIndeterminate(false);
         progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         progDailog.setCancelable(true);
         progDailog.show();
     }
	 
	@Override
	protected MessageEvent doInBackground(PostMessage ...postMessages) {
		DatabaseHandlerMessage db = new DatabaseHandlerMessage(context);
		MessageEvent response = new MessageEvent();
		
		try {
		
			PostMessage post = postMessages[0];
			
			post.setMessage(URLEncoder.encode(post.getMessage(), HTTP.UTF_8));
			
			List<PostMessage> postMessageList = new ArrayList<PostMessage>();
			
			postMessageList.add(post);
			
			Gson gson = new Gson();
			
			
			String marshelledConfirmList = gson.toJson(postMessageList);
		
		
			//String encodedMarshelledConfirmList = URLEncoder.encode(marshelledConfirmList, HTTP.UTF_16);
		
			String r = postdata(marshelledConfirmList);
		
			response = confirmSentMessage(r);
		

			
			
			db.addMessage(response);
		} catch (Exception e) {
			Log.d("postdata", e.getMessage());
		} finally {
			db.close();
		}
	
		
		return response;
	}
	
	@Override
	protected void onPostExecute(MessageEvent message) {
		progDailog.dismiss();
	}
	
	protected String postdata(String json) {
		
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpPost httpPost = new HttpPost(URL);
        StringBuilder stringBuilder = new StringBuilder();
        String r = "";
        try {
        	 StringEntity stringEntity = new StringEntity(json);
             httpPost.setEntity(stringEntity);
             httpPost.setHeader("Content-type", "application/json");
             // Execute HTTP Post Request
             HttpResponse response = httpClient.execute(httpPost);
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
	       
	            
	            r = stringBuilder.toString();
	         } else {
	        	 Toast.makeText(context, "Falha ao postar mensagem", Toast.LENGTH_SHORT).show();
	         }
                
	            Log.d("JSON",r);
	       
	           
        } catch (Exception e) {
            Log.d("postdata", e.getLocalizedMessage());          
        } 
        return r;
	}
	
	protected MessageEvent confirmSentMessage(String r){
		MessageEvent messageResponse = new MessageEvent();
		
		try {
	    	JSONObject jsonObject = new JSONObject(r);
	        JSONArray sentMessagesListResponse = new 
	            JSONArray(jsonObject.getString("messages"));
	       
	        JSONObject jsonMessage = sentMessagesListResponse.getJSONObject(0);
	        
	        int usermessageid = jsonMessage.getInt("userMessageId");
	        int messageid = jsonMessage.getInt("messageid");
	        int from =  jsonMessage.getInt("from");
	        int to =  jsonMessage.getInt("to");
	        String senttime = jsonMessage.getString("senttime");
	        String message = jsonMessage.getString("message");
	        String received = jsonMessage.getString("received");
	       
	        
	        messageResponse = new MessageEvent(messageid, usermessageid, from,
            		to, message, senttime, Boolean.parseBoolean(received));
	        
	       
       } catch (JSONException ex) {
    	   Log.d("confirmSentMessage", ex.getMessage());
    	   
       }
		
		return messageResponse;
	}
}
