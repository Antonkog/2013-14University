package com.example.android_widgetlist;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class ViewFactory implements RemoteViewsFactory 
{

  ArrayList<String> data;
  Context context;

  ViewFactory(Context ctx, Intent intent) 
  {
    context = ctx;
  }

  @Override
  public void onCreate() {
    data = new ArrayList<String>();
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public RemoteViews getLoadingView() {
    return null;
  }

  // Метод возвращает содержимое каждого подготовленного пункта списка
  @Override
  public RemoteViews getViewAt(int position) 
  {
	  // Заполнение пункта данными
    RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.item);
    view.setTextViewText(R.id.itemText, data.get(position));
    
    // Установка обработчика нажатия для каждого пункта списка
    Intent clickIntent = new Intent();
    clickIntent.putExtra(SimpleWidget.ITEM_POSITION, position);
    view.setOnClickFillInIntent(R.id.itemText, clickIntent);

    return view;
  }

  @Override
  public int getViewTypeCount() {
    return 1;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public void onDataSetChanged() {
    data.clear();
    for (int i = 0; i < 15; i++) 
    {
      data.add("Item " + i);
    }
  }

  @Override
  public void onDestroy() {

  }

}