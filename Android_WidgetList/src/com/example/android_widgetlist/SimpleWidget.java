package com.example.android_widgetlist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SimpleWidget extends AppWidgetProvider 
{

	final String ACTION_ON_CLICK = "_action";
	final static String ITEM_POSITION = "_item_position";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
	{
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int i : appWidgetIds) 
		{
			updateWidget(context, appWidgetManager, i);
		}
	}

	void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) 
	{
	    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

	    // Указание показываемого списка для виджета
	    // Создание интента для задания сервиса, возвращающего адаптер для списка
	    Intent adapter = new Intent(context, SimpleService.class);
		adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		
		// Задание адаптера для списка в разметке (создаётся новый сервис, возвращающий адаптер для списка)
		views.setRemoteAdapter(R.id.listView1, adapter);

		// Задание реакции на клик по пункту списка
		Intent listClickIntent = new Intent(context, SimpleWidget.class);
	    listClickIntent.setAction(ACTION_ON_CLICK);
	    
	    // Создание PendingIntent для бродкаста о нажатии на пункт списка
	    PendingIntent listClickPIntent = PendingIntent.getBroadcast(context, 0, listClickIntent, 0);
	    
	    // Задание PendingIntent для listView
	    views.setPendingIntentTemplate(R.id.listView1, listClickPIntent);

	    appWidgetManager.updateAppWidget(appWidgetId, views);
	}
  
	// Метод запускается в ответ на нажатие на пункт списка
  @Override
  public void onReceive(Context context, Intent intent) 
  {
    super.onReceive(context, intent);
    
    // Проверка полученного intent
    if (intent.getAction().equalsIgnoreCase(ACTION_ON_CLICK)) 
    {
    	// Получение номера пункта списка, по которому нажали
      int itemPos = intent.getIntExtra(ITEM_POSITION, -1);
      if (itemPos != -1) 
      {
        Toast.makeText(context, "Clicked on item " + itemPos, Toast.LENGTH_SHORT).show();
      }
    }
  }

}
