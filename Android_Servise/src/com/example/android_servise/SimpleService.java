package com.example.android_servise;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SimpleService extends Service 
{
  
  final String LOG_TAG = "MySrv";
  int number=0;

  public void onCreate() {
    super.onCreate();
    Log.d(LOG_TAG, "Service onCreate");
  }
  
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(LOG_TAG, "Service onStartCommand");
    
    number = intent.getIntExtra("start_number", 1);
    
    // Если сервис перезапущен, intent равен null
    if(intent!=null) task();
    
    // Режим, при котором Android перезапускает service, в случае аварийной остановки сервиса
    // (нехватка ресурсов, завшении стартовавшей активности)
    //return START_STICKY;
    
    // Режим, при котором Android НЕ перезапускает service
    return START_NOT_STICKY;
  }

  public void onDestroy() {
    super.onDestroy();
    Log.d(LOG_TAG, "Service onDestroy");
  }
  
  void task() 
  {  
	  new Thread(new Runnable() {
	      public void run() {
	        for (int i = number; i <= 10; i++) {
	          Log.d(LOG_TAG, "number = " + i);
	          try 
	          {
	        	  	TimeUnit.SECONDS.sleep(1);
	          } catch (InterruptedException e) 
	          {
	            e.printStackTrace();
	          }
	        }
	        stopSelf();
	      }
	    }).start();
  }

@Override
public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
}
  
}