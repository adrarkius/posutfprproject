package com.gkuhn.messenger.dialog;

import com.gkuhn.messenger.R;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

public class NewRoomDialog extends DialogFragment implements View.OnClickListener{

	Spinner spinner;
	Button conversar;
	Communicator communicator;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		communicator = (Communicator) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_newroom, null);
		spinner = (Spinner) view.findViewById(R.id.spinner);
		
		conversar = (Button) view.findViewById(R.id.button);
		conversar.setOnClickListener(this);
		
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.button) {
			communicator.onDialogMessage(spinner.getSelectedItem().toString());
			this.dismiss();
		}
	}
	
	public interface Communicator {
		public void onDialogMessage(String message);
	}
	
	
}
