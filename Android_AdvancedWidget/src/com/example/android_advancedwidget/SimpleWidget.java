package com.example.android_advancedwidget;

import java.util.Arrays;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SimpleWidget extends AppWidgetProvider 
{
  final String LOG_TAG = "Widget";

  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.d(LOG_TAG, "onEnabled");
  }

  @Override
  // appWidgetIds - идентификаторы всех созданных SimpleWidget виджетов
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
  {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    
    // Получить опции приложениЯ
    SharedPreferences pref = context.getSharedPreferences(MainActivity.WIDGET_PREF, Context.MODE_PRIVATE);
    
    // Перебрать все id виджетов
    for (int id : appWidgetIds) 
    {
    	  // Обновить текущий виджет
      updateWidget(context, appWidgetManager, pref, id);
    }
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) 
  {
    super.onDeleted(context, appWidgetIds);
    Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));

    // Удаляем Preferences
    Editor editor = context.getSharedPreferences(MainActivity.WIDGET_PREF, Context.MODE_PRIVATE).edit();
    for (int widgetID : appWidgetIds) 
    {
    	// Очистка текущего ключа
    	editor.remove("text_param" + widgetID);
    }
    
    // Зафиксировать изменения
    editor.commit();
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    Log.d(LOG_TAG, "onDisabled");
  }

  // Обновление одного виджета
  static void updateWidget(Context context, AppWidgetManager appWidgetManager, SharedPreferences pref, int widgetID) 
  {
    Log.d("Widget", "updateWidget " + widgetID);
    
    // Получить содержимое EditText длЯ одного заданного по id виджета
    String widgetText = pref.getString("text_param" + widgetID, null);
    
    // Если нет такого - то нет!!!
    if (widgetText == null) return;
    
    // Обновление виджета
    RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
    
    // Заполнить все контролы виджета
    widgetView.setTextViewText(R.id.tv, widgetText);
    
    // Настройка параметров запускаемой по тапу активности
    Intent configIntent = new Intent(context, MainActivity.class);
    configIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
    configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
    PendingIntent pIntent = PendingIntent.getActivity(context, widgetID, configIntent, 0);
    
    // Установка PendingIntent для тапа по текстовому полю
    widgetView.setOnClickPendingIntent(R.id.tv, pIntent);
    
    // Обновить виджет полностью
    appWidgetManager.updateAppWidget(widgetID, widgetView);
  }

}