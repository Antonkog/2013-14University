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
	// ������������� �������� ������� (�� ��������� ����� ���������� id)
	int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	// �������������� ������
	Intent result;
	  
	final String LOG_TAG = "Widget";

	// �������� ����� ��� intent
	public final static String WIDGET_PREF = "widget_pref";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    Log.d(LOG_TAG, "onCreate config");

	    // �������� ��������� ������ �� ������� 
	    Intent intent = getIntent();
	    Bundle extras = intent.getExtras();
	    
	    // ������� �������� id �������
	    if (extras != null) 
	    		widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

	    // ���� id ������� ������������, �� ���������
	    if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
	    		finish();
	    
	    // �������� ��������������� �������
	    result = new Intent();
	    
	    // ������ � ���� id �������
	    result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
	    
	    // ������ ���������� ���������� �� ��������� (�� ������ ������, ������� ������� �������� �������)
	    setResult(RESULT_CANCELED, result);
	    
	    setContentView(R.layout.activity_main);
	}
	  
	public void onClick(View v) 
	{
		// ��������� ������ EditText
	    EditText etText = (EditText) findViewById(R.id.etText);

	    // ������� ������ ����� ��� ����������
	    SharedPreferences pref = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
	    
	    Editor editor = pref.edit();
	    
	    // �������� ���������� EditText � �����
	    editor.putString("text_param" + widgetID, etText.getText().toString());
	    editor.commit();
	    
	    // ������������� �������� ������
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
	    SimpleWidget.updateWidget(this, appWidgetManager, pref, widgetID);

	    // ������� ������������� ��������� �������� (������ �����������)
	    setResult(RESULT_OK, result);
	    
	    Log.d(LOG_TAG, "finish config " + widgetID);
	    finish();
	}

}
