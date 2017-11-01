package com.gkuhn.messenger.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.gkuhn.messenger.R;
import com.gkuhn.messenger.adapter.ChatRoomArrayAdapter;
import com.gkuhn.messenger.dialog.NewRoomDialog;
import com.gkuhn.messenger.model.ChatRoom;
import com.gkuhn.messenger.tasks.ReadChatRoomsByUserFromDbTask;
import com.gkuhn.messenger.tasks.ReadJsonFeedTask;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatRoomActivity extends Activity implements NewRoomDialog.Communicator{

	private static final String URL = "http://gustavo14779.cloudapp.net:8080/message/user/";
	private List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
	
	private ListView lstView;
	private ChatRoomArrayAdapter chatRoomArrayAdapter;
	
	Timer timerReadDb;
	Timer timerReadWebService;
	
	
	ImageButton newChatRoomBtn;
	
	private String userId = "";
	
	
	ReadJsonFeedTask newTask;
	ReadChatRoomsByUserFromDbTask readMessageTask;
	
	int listSize = 0;
	
	private void callMessengeActivity(String guestUser) {
		Intent intent = new Intent(ChatRoomActivity.this, MessengerActivity.class);
		
		intent.putExtra("from", guestUser);
		intent.putExtra("user", userId);
		
		startActivity(intent);
		overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
	}
	
	private OnItemClickListener lstViewClickListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			TextView v = (TextView) view.findViewById(R.id.from);
			callMessengeActivity(v.getText().toString());
		}
	};
	
	private void updateViewChatRoom() {
		
		readMessageTask = new ReadChatRoomsByUserFromDbTask(ChatRoomActivity.this, chatRoomList) {
            @Override
            protected void onPostExecute(String userId) {
            	super.onPostExecute(userId);
            	
            		chatRoomArrayAdapter.getChatRoomList().clear();
            		chatRoomArrayAdapter.getChatRoomList().addAll(chatRoomList);
            		chatRoomArrayAdapter.notifyDataSetChanged();
            		
            	
            }
        };
		readMessageTask.execute(userId);
	     
	}
	
	@Override
	public void onDialogMessage(String message) {
		callMessengeActivity(message);
	}
	
	private void readWebServiceContent() {
		
		  newTask = new ReadJsonFeedTask(this, Integer.parseInt(userId)) {
		      	@Override	
		      	protected void onPostExecute(String result) {
		              super.onPostExecute(result);
		          }
		     };
	     newTask.execute(URL+userId);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Bundle bundle = getIntent().getExtras();
        
        userId = bundle.getString("value");
        
        TextView tv = (TextView) findViewById(R.id.userId);
        
        tv.setText("Usuário: "+userId);
        
        lstView = (ListView) findViewById(R.id.lstView);
        
        lstView.setOnItemClickListener(lstViewClickListener);
       
        
        setRepeatingReadWebServiceAsyncTask();
        setRepeatingReadDbAsyncTask();
        
        chatRoomArrayAdapter = new ChatRoomArrayAdapter(getApplicationContext(), R.layout.list_item_chatrooms, Integer.parseInt(userId));
        
        lstView.setAdapter(chatRoomArrayAdapter);
        
        newChatRoomBtn = (ImageButton) findViewById(R.id.addChatRoomBtn);
        
        newChatRoomBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(v);
				
			}
		});
     
	}
	
	private void showDialog(View v) {
		FragmentManager manager = getFragmentManager();
		NewRoomDialog newRoomDialog = new NewRoomDialog();
		newRoomDialog.show(manager, "New Room");
	}
	
	private void setRepeatingReadWebServiceAsyncTask() {

        final Handler handler = new Handler();
        timerReadWebService = new Timer();

        TimerTask task = new TimerTask() {       
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {       
                        try {
                        	updateViewChatRoom();
                        
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timerReadWebService.schedule(task, 0, 5*1000);  // interval of one minute

    }
    
    private void setRepeatingReadDbAsyncTask() {

        final Handler handler = new Handler();
        timerReadDb = new Timer();

        TimerTask task = new TimerTask() {       
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {       
                        try {
                        	readWebServiceContent();
                        
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timerReadDb.schedule(task, 0, 2*1000);  // interval of one minute

    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	timerReadDb.cancel();
    	timerReadWebService.cancel();
    	Toast.makeText(this, "ChatRoomActivity onDestroy", Toast.LENGTH_SHORT).show();
    }
	

}
