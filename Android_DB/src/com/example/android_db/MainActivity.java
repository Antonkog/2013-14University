package com.example.android_db;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements SQLiteTransactionListener
{
	AuthorsDB helper;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		helper = new AuthorsDB(this);
	    db = helper.getWritableDatabase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch(item.getItemId())
      {
      	case R.id.update:
      		String filter = "au_id = ?";
      		ContentValues cv = new ContentValues();
      		cv.put("au_fname", "Nikolay");
      		db.update("authors", cv, filter, new String[] {"1"});
    	  	break;
    	  		
      	case R.id.insert:
      		try
      		{
      			db.beginTransactionWithListener(this);
	      		cv = new ContentValues();
		    	cv.put("au_fname", "Ivan");
		    	cv.put("au_lname", "Demidov");
		    	cv.put("city", "Donetsk");
		    	cv.put("state", "DN");
		    	db.insert("authors", null, cv);
		    	
		    	String insertQuery = "INSERT INTO authors(au_fname, au_lname, city, state) values ('Alex', 'Petrov', 'Kiev', 'KI');";
	      		db.execSQL(insertQuery);
	      		
	      		db.setTransactionSuccessful();
      		}
      		catch(Exception ex)
      		{
      			
      		}
      		finally
      		{
      			db.endTransaction();
      		}
      		break;
    	  		
      	case R.id.delete:
      		String deleteQuery = "delete from authors;";
      		db.execSQL(deleteQuery);
      		break;
    	  		
      	case R.id.select:
      		/*Cursor cursor = db.query("authors", new String[] {"au_id", "au_fname", "au_lname", "city", "state"}, 
    		"state = ?", // фильтр where
    		new String[] {"DN"}, // значения фильтра where
    		null, // группировка group by
    		null, // фильтр групп having
    		"au_id desc" // сортировка order by
    		);
	    	while (cursor.moveToNext()) 
	    	{
	    		int id = cursor.getInt(cursor.getColumnIndex("au_id"));
	    		String fname = cursor.getString(cursor.getColumnIndex("au_fname"));
	    		String lname = cursor.getString(cursor.getColumnIndex("au_lname"));
	    		String city = cursor.getString(cursor.getColumnIndex("city"));
	    		String state = cursor.getString(cursor.getColumnIndex("state"));
	    		Log.i("DB!!!","au_id:  "+id+", au_fname "+fname+", au_lname: "+lname+", city: "+city+", state: "+state);
	    	}
	    	cursor.close();*/
      		
      		/*
      		Cursor cursor = db.query("authors", new String[] {"state", "count(state) as kolvo"}, 
    		null, // фильтр where
    		null, // значения фильтра where
    		"state", // группировка group by
    		"kolvo > 2", // фильтр групп having
    		null // сортировка order by
    		);
	    	while (cursor.moveToNext()) 
	    	{
	    		String state = cursor.getString(cursor.getColumnIndex("state"));
	    		int kolvo = cursor.getInt(cursor.getColumnIndex("kolvo"));
	    		Log.i("DB!!!","kolvo:  "+kolvo+", state: "+state);
	    	}
	    	cursor.close();
	    	*/
      		
      		String query = "SELECT au_id, au_fname, au_lname, city, state from authors";
	    	Cursor cursor2 = db.rawQuery(query, null);
	    	while (cursor2.moveToNext()) {
	    		int id = cursor2.getInt(0);
    			String fname = cursor2.getString(1);
    			String lname = cursor2.getString(cursor2.getColumnIndex("au_lname"));
    			String city = cursor2.getString(cursor2.getColumnIndex("city"));
    			String state = cursor2.getString(cursor2.getColumnIndex("state"));
    			Log.i("DB!!!","au_id:  "+id+", au_fname "+fname+", au_lname: "+lname+", city: "+city+", state: "+state);
	    	}
	    	cursor2.close();
      		break;
      }
      
      return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		db.close();
		helper.close();
	    Log.i("DB!!!", "OnDestroy!");
		super.onDestroy();
	}

	public void onBegin() {
		// TODO Auto-generated method stub
		Log.i("DB!!!", "Begin tran!");
	}

	public void onCommit() {
		// TODO Auto-generated method stub
		Log.i("DB!!!", "Commit tran!");
	}

	public void onRollback() {
		// TODO Auto-generated method stub
		Log.i("DB!!!", "Rollback tran!");
	}
}
