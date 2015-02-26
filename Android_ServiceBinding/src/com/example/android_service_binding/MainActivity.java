package com.example.android_service_binding;

import java.util.List;

import com.example.android_servise.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
  
  final String LOG_TAG = "MySrv";
  
  // Класс, получающий информацию о соединении
  ServiceConnection servCon;
  Intent servIntent;
  
  // Ссылка на сервис для получения параметров
  SimpleService service;
  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        servIntent = new Intent("com.example.android_service_binding.ServiceBinding");
        
        servCon = new ServiceConnection() 
        {
			@Override
			// Метод запускается во время привязки к сервису
			public void onServiceConnected(ComponentName arg0, IBinder binder) {
				Log.d(LOG_TAG, "Service onServiceConnected");
				
				// Получение ссылки на сервис через объект IBinder
				SimpleService.SimpleBinder b = (SimpleService.SimpleBinder) binder;
				service = b.getService(); 
			}

			@Override
			// Метод запускается во время отсоединения от сервиса
			public void onServiceDisconnected(ComponentName arg0) {
				Log.d(LOG_TAG, "Service onServiceDisconnected");
			}

        };
        
    }
    
    public void onStart(View v)
    {
    	// Задание параметра для отсылки на сервис
    	Intent intent = new Intent(this, SimpleService.class);
    	intent.putExtra("start_number", 3);
    	
    	// Старт сервиса
    	startService(intent);
    }
    
    public void onStop(View v) 
    {
    	// Остановка сервиса
    	stopService(new Intent(this, SimpleService.class));
    }
    
    public void onBind(View v) 
    {
    	Log.d(LOG_TAG, "Service try bind");
    	
    	// Начало привязки активности к сервису
    	bindService(servIntent, servCon, BIND_AUTO_CREATE);
    }
    
    public void onUnBind(View v) 
    {
    	// Отсоединиться от сервиса
    	unbindService(servCon);
    }
    
    // ПОЛУЧИТЬ параметр с сервиса
    public void onGetNumber(View v) 
    {
    	// Метод get_number вызывается на сервисе!!!!!
    	int number = service.get_number();
    	Log.d(LOG_TAG, "Activity get number: " + number);
    }
    
    // Получить список сервисов
    public void onShow(View v) 
    {
	    ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
	    List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
	
	    for (int i = 0; i < rs.size(); i++) 
	    {
	    	ActivityManager.RunningServiceInfo rsi = rs.get(i);
	    	Log.i(LOG_TAG, "Process: " + rsi.process + " CLASS: " + rsi.service.getClassName());
	    }
    }
}
