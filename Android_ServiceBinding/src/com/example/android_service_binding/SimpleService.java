package com.example.android_service_binding;

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
  int start_number=0;
  
  // �������� � ������������� ���������� ���������� IBinder
  SimpleBinder binder = new SimpleBinder();

  public void onCreate() {
    super.onCreate();
    Log.d(LOG_TAG, "Service onCreate");
  }
  
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(LOG_TAG, "Service onStartCommand");
    
    // ��������� ���������� ��������� �� ����������
    start_number = intent.getIntExtra("start_number", 1);
    
    // ������ ������ � �������
    if(intent!=null) task();
    
    return START_NOT_STICKY;
  }

  public void onDestroy() {
    super.onDestroy();
    Log.d(LOG_TAG, "Service onDestroy");
  }

  // ����� ���������� ��� ������������� �������� ���������� � �������
  // ����� ������� ������������������ ��������� IBinder
  public IBinder onBind(Intent intent) 
  {
	  Log.d(LOG_TAG, "Service onBind");
	  return binder;
  }

  // ��� ����������� �� ����� ������������
  public void onRebind(Intent intent) 
  {
	  super.onRebind(intent);
	  Log.d(LOG_TAG, "Service onRebind");
  }
	  
//��� ����������� �� ����� ������������
  public boolean onUnbind(Intent intent) {
	  Log.d(LOG_TAG, "Service onUnbind");
	  return super.onUnbind(intent);
  }
  
  void task() 
  {  
	  new Thread(new Runnable() {
	      public void run() {
	        for (number = start_number; number <= 20; number++) {
	          Log.d(LOG_TAG, "number = " + number);
	          try 
	          {
	        	  	TimeUnit.SECONDS.sleep(1);
	          } catch (InterruptedException e) 
	          {
	            e.printStackTrace();
	          }
	        }
	        // ��������� �������
	        stopSelf();
	      }
	    }).start();
  }
  
  // �����, ���������� ����������� � ������������ �������� �� �������
  int get_number()
  {
	  return number;
  }
  
  // ����� ����������� �����, ������������ ������ �� ������
  class SimpleBinder extends Binder 
  {
	  // �����, ������������ ������ �� ������
	  SimpleService getService() 
	  {
		  return SimpleService.this;
	  }
  }
  
}