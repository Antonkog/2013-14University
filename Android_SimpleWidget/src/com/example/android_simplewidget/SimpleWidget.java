package com.example.android_simplewidget;

import java.util.Arrays;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SimpleWidget extends AppWidgetProvider 
{
  final String LOG_TAG = "Widget";

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.d(LOG_TAG, "onEnabled");
    Toast.makeText(context, "Widget enabled!!!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) 
  {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    Log.d(LOG_TAG, "onUpdate ");
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) 
  {
    super.onDeleted(context, appWidgetIds);
    Log.d(LOG_TAG, "onDeleted ");
  }

  @Override
  public void onDisabled(Context context) 
  {
    super.onDisabled(context);
    Log.d(LOG_TAG, "onDisabled");
  }

}