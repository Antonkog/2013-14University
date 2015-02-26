package com.example.android_simplecursoradapter;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor>
{
    SimpleCursorAdapter mAdapter; 		
    LoaderManager loadermanager;		
    CursorLoader cursorLoader;
    ListView listview1;
    
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Получить актуальный LoaderManager
		loadermanager=getLoaderManager();
		
		String[] from = new String[] { "au_fname", "au_lname", "city", "state" };
	    int[] to = new int[] { R.id.au_fname, R.id.au_lname, R.id.city, R.id.state };

	    
	    mAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
       
	    // Инициализация LoaderManager
	    // 1 - номер менеджера
	    // 2 - пользовательский параметр
	    // 3 - активность
        loadermanager.initLoader(1, null, this);
       
        listview1 = (ListView) findViewById(R.id.listview1);
	    listview1.setAdapter(mAdapter);
	    
	}
	
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		String[] projection = { "_id", "au_fname" };
		
		cursorLoader = new CursorLoader(this, CustomProvider.CONTENT_URI, projection, null, null, null);
	    return cursorLoader;
		
	}

	public void onLoadFinished(Loader<Cursor> loader,Cursor cursor) {
		if(mAdapter!=null && cursor!=null)
			mAdapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		if(mAdapter!=null)
			mAdapter.swapCursor(null);
	}

}
