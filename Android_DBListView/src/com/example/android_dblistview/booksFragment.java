package com.example.android_dblistview;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class booksFragment extends ListFragment 
{

  final String LOG_TAG = "myLogs";
  TextView textView1;
  
  AuthorsDB sqh;
  SQLiteDatabase db;
  ArrayList <String> lst = new ArrayList<String>();
  
  public static booksFragment newInstance() 
  {
	  booksFragment myFragment = new booksFragment();
	  //Bundle args = new Bundle();
	  //args.putString("str", str);
	  //myFragment.setArguments(args);
	  return myFragment;
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(LOG_TAG, "Fragment1 onCreate");
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
	  
	  View v = inflater.inflate(R.layout.fragment1, null);
	  
	  sqh = new AuthorsDB(getActivity());  
	  db = sqh.getWritableDatabase();
  		
  		String query = "SELECT title, type from titles";
		Cursor cursor2 = db.rawQuery(query, null);
		while (cursor2.moveToNext()) {
			String fname = cursor2.getString(0);
			String lname = cursor2.getString(1);
			lst.add(fname + " " + lname);
		}
		cursor2.close();
	  
		String authorsArray[] = new String[lst.size()];
		lst.toArray(authorsArray);
		
	  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, authorsArray);
		    setListAdapter(adapter);
	  
	  return v;
  }

  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  public void onResume() {
    super.onResume();
    Log.d(LOG_TAG, "Fragment1 onResume");
  }

  public void onPause() {
    super.onPause();
    Log.d(LOG_TAG, "Fragment1 onPause");
  }
  
  public void setAuthor(String author)
  {
	  lst.clear();
	  
	  String query = "SELECT title, type from titles where title_id in (select title_id from titleauthor where au_id = '"+author+"')";
		Cursor cursor2 = db.rawQuery(query, null);
		while (cursor2.moveToNext()) {
			String fname = cursor2.getString(0);
			String lname = cursor2.getString(1);
			lst.add(fname + " " + lname);
		}
		cursor2.close();
	  
		String authorsArray[] = new String[lst.size()];
		lst.toArray(authorsArray);
		
	  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, authorsArray);
	  setListAdapter(adapter);
  }

}