package com.gkuhn.messenger.dao;

import java.util.ArrayList;
import java.util.List;

import com.gkuhn.messenger.model.ChatRoom;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandlerChatRoom  extends SQLiteOpenHelper{

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "instant_messenger";
 
    // Contacts table name
    private static final String TABLE_CHATROOM = "ChatRooms";
 
    // Contacts Table Columns names
    private static final String KEY_ROOMID = "_roomId";
    private static final String KEY_OWNERUSER = "_owner";
    private static final String KEY_GUESTUSER = "_guest";
    private static final String KEY_LASTMESSAGE = "_lastMessage";

    public DatabaseHandlerChatRoom(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		 String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CHATROOM + "("
	                + KEY_ROOMID + " INTEGER PRIMARY KEY," + KEY_OWNERUSER + " INT,"
	                + KEY_GUESTUSER + " INT," + KEY_LASTMESSAGE + " TEXT)";
	        
		 try {
			 db.execSQL(CREATE_CONTACTS_TABLE);
		 } catch (SQLException e) {
			 Log.d("DatabaseHandlerChatRoom", e.getMessage());
		 }
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		 // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATROOM);
 
        // Create tables again
        onCreate(db);
		
	}
	
	  // Adding new message
    public void addChatRoom(ChatRoom chat) {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
    	    ContentValues values = new ContentValues();
    	    values.put(KEY_ROOMID, chat.get_roomId());
    	    values.put(KEY_OWNERUSER, chat.get_OwnerUser()); // Message FROM USER NUMBER
    	    values.put(KEY_GUESTUSER, chat.get_GuestUser()); // Message FROM USER NUMBER
    	    values.put(KEY_LASTMESSAGE, chat.get_LastMessage()); // LAST MESSAGE

    	    
    	 
    	    // Inserting Row
    	    db.replace(TABLE_CHATROOM, null, values);
    	    db.close(); // Closing database connection
    }
     
    // Getting single message
    public ChatRoom getRoomById(int id) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_CHATROOM, new String[] { KEY_ROOMID,
        		KEY_OWNERUSER, KEY_GUESTUSER, KEY_LASTMESSAGE}, KEY_ROOMID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        ChatRoom room = new ChatRoom(Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        // return contact
        return room;
    }
    
    // Getting single room
    public ChatRoom getRoomByOwnerAndGuest(int owner, int guest) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
    	String selectQuery = "SELECT  * FROM " + TABLE_CHATROOM +" WHERE "+KEY_OWNERUSER+"="+owner+" AND "+KEY_GUESTUSER+"="+guest;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        ChatRoom room = new ChatRoom(Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        // return contact
        return room;
    }
    
    // Getting single room
    public List<ChatRoom> getRoomsByOwner (int owner) {
    	
    	List<ChatRoom> roomsList = new ArrayList<ChatRoom>();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
    	String selectQuery = "SELECT  * FROM " + TABLE_CHATROOM +" WHERE "+KEY_OWNERUSER+"="+owner;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	 ChatRoom room = new ChatRoom(Integer.parseInt(cursor.getString(0)),
                 		Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        
                // Adding contact to list
            	 roomsList.add(room);
            } while (cursor.moveToNext());
        }
       
        // return contact
        return roomsList;
    }
	
	
	
	
}
