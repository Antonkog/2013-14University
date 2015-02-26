package com.example.android_dblistview;

import com.example.android_dblistview.authorsFragment.Fragment2Listener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Fragment2Listener
{

	AuthorsDB sqh;
	SQLiteDatabase db;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    sqh = new AuthorsDB(this);  
	db = sqh.getWritableDatabase();
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
  	case R.id.show:
  		authorsFragment frag2 = authorsFragment.newInstance("It works!!!");
  		frag2.setDatabase(db);
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.add(R.id.fragment2, frag2);
	    
	    booksFragment frag1 = booksFragment.newInstance();
	    ft.add(R.id.fragment1, frag1);
	    ft.commit();
	  break;
  }
  return false;
}

public void onDestroy() {
    super.onDestroy();
    db.close();
    sqh.close();
  }

@Override
public void sendStr(String str) 
{
	booksFragment frag = (booksFragment) getFragmentManager().findFragmentById(R.id.fragment1);
	frag.setAuthor(str);
}

}
