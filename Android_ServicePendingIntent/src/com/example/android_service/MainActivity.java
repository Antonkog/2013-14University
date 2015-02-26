package com.example.android_service;

import java.util.List;

import com.example.android_servise.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
  
  final String LOG_TAG = "MySrv";
  SimpleService service;
  
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);       
    }
    
    public void onStart(View v)
    {
    	// Создание объекта, содержащего обратный вызов
    	// 1 - номер запроса
    	// 2 - intent, способный нести доп. параметры
    	// 3 - флаги
        PendingIntent pi = createPendingResult(144, new Intent(), 0);

        // Передача параметров в сервис
        Intent intent = new Intent(this, SimpleService.class);
        
        // Пауза между вызовами активности
        intent.putExtra("pause", 2);
        intent.putExtra("pending_intent", pi);

        // Старт сервиса
        startService(intent);
    }
    
    public void onStop(View v) 
    {
      stopService(new Intent(this, SimpleService.class));
    }
    
    public void onBind(View v) 
    {
    		return;
    }
    
    // Получение результата из сервиса
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
      super.onActivityResult(requestCode, resultCode, intent);
      
      // Проверить наличие дополнительного параметра
      if(intent.hasExtra("number"))
      {
    	  //Подучение дополнительного параметра
    	  int param = intent.getIntExtra("number", 1);
    	  Log.d(LOG_TAG, "onActivityResult param = " + param);
      }
    	  else
    	  	Log.d(LOG_TAG, "onActivityResult resultCode = " + resultCode + ", requestCode = " + requestCode);
    }
}
