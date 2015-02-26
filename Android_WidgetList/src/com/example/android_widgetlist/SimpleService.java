package com.example.android_widgetlist;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class SimpleService extends RemoteViewsService 
{
	
  @Override
  // Метод запускается автоматически при связывании списка и адаптера
  public RemoteViewsFactory onGetViewFactory(Intent intent) 
  {
    return new ViewFactory(getApplicationContext(), intent);
  }
  
  public void onCreate() {
	    super.onCreate();
	    Log.d("Widget", "SimpleService onCreate");
	  }

	  public void onDestroy() {
	    super.onDestroy();
	    Log.d("Widget", "SimpleService onDestroy");
	  }

}
