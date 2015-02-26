package com.example.android_simplecursoradapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AuthorsDB extends SQLiteOpenHelper
{

	final static String databaseName = "pubs.sqlite3";
	final static int databaseVersion = 1;
	Context appContext;
	
	public AuthorsDB(Context context)
	{
		super(context, databaseName, null, databaseVersion);
		appContext = context;
		try {
			copyDataBase(databaseName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL("CREATE TABLE authors(au_id INTEGER PRIMARY KEY, au_lname VARCHAR(40), au_fname VARCHAR(20), city VARCHAR(20), state VARCHAR(2))");
	}
	
	private void copyDataBase(String dbname) throws IOException 
	{
        // Open your local db as the input stream
        InputStream myInput = appContext.getAssets().open(dbname);
        // Path to the just created empty db
        String outFileName = "/data/data/com.example.android_simplecursoradapter/databases/" + dbname;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[102400];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
