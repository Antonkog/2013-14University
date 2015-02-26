package com.example.android_servise;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
  
  final String LOG_TAG = "MySrv";
  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    
    public void onStart(View v)
    {
    		Intent intent = new Intent(this, SimpleService.class);
    		intent.putExtra("start_number", 3);
    		startService(intent);
    }
    
    public void onStop(View v) 
    {
      stopService(new Intent(this, SimpleService.class));
    }
    
    public void onShow(View v) 
    {
	    	ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
	    	List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
	
	    	for (int i = 0; i < rs.size(); i++) 
	    	{
	    		ActivityManager.RunningServiceInfo rsi = rs.get(i);
	    		Log.i(LOG_TAG, "Process: " + rsi.process + " CLASS: " + rsi.service.getClassName());
	    	}
    }
}
