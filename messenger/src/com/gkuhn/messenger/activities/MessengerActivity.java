package com.gkuhn.messenger.activities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.gkuhn.messenger.R;
import com.gkuhn.messenger.adapter.MessageArrayAdapter;
import com.gkuhn.messenger.model.MessageEvent;
import com.gkuhn.messenger.model.PostMessage;
import com.gkuhn.messenger.tasks.AddMessageTask;
import com.gkuhn.messenger.tasks.PostMessageTask;
import com.gkuhn.messenger.tasks.ReadJsonFeedTask;
import com.gkuhn.messenger.tasks.ReadMessagesByChatFromDbTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MessengerActivity extends Activity {
	private static final String URL = "http://gustavo14779.cloudapp.net:8080/message/user/";
	private ArrayList<HashMap<String,String>> messages = new ArrayList<HashMap<String,String>>();
	private List<MessageEvent> messageList = new ArrayList<MessageEvent>();
	private HashMap<String, String> params;
	private ListView lstView;
	private MessageArrayAdapter messageAdapter;
	private int listSize = 0;
	
	private Button sendMessageBtn;
	private EditText sendMessageTxt;
	
	ReadJsonFeedTask newTask;
	ReadMessagesByChatFromDbTask readMessageTask;
	
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

                          if(s.toString().trim().length()==0){
                        	  sendMessageBtn.setEnabled(false);
                           } else {
                        	   sendMessageBtn.setEnabled(true);
                            }
                          String from = params.get("user");
                		  String to = params.get("from");
                          
                		  if(from.equals(to)) {
                			  sendMessageBtn.setEnabled(false);
                		  }


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
	};
	
	
	private void readWebService(String user){   
		newTask = new ReadJsonFeedTask(this, Integer.parseInt(user));
		newTask.execute(URL+user); 
         
	}
	
	private void readDb(String user) {
	        readMessageTask = new ReadMessagesByChatFromDbTask(MessengerActivity.this, messages, params, messageList) {
	            @Override
	            protected void onPostExecute(String userId) {
	            	super.onPostExecute(userId);
	            	List<MessageEvent> mList = messageAdapter.getMessageEventList();
	        		if(listSize == 0) {
	        			listSize = mList.size();
	        		}
	        		
	            	mList.clear();
	            	mList.addAll(messageList);
	            	
	            	if(mList.size() > listSize) {
	            		messageAdapter.notifyDataSetChanged();
	            		listSize = mList.size();
	            	}
	            		
	            }
	            	
	     };
			readMessageTask.execute("1");
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        
        Bundle bundle = getIntent().getExtras();
        
        String user = bundle.getString("user");
        String from = bundle.getString("from");
        
        setTitle("Conversa com " + from);
        
        params = new HashMap<String,String>();
        
        params.put("user", user);
        params.put("from", from);
        
        lstView = (ListView) findViewById(R.id.lstView);
        
        
        setRepeatingReadWebServiceAsyncTask();
        setRepeatingReadDbAsyncTask();
        
        messageAdapter = new MessageArrayAdapter(getApplicationContext(), R.layout.right, Integer.parseInt(user));
        
      //to scroll the list view to bottom on data change
        messageAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                lstView.setSelection(messageAdapter.getCount() - 1);
            }
        });
        
        lstView.setAdapter(messageAdapter);
        
        sendMessageTxt = (EditText) findViewById(R.id.enter_message);
        
        sendMessageBtn = (Button) findViewById(R.id.send_button);
        
        sendMessageBtn.setEnabled(false);
        
        sendMessageTxt.addTextChangedListener(textWatcher);
        
        
        sendMessageBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		  
        		  String messageStr = sendMessageTxt.getEditableText().toString();
        		  String from = params.get("user");
        		  String to = params.get("from");
        		  
        		  MessageEvent newMessage = new MessageEvent(0, 0, Integer.parseInt(from), Integer.parseInt(to), messageStr, String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()), false);
        		  MessageEvent newMessageResponse = new MessageEvent();
        		  
        		  AddMessageTask addMessageTask = new AddMessageTask(MessengerActivity.this, newMessageResponse) {
        			  @Override 
        			  protected void onPostExecute(MessageEvent message) {
        				  super.onPostExecute(message);
        				  PostMessage postMessage = new PostMessage(message.get_from(), message.get_to(), message.get_usermessageid(), message.get_message());
        				  PostMessageTask postMessageTask = new PostMessageTask(MessengerActivity.this);
                		  postMessageTask.execute(postMessage);
        			  }
        		  };
        		  addMessageTask.execute(newMessage);
        		  
        		  Toast.makeText(MessengerActivity.this, String.valueOf(newMessageResponse.get_usermessageid()), Toast.LENGTH_LONG).show();
        		  
        		  sendMessageTxt.setText("");
        		  
        		  
        	}
        });
    }
    
    private void setRepeatingReadWebServiceAsyncTask() {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {       
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {       
                        try {
                        	String user = params.get("user");
                        	readWebService(user);
                        
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 5*1000);  // interval of one minute

    }
    
    private void setRepeatingReadDbAsyncTask() {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {       
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {       
                        try {
                        	String user = params.get("user");
                        	readDb(user);
                        
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 2*1000);  // interval of one minute

    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      	case R.id.delete:
      		getApplicationContext().deleteDatabase("instant_messeger");
      		getApplicationContext().deleteDatabase("instant_messenger");
      		this.finish();
      		break;
      }
      return true;
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

    }
    
}
