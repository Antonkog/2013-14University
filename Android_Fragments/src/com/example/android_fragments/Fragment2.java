package com.example.android_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Fragment2 extends Fragment implements OnClickListener
{
	
	public interface Fragment2Listener 
	{
		public void sendStr(Editable s, Editable s2);
	}
	  
	Fragment2Listener fr2Listener;

  Button sendButton, closeButton;
  EditText edit1, edit2;
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    
    // Проверка на реализацию слушателя и занесение в fr2Listener
    if(activity instanceof Fragment2Listener)
    		fr2Listener = (Fragment2Listener) activity;
    
    Log.d("Fragment2", "Fragment2 onAttach");
  }
  
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("Fragment2", "Fragment2 onCreate");
  }
  
  public static Fragment2 newInstance(String str) 
  {
	  Fragment2 myFragment = new Fragment2();
	  Bundle args = new Bundle();
	  args.putString("str", str);
	  myFragment.setArguments(args);
	  return myFragment;
  }
  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) 
  {
	  Log.d("Fragment2", "Fragment2 onCreateView");
    
	  View v = inflater.inflate(R.layout.fragment2, null);
	  
	  edit1 = (EditText) v.findViewById(R.id.editText1);
	  edit2 = (EditText) v.findViewById(R.id.editText2);
	  
	  sendButton = (Button) v.findViewById(R.id.button1);
	  sendButton.setOnClickListener(this);
	  
	  closeButton = (Button) v.findViewById(R.id.button2);
	  closeButton.setOnClickListener(this);
    
	  EditText edit1 = (EditText) v.findViewById(R.id.editText1);
	  String param1 = this.getArguments().getString("str");
	  if(param1!=null) edit1.setText(param1);
    
	  return v;
  }
  
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.d("Fragment2", "Fragment2 onActivityCreated");
  }
  
  public void onStart() {
    super.onStart();
    Log.d("Fragment2", "Fragment2 onStart");
  }
  
  public void onResume() {
    super.onResume();
    Log.d("Fragment2", "Fragment2 onResume");
  }
  
  public void onPause() {
    super.onPause();
    Log.d("Fragment2", "Fragment2 onPause");
  }
  
  public void onStop() {
    super.onStop();
    Log.d("Fragment2", "Fragment2 onStop");
  }
  
  public void onDestroyView() {
    super.onDestroyView();
    Log.d("Fragment2", "Fragment2 onDestroyView");
  }
  
  public void onDestroy() {
    super.onDestroy();
    Log.d("Fragment2", "Fragment2 onDestroy");
  }
  
  public void onDetach() {
    super.onDetach();
    Log.d("Fragment2", "Fragment2 onDetach");
  }

@Override
public void onClick(View v) {
	if(v==closeButton)
	{
		// Закрытие текущего фрагмента
		getActivity().getFragmentManager().beginTransaction().remove(this).commit();
	}
	if(v==sendButton)
	{
		// Обращение к контролам Activity из фрагмента
		//((TextView)getActivity().findViewById(R.id.firstTextView)).setText(edit1.getText());
		//((TextView)getActivity().findViewById(R.id.secondTextView)).setText(edit2.getText());
	
		// Вызов метода слушателя в активности
		if(fr2Listener!=null) fr2Listener.sendStr(edit1.getText(), edit2.getText());
	}
}
}