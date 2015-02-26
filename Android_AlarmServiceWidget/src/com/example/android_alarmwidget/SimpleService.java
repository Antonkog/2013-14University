package com.example.android_alarmwidget;

import java.util.Date;

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
        return super.onStartCommand(intent, flags, startId);  
    }
    
    public void onDestroy() 
    {
        super.onDestroy();
        Log.d("Widget", "Service onDestroy");
    }
  
    // ����� ���������� ��� ������ AlarmManager
    private void buildUpdate()  
    {  
    	// ��������� �������� �������
        String lastUpdated = DateFormat.format("h:mm:ss", new Date()).toString();  
  
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget);  
  
        view.setTextViewText(R.id.tv, lastUpdated);  
   
        // ��������� ������� � ������� �� �������
        ComponentName thisWidget = new ComponentName(this, AlarmServiceWidget.class);  
        AppWidgetManager manager = AppWidgetManager.getInstance(this);  
        
        // ���������� �������
        manager.updateAppWidget(thisWidget, view);
        
        // ��������� �������
        this.stopSelf();
    }  
  
    @Override  
    public IBinder onBind(Intent intent)  
    {  
        return null;  
    }  
}
