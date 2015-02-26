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
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class AlarmWidget extends AppWidgetProvider 
{
  final String LOG_TAG = "Widget";

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.d(LOG_TAG, "onEnabled");
  }

  @Override  
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)  
  {   
	  // �������� ������� ��� AlarmWidget
	  Intent intent = new Intent(context, AlarmWidget.class);
	  
	  // ������� Action ��� �������
	  intent.setAction("update_widgets");
	  
	  // �������� PendingIntent ��� Broadcast ��������
	  PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	  
	  // �������� AlarmManager
	  AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	  
	  // ������ � ���������� 1 ���.
	  alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000, pIntent);
	  
      Log.d(LOG_TAG, "onUpdate");
  }  

  @Override  
  public void onDisabled(Context context)  
  {   
	  // ���������� AlarmManager
	  Intent intent = new Intent(context, AlarmWidget.class);
	  intent.setAction("update_widgets");
	  PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	  AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	  alarmManager.cancel(pIntent);
	  
      Log.d(LOG_TAG, "onDisabled");
  } 

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) 
  {
    super.onDeleted(context, appWidgetIds);
    Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
  }
  
  // ����� ����������� � ����� �� Broadcast ���������
  @Override
  public void onReceive(Context context, Intent intent) 
  {
	  super.onReceive(context, intent);
	  
	  // �������� �� ��������� ������������ �������
	  if (intent.getAction().equalsIgnoreCase("update_widgets")) 
	  {
		  // ��������� ���������� ��� �������� �������
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
			
			// ��������� AppWidgetManager ��� ���������� �������
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
			for (int appWidgetID : ids) 
			{
				// �������� ������
				updateWidget(context, appWidgetManager, appWidgetID);
				Log.d(LOG_TAG, "onReceive!!!");
			}
	  }
  }
  
  // ���������� �������
  static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetID) 
  {
		String lastUpdated = DateFormat.format("h:mm:ss", new Date()).toString();  
		RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
		widgetView.setTextViewText(R.id.textView1, lastUpdated);
		
		appWidgetManager.updateAppWidget(widgetID, widgetView);
  }

}