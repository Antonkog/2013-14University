package com.example.android_alarmwidget;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.android_alarmwidget.R;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;

public class SimpleService extends Service  
{  
    @Override  
    public void onCreate()  
    {  
        super.onCreate();  
    }  
  
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId)  
    {  
        buildUpdate();  
        Log.d("Widget", "Service onStartCommand");
        
        new Thread(new Runnable() {
  	      public void run() {
  	        for (int i = 0; i <= 20; i++) {
  	          Log.d("Widget", "number = " + i);
  	          
  	          buildUpdate();
  	          
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
        
        return super.onStartCommand(intent, flags, startId);  
    }
    
    public void onDestroy() 
    {
        super.onDestroy();
        Log.d("Widget", "Service onDestroy");
    }
  
    private void buildUpdate()  
    {  
        String lastUpdated = DateFormat.format("h:mm:ss", new Date()).toString();  
  
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget);  
  
        view.setTextViewText(R.id.tv, lastUpdated);  
   
        ComponentName thisWidget = new ComponentName(this, ServiceWidget.class);  
        AppWidgetManager manager = AppWidgetManager.getInstance(this);  
        manager.updateAppWidget(thisWidget, view);
    }  
  
    @Override  
    public IBinder onBind(Intent intent)  
    {  
        return null;  
    }  
}
