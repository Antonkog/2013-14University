package com.example.android_actionbar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements ActionBar.TabListener, ActionBar.OnNavigationListener
{
  
  String[] data = new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

  TextView textView1;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar bar = getActionBar();
    textView1 = (TextView) this.findViewById(R.id.textView1);

    /*bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    Tab tab = bar.newTab();
    tab.setText("tab1");
    tab.setTabListener(this);
    bar.addTab(tab);

    tab = bar.newTab();
    tab.setText("tab2");
    tab.setTabListener(this);
    bar.addTab(tab);
    
    tab = bar.newTab();
    tab.setText("tab3");
    tab.setTabListener(this);
    bar.addTab(tab);

    tab = bar.newTab();
    tab.setText("tab4");
    tab.setTabListener(this);
    bar.addTab(tab);
    
    tab = bar.newTab();
    tab.setText("tab5");
    tab.setTabListener(this);
    bar.addTab(tab);*/
    
    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    bar.setListNavigationCallbacks(adapter, this);

  }

  public void onTabReselected(Tab tab, FragmentTransaction ft) {

  }

  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    textView1.setText(tab.getText());
  }

  public void onTabUnselected(Tab tab, FragmentTransaction ft) {

  }

	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		textView1.setText(data[itemPosition]);
		return false;
	}
}
