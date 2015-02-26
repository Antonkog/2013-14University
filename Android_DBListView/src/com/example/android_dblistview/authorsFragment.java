package com.example.android_dblistview;

import java.util.ArrayList;

import com.example.android_dblistview.AuthorsDB;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class authorsFragment extends ListFragment
{
	
	public interface Fragment2Listener 
	{
		public void sendStr(String str);
	}
	  
	Fragment2Listener fr2Listener;

  final String LOG_TAG = "myLogs";
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    
    if(activity instanceof Fragment2Listener) fr2Listener = (Fragment2Listener) activity;
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  public static authorsFragment newInstance(String str) 
  {
	  authorsFragment myFragment = new authorsFragment();
	  Bundle args = new Bundle();
	  args.putString("str", str);
	  myFragment.setArguments(args);
	  return myFragment;
  }
  
  AuthorsDB sqh;
  SQLiteDatabase db;
  ArrayList <Author> lst = new ArrayList<Author>();
  
  public void setDatabase(SQLiteDatabase database)
  {
	  db = database;
  }
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) 
  {		
  		String query = "SELECT au_id, au_fname, au_lname, city, state from authors";
		Cursor cursor2 = db.rawQuery(query, null);
		while (cursor2.moveToNext()) 
		{
			Author author = new Author();
			author.au_id = cursor2.getString(0);
			author.au_fname = cursor2.getString(1);
			author.au_lname = cursor2.getString(2);
			author.city = cursor2.getString(3);
			author.state = cursor2.getString(4);
			lst.add(author);
		}
		cursor2.close();
	  
		String authorsArray[] = new String[lst.size()];
		for(int i=0;i<lst.size();i++)
		{
			authorsArray[i] = lst.get(i).au_fname + " " + lst.get(i).au_lname;
		}
		
	  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
		        android.R.layout.simple_list_item_1, authorsArray);
	  setListAdapter(adapter);

	  View v = inflater.inflate(R.layout.fragment2, null);
	  
	  return v;
  }

public void onListItemClick(ListView l, View v, int position, long id) 
{
    super.onListItemClick(l, v, position, id);
    if(fr2Listener!=null) fr2Listener.sendStr(lst.get(position).au_id);
}

}