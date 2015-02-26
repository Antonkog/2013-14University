package com.example.android_listviewfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainList extends ListFragment 
{

  String data[] = new String[] { "one", "two", "three", "four", "five" };
  
  PersonAdapter personAdapter;
  ArrayList<Person> people=null;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) 
  {
    super.onActivityCreated(savedInstanceState);
    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
    //setListAdapter(adapter);
    
    people = new ArrayList<Person>();
	people.add(new Person("Ivan", "Petrov"));
	people.add(new Person("Anna", "Karenina"));
	people.add(new Person("Vasya", "Pupkin"));
	
	personAdapter = new PersonAdapter(getActivity(), R.layout.person_item, people);

	setListAdapter(personAdapter);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
  {
    return inflater.inflate(R.layout.fragment1, null);
  }
  
  public void onListItemClick(ListView l, View v, int position, long id) 
  {
	    super.onListItemClick(l, v, position, id);
	    
	    //Toast.makeText(getActivity(), "position = " + position, Toast.LENGTH_SHORT).show();
	    
	    // Получить доступ к элементу по его позиции 
	    Person person = (Person)(this.getListView().getItemAtPosition(position));
		
		Toast.makeText(getActivity(), person.firstName + " " + person.lastName, Toast.LENGTH_SHORT).show();
		
		// Сообщить адаптеру, что пользователь нажал на пункт
		personAdapter.inverseSelection(position);
		
	    ArrayList<Integer> checkedPos = personAdapter.getCurrentCheckedPosition();
	    for(Integer pos : checkedPos)
	    {
	    	Log.d("OK!", people.get(pos).toString());
	    }
	    
  }

}