package com.gkuhn.messenger.activities;

import com.gkuhn.messenger.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity{
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	  
	        
	        final Button button = (Button) findViewById(R.id.but1);
	        button.setOnClickListener(new View.OnClickListener() {
				
	        	 Spinner sp1 = (Spinner) findViewById(R.id.spinner1);
	        	
				@Override
				public void onClick(View arg0) {
					
					Intent intent = new Intent(MainActivity.this, ChatRoomActivity.class);
					
					intent.putExtra("value", sp1.getSelectedItem().toString());
					
					startActivity(intent);
					overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
					
					
				}
			});
	      
	    }
	    
	    


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

}
