package com.example.android_notification;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onSimpleClick(View v) 
	{
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    int icon = android.R.drawable.sym_action_email;
	    Bitmap enotik = BitmapFactory.decodeResource(getResources(), R.drawable.enotik);
	    
	    // —оздание уведомлени€ до API11
	    // ѕараметры - икона уведомлени€, текст и врем€ всплыти€
	    // long when = System.currentTimeMillis();
	    // Notification notification = new Notification(icon, tickerText, when);
	    
	    Context context = getApplicationContext();
	    Notification notification = new Notification.Builder(context)
        .setContentTitle("Простое уведомление")
        .setContentText("«десь могла бы быть ваша реклама!")
        .setSmallIcon(icon)
        .setLargeIcon(enotik)
        .build();
	    
	    mNotificationManager.notify(1, notification);
	}
	
	public void onAdvancedClick(View v) 
	{
		// «агрузка иконок и картинок
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 
	    Bitmap enotik = BitmapFactory.decodeResource(getResources(), R.drawable.enotik);

	    // —оздание уведомлени€
	    Context context = getApplicationContext();
	    Notification notification = new Notification.Builder(context)
        .setContentTitle("Custom уведомление")
        .setContentText("“екст уведомлени€")
        .setSmallIcon(R.drawable.ic_launcher)
        .setLargeIcon(enotik)
        .build();
	    
	    // «агрузка пользовательского шаблона уведомлени€
	    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notify);
	    contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
	    contentView.setTextViewText(R.id.text,"ѕривет, мир!!!");
	    
	    // ”становка пользовательского шаблона уведомлени€
	    notification.contentView = contentView;
	    
	    // Ќастройка перехода из уведомлени€ в активность
	    Intent notificationIntent = new Intent(this, MainActivity.class);
	    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	    notification.contentIntent = contentIntent;
	    
	    // »грать музычку
	    notification.defaults |= Notification.DEFAULT_SOUND;
	    
	    // ¬ибрировать
	    notification.defaults |= Notification.DEFAULT_VIBRATE;
	    
	    // ћигать огоньками
	    notification.defaults |= Notification.DEFAULT_LIGHTS;
	    
	    // 1 параметр - врем€ до запуска вибрации
	    // 2,3,4 - времена вибраций (может быть много чисел)
	    long[] vibrate = {0,100,200,300};
	    notification.vibrate = vibrate;
	    
	    // цвет дл€ RGB индикатора.
	    notification.ledARGB = 0xff00ff00; 
	    
	    // врем€ между мигани€ми
	    notification.ledOnMS = 300; 
	    
	    // врем€, через которое индикатор потухнет
	    notification.ledOffMS = 1000;
	    
	    // включить мигание
	    notification.flags |= Notification.FLAG_SHOW_LIGHTS; 
	    
	    mNotificationManager.notify(1, notification);
	}

}
