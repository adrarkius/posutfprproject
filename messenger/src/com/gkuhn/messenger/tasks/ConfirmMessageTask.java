package com.gkuhn.messenger.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.gkuhn.messenger.model.ConfirmMessage;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;

public class ConfirmMessageTask extends AsyncTask<List<ConfirmMessage>, Void, String>{
	
	
	private static final String URL = "http://gustavo14779.cloudapp.net:8080/message/confirm/";
	
	@Override
	protected String doInBackground(List<ConfirmMessage> ... lists ) {
		List<ConfirmMessage> confirmList = lists[0];
		
		Gson gson = new Gson();
		
		
		String marshelledConfirmList = gson.toJson(confirmList);
		
		postdata(marshelledConfirmList);
		
		return marshelledConfirmList;
	}
	
	@Override
	protected void onPostExecute(String json) {
	}
	
	
	protected void postdata(String json) {
		HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        StringBuilder stringBuilder = new StringBuilder();
        try {
        	 StringEntity stringEntity = new StringEntity(json);
             httpPost.setEntity(stringEntity);
             httpPost.setHeader("Content-type", "application/json");
             
             // Execute HTTP Post Request
             HttpResponse response = httpClient.execute(httpPost);

	                HttpEntity entity = response.getEntity();
	                InputStream inputStream = entity.getContent();
	                BufferedReader reader = new BufferedReader(
	                        new InputStreamReader(inputStream));
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    stringBuilder.append(line);
	                }
	                inputStream.close();
	       
	            
	            String r = stringBuilder.toString();
	            
	            Log.d("JSON",r);
        } catch (Exception e) {
            Log.d("postdata", e.getLocalizedMessage());          
        }        
	}
}
