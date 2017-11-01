package com.gkuhn.messenger.dao;

import java.util.ArrayList;
import java.util.List;

import com.gkuhn.messenger.model.MessageEvent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerMessage extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "instant_messeger";
 
    // Contacts table name
    private static final String TABLE_MESSAGEEVENT = "messageevent";
 
    // Contacts Table Columns names
    private static final String KEY_USERMESSAGEID = "_usermessageid";
    private static final String KEY_MESSAGEID = "_messageid";
    private static final String KEY_FROM = "_from";
    private static final String KEY_TO = "_to";
    private static final String KEY_MESSAGE = "_message";
    private static final String KEY_SENTTIME = "_senttime";
    private static final String KEY_RECEIVED = "_received";
 
    public DatabaseHandlerMessage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGEEVENT + "("
                + KEY_USERMESSAGEID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_MESSAGEID + " INT," + KEY_FROM + " INT,"
                + KEY_TO + " INT," + KEY_MESSAGE + " TEXT," + KEY_SENTTIME + " TEXT,"
                + KEY_RECEIVED + " TEXT,"+" UNIQUE("+KEY_USERMESSAGEID+","+KEY_FROM+")" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGEEVENT);
 
        // Create tables again
        onCreate(db);
    }
    
    // Adding new message
    public Long addMessage(MessageEvent message) {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 Long returnedId;
    	    ContentValues values = new ContentValues();
    	    if(message.get_usermessageid() != 0) {
    	    	values.put(KEY_USERMESSAGEID, message.get_usermessageid());
    	    }
    	    values.put(KEY_MESSAGEID, message.get_messageid());
    	    values.put(KEY_FROM, message.get_from()); // Message FROM USER NUMBER
    	    values.put(KEY_TO, message.get_to()); // Message FROM USER NUMBER
    	    values.put(KEY_MESSAGE, message.get_message()); // Message FROM USER NUMBER
    	    values.put(KEY_SENTTIME, message.get_senttime()); // Message FROM USER NUMBER
    	    values.put(KEY_RECEIVED, message.is_received()); // Message FROM USER NUMBER
    	    
    	 
    	    // Inserting Row
    	    returnedId = db.replace(TABLE_MESSAGEEVENT, null, values);
    	    db.close(); // Closing database connection
    	    
    	    return returnedId;
    }
     
    // Getting single message
    public MessageEvent getMessage(Long id) {
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_MESSAGEEVENT, new String[] { KEY_USERMESSAGEID ,KEY_MESSAGEID,
                KEY_FROM, KEY_TO, KEY_MESSAGE, KEY_SENTTIME, KEY_RECEIVED }, KEY_USERMESSAGEID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        MessageEvent message = new MessageEvent(
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(2)), 
        		Integer.parseInt(cursor.getString(3)),
        		cursor.getString(4),
        		cursor.getString(5), 
        		Boolean.parseBoolean(cursor.getString(6)));
        // return contact
        return message;
    }
     
    // Getting All Messages
    public List<MessageEvent> getAllMessages() {
    	  List<MessageEvent> messageList = new ArrayList<MessageEvent>();
          // Select All Query
          String selectQuery = "SELECT  * FROM " + TABLE_MESSAGEEVENT;
   
          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.rawQuery(selectQuery, null);
   
          // looping through all rows and adding to list
          if (cursor.moveToFirst()) {
              do {
                  MessageEvent message = new MessageEvent();
                  message.set_usermessageid(Integer.parseInt(cursor.getString(0)));
                  message.set_messageid(Integer.parseInt(cursor.getString(1)));
                  message.set_from(Integer.parseInt(cursor.getString(2)));
                  message.set_to(Integer.parseInt(cursor.getString(3)));
                  message.set_message(cursor.getString(4));
                  message.set_senttime(cursor.getString(5));
                  message.set_received(Boolean.parseBoolean(cursor.getString(6)));
          
                  // Adding contact to list
                  messageList.add(message);
              } while (cursor.moveToNext());
          }
   
          // return contact list
          return messageList;
    }
    
    // Getting Rooms information
    public List<MessageEvent> getMessagesByUser(int userId) {
    	  List<MessageEvent> messageList = new ArrayList<MessageEvent>();
          
   
          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.query(TABLE_MESSAGEEVENT, new String[] { KEY_USERMESSAGEID ,KEY_MESSAGEID,
                  KEY_FROM, KEY_TO, KEY_MESSAGE, KEY_SENTTIME, KEY_RECEIVED }, KEY_TO + "=?",
                  new String[] {String.valueOf(userId)}, null, null, null, null);
   
          // looping through all rows and adding to list
          if (cursor.moveToFirst()) {
              do {
                  MessageEvent message = new MessageEvent();
                  message.set_usermessageid(Integer.parseInt(cursor.getString(0)));
                  message.set_messageid(Integer.parseInt(cursor.getString(1)));
                  message.set_from(Integer.parseInt(cursor.getString(2)));
                  message.set_to(Integer.parseInt(cursor.getString(3)));
                  message.set_message(cursor.getString(4));
                  message.set_senttime(cursor.getString(5));
                  message.set_received(Boolean.parseBoolean(cursor.getString(6)));
          
          
                  // Adding contact to list
                  messageList.add(message);
              } while (cursor.moveToNext());
          }
   
          // return contact list
          return messageList;
    }
    
    // Getting Rooms By User
    public List<MessageEvent> getRoomsByUser(int userId) {
    	  List<MessageEvent> messageList = new ArrayList<MessageEvent>();
          
    	  String selectQuery = "SELECT  * FROM " + TABLE_MESSAGEEVENT +" WHERE "+KEY_TO+" =" + userId + " GROUP BY "+KEY_FROM;
    	  
          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.rawQuery(selectQuery,null);
   
          // looping through all rows and adding to list
          if (cursor.moveToFirst()) {
              do {
                  MessageEvent message = new MessageEvent();
                  message.set_usermessageid(Integer.parseInt(cursor.getString(0)));
                  message.set_messageid(Integer.parseInt(cursor.getString(1)));
                  message.set_from(Integer.parseInt(cursor.getString(2)));
                  message.set_to(Integer.parseInt(cursor.getString(3)));
                  message.set_message(cursor.getString(4));
                  message.set_senttime(cursor.getString(5));
                  message.set_received(Boolean.parseBoolean(cursor.getString(6)));
          
          
                  // Adding contact to list
                  messageList.add(message);
              } while (cursor.moveToNext());
          }
   
          // return contact list
          return messageList;
    }
    
    
    public List<MessageEvent> getChatByUserFromAndUserTo(int userFrom, int userTo) {
  	  List<MessageEvent> messageList = new ArrayList<MessageEvent>();
        
  	  String selectQuery = "SELECT  * FROM " + TABLE_MESSAGEEVENT +" WHERE "+"("+KEY_TO+" =" +userTo+" AND "+KEY_FROM+" ="+userFrom+")"+" OR "+"("+KEY_TO+" ="+userFrom+ " AND "+KEY_FROM+" ="+userTo+")"+" ORDER BY "+KEY_SENTTIME+" ASC";
  	  
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MessageEvent message = new MessageEvent();
                message.set_usermessageid(Integer.parseInt(cursor.getString(0)));
                message.set_messageid(Integer.parseInt(cursor.getString(1)));
                message.set_from(Integer.parseInt(cursor.getString(2)));
                message.set_to(Integer.parseInt(cursor.getString(3)));
                message.set_message(cursor.getString(4));
                message.set_senttime(cursor.getString(5));
                message.set_received(Boolean.parseBoolean(cursor.getString(6)));
        
                // Adding contact to list
                messageList.add(message);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return messageList;
  }


    public void deleteAllMessages() {
 
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_MESSAGEEVENT,"1",null);
            db.close();
        }
    
    
    public List<MessageEvent> getMessagesByMessageId(int messageId) {
    	  List<MessageEvent> messageList = new ArrayList<MessageEvent>();
          
    	  String selectQuery = "SELECT  * FROM " + TABLE_MESSAGEEVENT +" WHERE "+"("+KEY_MESSAGEID+" =" +messageId+")"+" ORDER BY "+KEY_SENTTIME+" ASC";
    	  
          SQLiteDatabase db = this.getWritableDatabase();
          Cursor cursor = db.rawQuery(selectQuery,null);
   
          // looping through all rows and adding to list
          if (cursor.moveToFirst()) {
              do {
                  MessageEvent message = new MessageEvent();
                  message.set_usermessageid(Integer.parseInt(cursor.getString(0)));
                  message.set_messageid(Integer.parseInt(cursor.getString(1)));
                  message.set_from(Integer.parseInt(cursor.getString(2)));
                  message.set_to(Integer.parseInt(cursor.getString(3)));
                  message.set_message(cursor.getString(4));
                  message.set_senttime(cursor.getString(5));
                  message.set_received(Boolean.parseBoolean(cursor.getString(6)));
          
                  // Adding contact to list
                  messageList.add(message);
              } while (cursor.moveToNext());
          }
   
          // return contact list
          return messageList;
    }
     
    /*// Getting messages Count
    public int getMessagesCount() {}
    // Updating single message
    public int updateMessage(MessageEvent message) {}
     
    // Deleting single message
    public void deleteMessage(MessageEvent message) {}*/
}
