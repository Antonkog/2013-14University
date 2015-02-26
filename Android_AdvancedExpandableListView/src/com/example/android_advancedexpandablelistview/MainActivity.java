package com.example.android_advancedexpandablelistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {

	final Context context = this;
    private ExpandableListView exListView;
    
    Garage garage = new Garage();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Company audi = new Company("Audi", R.drawable.audi_a4);
        audi.addCar(new Car("A4", R.drawable.audi_a4));
        audi.addCar(new Car("A6", R.drawable.audi_a4));
        audi.addCar(new Car("A8", R.drawable.audi_a4));
        garage.addCompany(audi);
        
        Company mercedes = new Company("Mercedes", R.drawable.bmw_m6);
        mercedes.addCar(new Car("S 600", R.drawable.bmw_m6));
        mercedes.addCar(new Car("E 350", R.drawable.bmw_m6));
        mercedes.addCar(new Car("A 250", R.drawable.bmw_m6));
        garage.addCompany(mercedes);
        
        Company toyota = new Company("Toyota", R.drawable.bmw_x6);
        toyota.addCar(new Car("Camry", R.drawable.bmw_x6));
        toyota.addCar(new Car("Corolla", R.drawable.bmw_x6));
        toyota.addCar(new Car("Celica", R.drawable.bmw_x6));
        garage.addCompany(toyota);
        
        Company honda = new Company("Honda", R.drawable.audi_q7);
        honda.addCar(new Car("Accord", R.drawable.audi_q7));
        honda.addCar(new Car("Civic", R.drawable.audi_q7));
        garage.addCompany(honda);
        
        exListView = (ExpandableListView)findViewById(R.id.expandableListView1);
        AutoListAdapter adapter = new AutoListAdapter(context, this, garage);
        exListView.setAdapter(adapter);
        
        adapter.addCompany("VW", R.drawable.audi_q7);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
