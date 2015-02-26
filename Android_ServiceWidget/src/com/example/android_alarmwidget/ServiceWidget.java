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

public class ServiceWidget extends AppWidgetProvider 
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
	  Intent intent = new Intent(context, SimpleService.class); 
	  context.startService(intent);
	  
      Log.d(LOG_TAG, "onUpdate");
  }  

  @Override  
  public void onDisabled(Context context)  
  {   
      Log.d(LOG_TAG, "onDisabled");
  } 

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) 
  {
    super.onDeleted(context, appWidgetIds);
    Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
  }

}