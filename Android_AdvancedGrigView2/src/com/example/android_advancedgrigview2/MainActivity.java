package com.example.android_advancedgrigview2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	GridView gridView1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView1=(GridView)findViewById(R.id.gridView1);
        
        Group group = new Group();
        group.addPerson("Ivan", "Petrov", R.drawable.admin);
        group.addPerson("Vasya", "Enotov", R.drawable.enotik);
        group.addPerson("Anna", "Gavrilova", R.drawable.admin);
        
        ImageAdapter adapter = new ImageAdapter(this, group);
        gridView1.setAdapter(adapter);
        
        adapter.addPerson(new Person("Ivan", "Kozlov", R.drawable.enotik));
    }
}
