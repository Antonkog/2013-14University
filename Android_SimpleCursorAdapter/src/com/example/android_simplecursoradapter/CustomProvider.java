package com.example.android_simplecursoradapter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class CustomProvider extends ContentProvider 
{
	protected static String DB_NAME = "pubs.sqlite3";	
	protected static final int DB_VERSION = 1;
	public static String authority="com.example.android_simplecursoradapter.provider";
	public static Uri CONTENT_URI = Uri.parse("content://" + authority  + "/pubs");
	private static String TAG="CustomProvider";
	
	AuthorsDB sqh;
	SQLiteDatabase db;
	   
	@Override
	public boolean onCreate() {
		Log.v(TAG,"CustomProvider:OnCreate()");

		sqh = new AuthorsDB(getContext());  
		db = sqh.getWritableDatabase();
		return true;
	}
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.v(TAG,"CustomProvider:Query()");
		Cursor cursor = db.rawQuery( "select rowid _id, * from authors", null);
		return cursor;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
