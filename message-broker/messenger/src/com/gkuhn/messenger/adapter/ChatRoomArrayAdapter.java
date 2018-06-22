package com.gkuhn.messenger.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gkuhn.messenger.R;
import com.gkuhn.messenger.model.ChatRoom;

@SuppressWarnings("unused")
public class ChatRoomArrayAdapter extends ArrayAdapter<ChatRoom>{

		private TextView chatRoomText;
		private TextView chatRoomFromText;
		private List<ChatRoom> chatRoomList = new ArrayList<ChatRoom>();
		private Context context;
		private int userId;
		
		
		@Override
		public void add(ChatRoom object) {
			chatRoomList.add(object);
			super.add(object);
		}
		
		public ChatRoomArrayAdapter(Context context, int textViewResourceId, int userId) {
			super(context, textViewResourceId);
			this.context = context;
			this.userId = userId;
		}
		
		public int getCount() {
			return chatRoomList.size();
		}
		
		public ChatRoom getItem(int index) {
			return this.chatRoomList.get(index);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatRoom chatRoomObj = getItem(position);
			View row = convertView;
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		    row = inflater.inflate(R.layout.list_item_chatrooms, parent, false);

		    chatRoomText = (TextView) row.findViewById(R.id.message);
		    chatRoomText.setText(String.valueOf(chatRoomObj.get_LastMessage()));
			
		    chatRoomFromText = (TextView) row.findViewById(R.id.from);
		    chatRoomFromText.setText(String.valueOf(chatRoomObj.get_GuestUser()));
		    
			return row;
		}
		
		public List<ChatRoom> getChatRoomList() {
			return this.chatRoomList;
		}
}
