package com.example.android_alarmwidget;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class AlarmServiceWidget extends AppWidgetProvider 
{
  final String LOG_TAG = "Widget";
  
  // PendingIntent ��� ������ �������
  private PendingIntent service = null;

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.d(LOG_TAG, "onEnabled");
  }

  @Override  
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)  
  {  
	  // �������� AlarmManager
      final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  

      // ������� �������
      final Calendar TIME = Calendar.getInstance();  
      TIME.set(Calendar.MINUTE, 0);  
      TIME.set(Calendar.SECOND, 0);  
      TIME.set(Calendar.MILLISECOND, 0);  

      // �������� ������� � �������� �������
      final Intent intent = new Intent(context, SimpleService.class);  

      if (service == null)  
      {  
    	  // �������� PendingIntent ��� �������
          service = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);  
      }  

      // ����� AlarmManager � ���������� 1 ���
      m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), 1000, service);
	  
      Log.d(LOG_TAG, "onUpdate");
  }
  
  public void onAppWidgetOptionsChanged (Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions)
  {
	  Log.d(LOG_TAG, "onAppWidgetOptionsChanged");
  }

  @Override  
  public void onDisabled(Context context)  
  {  
	  // ���������� �������
      final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);  
      Intent i = new Intent(context, SimpleService.class);  
      if (service == null)  
      {  
          service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);  
      }
      m.cancel(service);
      
      Log.d(LOG_TAG, "onDisabled");
  } 

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) 
  {
    super.onDeleted(context, appWidgetIds);
    Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
  }

}