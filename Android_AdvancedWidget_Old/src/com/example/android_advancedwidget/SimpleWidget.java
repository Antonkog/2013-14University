package com.example.android_advancedwidget;

import java.util.Arrays;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
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
  // appWidgetIds - �������������� ���� ��������� SimpleWidget ��������
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
  {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
    
    // �������� ����� ����������
    SharedPreferences pref = context.getSharedPreferences(MainActivity.WIDGET_PREF, Context.MODE_PRIVATE);
    
    // ��������� ��� id ��������
    for (int id : appWidgetIds) 
    {
    	  // �������� ������� ������
      updateWidget(context, appWidgetManager, pref, id);
    }
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) 
  {
    super.onDeleted(context, appWidgetIds);
    Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));

    // ������� Preferences
    Editor editor = context.getSharedPreferences(MainActivity.WIDGET_PREF, Context.MODE_PRIVATE).edit();
    for (int widgetID : appWidgetIds) 
    {
    	  // ������� �������� �����
      editor.remove("text_param" + widgetID);
    }
    
    // ������������� ���������
    editor.commit();
  }

  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    Log.d(LOG_TAG, "onDisabled");
  }

  // ���������� ������ �������
  static void updateWidget(Context context, AppWidgetManager appWidgetManager, SharedPreferences pref, int widgetID) 
  {
    Log.d("Widget", "updateWidget " + widgetID);
    
    // �������� ���������� EditText ��� ������ ��������� �� id �������
    String widgetText = pref.getString("text_param" + widgetID, null);
    
    // ���� ��� ������ - �� ���!!!
    if (widgetText == null) return;
    
    // ���������� �������
    RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
    
    // ��������� ��� �������� �������
    widgetView.setTextViewText(R.id.tv, widgetText);
    
    // �������� ������ ���������
    appWidgetManager.updateAppWidget(widgetID, widgetView);
  }

}