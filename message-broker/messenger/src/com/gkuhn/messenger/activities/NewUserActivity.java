package com.gkuhn.messenger.activities;

import com.gkuhn.messenger.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewUserActivity extends Activity {

	EditText userNameEdtTxt;
	EditText userPasswordEdtTxt;
	EditText userConfirmPasswordEdtTxt;
	
	TextView userMessageTxtView;
	
	Button	userRegisterBtn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser);
		
		userNameEdtTxt = (EditText) findViewById(R.id.newuser_edtTxt_username);
		userPasswordEdtTxt = (EditText) findViewById(R.id.newuser_edtTxt_userpassword);
		userConfirmPasswordEdtTxt = (EditText) findViewById(R.id.newuser_edtTxt_confirmuserpassword);
		
		userRegisterBtn = (Button) findViewById(R.id.newUser_button_register);
	
		userMessageTxtView = (TextView) findViewById(R.id.newUser_txtView_message);
		
		userRegisterBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = userPasswordEdtTxt.getText().toString();
				String confirmedPassword = userConfirmPasswordEdtTxt.getText().toString();
				
				if(!confirmedPassword.equals(password)) {
					userMessageTxtView.setText("Senhas não conferem");
				} else {
					userMessageTxtView.setText("");
				}
				
			}
		});
		
		
	}

}