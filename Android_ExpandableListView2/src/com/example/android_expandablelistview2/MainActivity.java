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

	  // �������� �������� (�����)
	  String[] groups = new String[] {"Audi", "BMW", "Toyota", "Honda"};
	  
	  // �������� (���������)
	  String[] carsAudi = new String[] {"A4", "A6", "A8", "Quattro"};
	  String[] carsBMW = new String[] {"M3", "M5", "X3", "X5", "740"};
	  String[] carsToyota = new String[] {"Prius", "Camry", "Corolla", "Auris"};
	  
	  // ��������� ��� �����
	  ArrayList<Map<String, String>> groupData;
	  
	  // ��������� ��� ��������� ����� ������
	  ArrayList<Map<String, String>> childDataItem;

	  // ����� ��������� ��� ��������� ���������
	  ArrayList<ArrayList<Map<String, String>>> childData;
	  // childData = ArrayList<childDataItem>
	  
	  // ������ ���������� ������ ��� ��������
	  Map<String, String> m;

	  ExpandableListView elvMain;
	  TextView textView1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView) findViewById(R.id.textView1);
        
        // ��������� ����� �� ������� � ���������� �����
        groupData = new ArrayList<Map<String, String>>();
        
        // ������� ������ ������������� ��������
        for (String group : groups) {
            // ������ ��������� ��� ������ ������
            m = new HashMap<String, String>();
            m.put("groupName", group); // ��� ���� ��������
            groupData.add(m);  
        }
        
        // ������ ���������� ����� ��� ������
        String groupFrom[] = new String[] {"groupName"};
        // ������ ID view-���������, � ������� ����� �������� ��������� �����
        int groupTo[] = new int[] {android.R.id.text1};
        
        // ������ ��������� ����������� ����� ����� 
        childData = new ArrayList<ArrayList<Map<String, String>>>(); 
        
        // ������� ��������� ��������� ��� ������ ������ (AUDI)
        childDataItem = new ArrayList<Map<String, String>>();
        // ��������� ������ ���������� ��� ������� �������� ����������� AUDI
        for (String car : carsAudi) {
            m = new HashMap<String, String>();
            m.put("carName", car);
            childDataItem.add(m);
        }
        // ��������� ���������
        childData.add(childDataItem);

        // ��������� ��������� ��� ������ ������ BMW       
        childDataItem = new ArrayList<Map<String, String>>();
        for (String car : carsBMW) {
            m = new HashMap<String, String>();
            m.put("carName", car);
            childDataItem.add(m);  
        }
        childData.add(childDataItem);

        // ��������� ��������� ��� ������� ������ Toyota      
        childDataItem = new ArrayList<Map<String, String>>();
        for (String car : carsToyota) {
            m = new HashMap<String, String>();
            m.put("carName", car);
            childDataItem.add(m);  
        }
        childData.add(childDataItem);
		
        // ������ ���������� ��������� ��� ������
        String childFrom[] = new String[] {"carName"};
        // ������ ID view-���������, � ������� ����� �������� ��������� ���������
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
        
        // ���������� ���������� ������ ������
        elvMain.expandGroup(2);
        
        // ���������� �������� ������ ������
        elvMain.collapseGroup(3);
        
        // ������� �� �������
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
        
        // ������� �� ������
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
        
        // ������������ ������        
        elvMain.setOnGroupCollapseListener(new OnGroupCollapseListener() 
        {

			@Override
			public void onGroupCollapse(int groupPosition) 
			{
				Log.d("OK!", "onGroupCollapse groupPosition = " + groupPosition);
			}
        });
        
        // �������������� ������
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
