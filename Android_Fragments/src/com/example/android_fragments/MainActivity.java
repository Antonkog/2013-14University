package com.example.android_fragments;

import com.example.android_fragments.Fragment2.Fragment2Listener;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, Fragment2Listener
{

  Button button1, button2;
  TextView textView1, textView2;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    textView1 = (TextView) this.findViewById(R.id.firstTextView);
    textView2 = (TextView) this.findViewById(R.id.secondTextView);
    
    button1 = (Button) this.findViewById(R.id.button1);
    button1.setOnClickListener(this);
    
    button2 = (Button) this.findViewById(R.id.button2);
    button2.setOnClickListener(this);
  }

@Override
public void onClick(View v) 
{
	if(v==button1)
	{
		// Обращение к элементам фрагмента из Activity
		Fragment frag2 = getFragmentManager().findFragmentById(R.id.fragment2);
		EditText edit1 = (EditText) frag2.getView().findViewById(R.id.editText1);
	    edit1.setText("Hello big world!!!");
	}
	if(v==button2)
	{
		// Создание фрагмента с параметрами
		//Fragment2 frag2 = new Fragment2();
	    Fragment frag2 = Fragment2.newInstance("It works!!!");
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.add(R.id.fragment2, frag2);
	    ft.commit();
	}
}

@Override
public void sendStr(Editable s, Editable s2) 
{
	textView1.setText(s);
	textView2.setText(s2);
}

}
