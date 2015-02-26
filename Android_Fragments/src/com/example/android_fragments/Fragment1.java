package com.example.android_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("Fragment1", "Fragment1 onCreate");
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.d("Fragment1", "Fragment1 onCreateView");
    return inflater.inflate(R.layout.fragment1, null);
  }

  public void onDestroyView() {
    super.onDestroyView();
    Log.d("Fragment1", "Fragment1 onDestroyView");
  }

  public void onDestroy() {
    super.onDestroy();
    Log.d("Fragment1", "Fragment1 onDestroy");
  }

}