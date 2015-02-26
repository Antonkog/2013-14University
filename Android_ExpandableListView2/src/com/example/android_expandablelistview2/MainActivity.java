package com.example.android_expandablelistview2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends Activity 
{

	  // названия компаний (групп)
	  String[] groups = new String[] {"Audi", "BMW", "Toyota", "Honda"};
	  
	  // названия (элементов)
	  String[] carsAudi = new String[] {"A4", "A6", "A8", "Quattro"};
	  String[] carsBMW = new String[] {"M3", "M5", "X3", "X5", "740"};
	  String[] carsToyota = new String[] {"Prius", "Camry", "Corolla", "Auris"};
	  
	  // коллекция для групп
	  ArrayList<Map<String, String>> groupData;
	  
	  // коллекция для элементов одной группы
	  ArrayList<Map<String, String>> childDataItem;

	  // общая коллекция для коллекций элементов
	  ArrayList<ArrayList<Map<String, String>>> childData;
	  // childData = ArrayList<childDataItem>
	  
	  // список аттрибутов группы или элемента
	  Map<String, String> m;

	  ExpandableListView elvMain;
	  TextView textView1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView) findViewById(R.id.textView1);
        
        // коллекция групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        
        // Перебор списка автомобильных компаний
        for (String group : groups) {
            // список атрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя авто компании
            groupData.add(m);  
        }
        
        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {"groupName"};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};
        
        // список коллекций автомобилей одной марки 
        childData = new ArrayList<ArrayList<Map<String, String>>>(); 
        
        // создаем коллекцию элементов для первой группы (AUDI)
        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список аттрибутов для каждого элемента автомобилей AUDI
        for (String car : carsAudi) {
            m = new HashMap<String, String>();
            m.put("carName", car);
            childDataItem.add(m);
        }
        // коллекция коллекций
        childData.add(childDataItem);

        // коллекция элементов для второй группы BMW       
        childDataItem = new ArrayList<Map<String, String>>();
        for (String car : carsBMW) {
            m = new HashMap<String, String>();
            m.put("carName", car);
            childDataItem.add(m);  
        }
        childData.add(childDataItem);

        // коллекция элементов для третьей группы Toyota      
        childDataItem = new ArrayList<Map<String, String>>();
        for (String car : carsToyota) {
            m = new HashMap<String, String>();
            m.put("carName", car);
            childDataItem.add(m);  
        }
        childData.add(childDataItem);
		
        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {"carName"};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};
        
        final SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
            this,
            groupData,
            android.R.layout.simple_expandable_list_item_1,
            groupFrom,
            groupTo,
            childData,
            android.R.layout.simple_list_item_1,
            childFrom,
            childTo);
            
        elvMain = (ExpandableListView) findViewById(R.id.expandableListView1);
        elvMain.setAdapter(adapter);
        
        // Программно развернуть вторую группу
        elvMain.expandGroup(2);
        
        // Программно свернуть третью группу
        elvMain.collapseGroup(3);
        
        // нажатие на элемент
        elvMain.setOnChildClickListener(new OnChildClickListener() 
        {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Log.d("Ok!", "onChildClick groupPosition = " + groupPosition + " childPosition = " + childPosition + " id = " + id);
				textView1.setText(((Map<String,String>)(adapter.getChild(groupPosition, childPosition))).get("carName"));
				return false;
			}
        });
        
        // нажатие на группу
        elvMain.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) 
			{
				Log.d("OK!", "onGroupClick groupPosition = " + groupPosition + " id = " + id);
				textView1.setText(((Map<String,String>)(adapter.getGroup(groupPosition))).get("groupName"));
				if (groupPosition == 3) return true;
				return false;
			}
        	
        	
        });
        
        // сворачивание группы        
        elvMain.setOnGroupCollapseListener(new OnGroupCollapseListener() 
        {

			@Override
			public void onGroupCollapse(int groupPosition) 
			{
				Log.d("OK!", "onGroupCollapse groupPosition = " + groupPosition);
			}
        });
        
        // разворачивание группы
        elvMain.setOnGroupExpandListener(new OnGroupExpandListener() 
        {

			@Override
			public void onGroupExpand(int groupPosition) 
			{
				Log.d("OK!", "onGroupExpand groupPosition = " + groupPosition);
			}
        });
    }
}
