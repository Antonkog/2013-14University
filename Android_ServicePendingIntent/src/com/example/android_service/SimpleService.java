package com.example.android_service;

import java.util.concurrent.TimeUnit;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SimpleService extends Service {

  final String LOG_TAG = "MySrv";

  public void onCreate() {
    super.onCreate();
    Log.d(LOG_TAG, "SimpleService onCreate");
  }

  public void onDestroy() {
    super.onDestroy();
    Log.d(LOG_TAG, "SimpleService onDestroy");
  }

  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(LOG_TAG, "SimpleService onStartCommand");

    // Получение параметров из активности
    int pause = intent.getIntExtra("pause", 1);
    
    // Получение объекта обратного вызова
    PendingIntent pi = intent.getParcelableExtra("pending_intent");

    // Старт задачи в отдельном потоке
    Task mt = new Task(pause, pi);
	Thread t = new Thread(mt, "Thread1");
	t.start();

    return super.onStartCommand(intent, flags, startId);
  }

  public IBinder onBind(Intent arg0) {
    return null;
  }

  class Task implements Runnable 
  {
    int pause;
    PendingIntent pIntent;

    public Task(int pause, PendingIntent pi) 
    {
      this.pause = pause;
      this.pIntent = pi;
    }

    public void run() {
      Log.d(LOG_TAG, "Thread started...");
      try {

    	  // Простая передача параметра в активность
    	  pIntent.send(1);

        for(int i=0;i<5;i++)
        {
	        TimeUnit.SECONDS.sleep(pause);
	
	        // Передача большого количества параметров
	        Intent intent = new Intent();
	        intent.putExtra("number", i);
	        pIntent.send(SimpleService.this, 3, intent);
        }
        
        pIntent.send(2);

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (CanceledException e) {
        e.printStackTrace();
      }
      Log.d(LOG_TAG, "SimpleService stop.");
      stopSelfResult(0);
    }
  }
}