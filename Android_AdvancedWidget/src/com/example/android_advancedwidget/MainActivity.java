package com.example.android_advancedwidget;

import android.os.Bundle;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity 
{
	// »дентификатор текущего виджета (по умолчанию равен ошибочному id)
	int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	// –езультируюций интент
	Intent result;
	  
	final String LOG_TAG = "Widget";

	// Ќазвание ключа дл€ intent
	public final static String WIDGET_PREF = "widget_pref";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    Log.d(LOG_TAG, "onCreate config");

	    // Получить стартовый интент из виджета 
	    Intent intent = getIntent();
	    Bundle extras = intent.getExtras();
	    
	    // Попытка получить id виджета
	    if (extras != null) 
	    		widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

	    // Если id виджета некорректный, то закрыться
	    if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
	    		finish();
	    
	    // Создание результирующего интента
	    result = new Intent();
	    
	    // Запись в него id виджета
	    result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
	    
	    // Запись неудачного результата по умолчанию (на всякий случай, который отменит загрузку виджета)
	    setResult(RESULT_CANCELED, result);
	    
	    setContentView(R.layout.activity_main);
	}
	  
	public void onClick(View v) 
	{
		// Получение ссылки EditText
	    EditText etText = (EditText) findViewById(R.id.etText);

	    // —оздать объект опций для приложения
	    SharedPreferences pref = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
	    
	    Editor editor = pref.edit();
	    
	    // Записать содержимое EditText в опции
	    editor.putString("text_param" + widgetID, etText.getText().toString());
	    editor.commit();
	    
	    // ѕринудительно обновить виджет
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
	    SimpleWidget.updateWidget(this, appWidgetManager, pref, widgetID);

	    // Указать положительный результат настроек (виджет запуститься)
	    setResult(RESULT_OK, result);
	    
	    Log.d(LOG_TAG, "finish config " + widgetID);
	    finish();
	}

}
