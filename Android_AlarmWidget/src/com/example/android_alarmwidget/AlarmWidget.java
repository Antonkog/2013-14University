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
	  // Создание интента для AlarmWidget
	  Intent intent = new Intent(context, AlarmWidget.class);
	  
	  // Задание Action для интента
	  intent.setAction("update_widgets");
	  
	  // Создание PendingIntent для Broadcast рассылки
	  PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	  
	  // Создание AlarmManager
	  AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	  
	  // Запуск с интервалом 1 сек.
	  alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000, pIntent);
	  
      Log.d(LOG_TAG, "onUpdate");
  }  

  @Override  
  public void onDisabled(Context context)  
  {   
	  // Выключение AlarmManager
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
  
  // Метод запускается в ответ на Broadcast сообщения
  @Override
  public void onReceive(Context context, Intent intent) 
  {
	  super.onReceive(context, intent);
	  
	  // Проверка на получение определённого интента
	  if (intent.getAction().equalsIgnoreCase("update_widgets")) 
	  {
		  // Получение компонента для текущего виджета
			ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
			
			// Получение AppWidgetManager для обновления виджета
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
			for (int appWidgetID : ids) 
			{
				// Обновить виджет
				updateWidget(context, appWidgetManager, appWidgetID);
				Log.d(LOG_TAG, "onReceive!!!");
			}
	  }
  }
  
  // Обновление виджета
  static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetID) 
  {
		String lastUpdated = DateFormat.format("h:mm:ss", new Date()).toString();  
		RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
		widgetView.setTextViewText(R.id.textView1, lastUpdated);
		
		appWidgetManager.updateAppWidget(widgetID, widgetView);
  }

}