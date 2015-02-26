package com.example.android_searchview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SuggestionsDatabase 
{
	  public static final String DB_SUGGESTION = "SUGGESTIONS_DB";
	  public final static String TABLE_SUGGESTION = "SUGGESTIONS";
	  public final static String FIELD_KEY = "_id";
	  public final static String SUGGESTION = "suggestion";

	  private SQLiteDatabase db;
	  private DBHelper helper;

	  public SuggestionsDatabase(Context context) 
	  {
		  helper = new DBHelper(context, DB_SUGGESTION, null, 1);
		  db = helper.getWritableDatabase();
	  }

	  public long insertSuggestion(String text)
	  {
		  ContentValues values = new ContentValues();
		  values.put(SUGGESTION, text);
		  return db.insert(TABLE_SUGGESTION, null, values);
	  }

	  public Cursor getSuggestions(String text)
	  {
	    /*return db.query(TABLE_SUGGESTION, new String[] {FIELD_KEY, SUGGESTION}, 
	    		SUGGESTION+" LIKE '"+ text +"%'", null, null, null, null);*/
		  
		  Cursor cur = db.rawQuery("select _id, suggestion from SUGGESTIONS where suggestion LIKE '" +text+ "%';", null);
		  return cur;
	  }

	private class DBHelper extends SQLiteOpenHelper
	{
		public DBHelper(Context context, String name, CursorFactory factory, int version) 
		{
			super(context, name, factory, version);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL("CREATE TABLE "+TABLE_SUGGESTION+" ("+
			FIELD_KEY+" integer primary key autoincrement, "+SUGGESTION+" text);");
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{}
	}
}