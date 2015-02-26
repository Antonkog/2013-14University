package com.example.android_broadcastreceiver;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SimpleService extends Service 
{

  final String LOG_TAG = "MySrv";
  
  public void onCreate() 
  {
    super.onCreate();
    Log.d(LOG_TAG, "SimpleService onCreate");
  }

  public void onDestroy() {
    super.onDestroy();
    Log.d(LOG_TAG, "SimpleService onDestroy");
  }

  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(LOG_TAG, "SimpleService onStartCommand");

    int pause = intent.getIntExtra("pause", 1);

    MyRun mr = new MyRun(pause);
	Thread t = new Thread(mr, "Thread1");
	t.start();

    return super.onStartCommand(intent, flags, startId);
  }

  public IBinder onBind(Intent arg0) {
    return null;
  }

  class MyRun implements Runnable 
  {
    int pause;

    public MyRun(int pause) {
      this.pause = pause;
    }

    public void run() 
    {
      // Создание интента для широковещательной передачи
      Intent intent = new Intent("com.example.android_broadcastreceiver");
      Log.d(LOG_TAG, "SimpleService thread start...");
      try {

	    	  for(int i=0;i<5;i++)
	    	  {
	    		  intent.putExtra("number", i);
	    		  
	    		  // Начало передачи
	    		  sendBroadcast(intent);
	    	  }

	    	  TimeUnit.SECONDS.sleep(pause);

	    	  for(int i=5;i<10;i++)
	    	  {
	    		  intent.putExtra("number", i);
	    		  sendBroadcast(intent);
	    	  }

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      stopSelfResult(0);
      Log.d(LOG_TAG, "SimpleService stop");
    }
  }
}
