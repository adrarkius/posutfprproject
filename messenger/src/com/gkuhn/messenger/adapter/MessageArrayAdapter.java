package com.gkuhn.messenger.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.gkuhn.messenger.R;
import com.gkuhn.messenger.model.MessageEvent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat") @SuppressWarnings("unused")
public class MessageArrayAdapter extends ArrayAdapter<MessageEvent>{
	private TextView messageText;
	private TextView senttimeText;
	private TextView fromText;
	private List<MessageEvent> messageEventList = new ArrayList<MessageEvent>();
	private Context context;
	private int userId;
	
	@Override
	public void add(MessageEvent object) {
		messageEventList.add(object);
		super.add(object);
	}
	
	public MessageArrayAdapter(Context context, int textViewResourceId, int userId) {
		super(context, textViewResourceId);
		this.context = context;
		this.userId = userId;
	}
	
	public int getCount() {
		return messageEventList.size();
	}
	
	public MessageEvent getItem(int index) {
		return this.messageEventList.get(index);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		MessageEvent messageEventObj = getItem(position);
		View row = convertView;
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (messageEventObj.get_from() == userId) {
	            row = inflater.inflate(R.layout.left, parent, false);
	        }else{
	            row = inflater.inflate(R.layout.right, parent, false);
	        }
		messageText = (TextView) row.findViewById(R.id.message);
		messageText.setText(messageEventObj.get_message());
		
		SimpleDateFormat isoFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		isoFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		
		Date sentDate = new Date(Long.parseLong(messageEventObj.get_senttime()));
		String sentDateStr = isoFormat.format(sentDate); 
		
		
		senttimeText = (TextView) row.findViewById(R.id.senttime);
		senttimeText.setText(sentDateStr);
		
		fromText = (TextView) row.findViewById(R.id.from);
		fromText.setText(String.valueOf(messageEventObj.get_usermessageid()));
		return row;
	}
	
	public List<MessageEvent> getMessageEventList() {
		return this.messageEventList;
	}

}
