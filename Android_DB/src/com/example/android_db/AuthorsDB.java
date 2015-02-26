package com.example.android_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AuthorsDB extends SQLiteOpenHelper
{

	final static String databaseName = "authors.db3";
	final static int databaseVersion = 1;
	
	public AuthorsDB(Context context) {
		super(context, databaseName, null, databaseVersion);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE authors(au_id INTEGER PRIMARY KEY, au_lname VARCHAR(40), au_fname VARCHAR(20), city VARCHAR(20), state VARCHAR(2))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
