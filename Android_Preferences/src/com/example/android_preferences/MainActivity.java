package com.example.android_preferences;

import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText nameEdit, lastNameEdit, txtEdit;
	SharedPreferences settings;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = getApplicationContext();
		
		nameEdit = (EditText) this.findViewById(R.id.editText1);
		lastNameEdit = (EditText) this.findViewById(R.id.editText2);
		txtEdit = (EditText) this.findViewById(R.id.editText3);
		
		// инициализация настроек
		settings = getSharedPreferences("my_application", Context.MODE_PRIVATE);
		
		// проверка и получение по ключу
		if(settings.contains("name")) {
		    String name = settings.getString("name", "default");
		    nameEdit.setText(name);
		}
		
		if(settings.contains("lastName")) {
		    String lastname = settings.getString("lastName", "default");
		    lastNameEdit.setText(lastname);
		}
		
		if(settings.contains("txt")) {
			Set<String> ret = settings.getStringSet("txt", new HashSet<String>());
			String txt = "";
			for(String r : ret) {
			    txt = txt + "\n" + r;
			}
		    txtEdit.setText(txt);
		}
	}
	
	@Override
	protected void onStop() {
	    super.onStop();
	    
	    settings = getSharedPreferences("my_application", Context.MODE_PRIVATE);
	    
	    String name = nameEdit.getText().toString();
	    String lastname = lastNameEdit.getText().toString();
	    String txt = txtEdit.getText().toString();
	    
	    // Сохранение по ключу
	    Editor editor = settings.edit();
		editor.putString("name", name);
		editor.putString("lastName", lastname);
		
		Set<String> linesSet = new HashSet<String>();
		String [] lines = txt.split("\n");
		for(String s : lines)
		{
			linesSet.add(s);
		}
		editor.putStringSet("txt", linesSet);
		
		// Сохранение настроек
		editor.commit();
	    
	    Log.d("Stop!", "MainActivity: onStop()");
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    Log.d("Start", "MainActivity: onStart()");
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    Log.d("Resume", "MainActivity: onResume()");
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    Log.d("Pause", "MainActivity: onPause()");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
