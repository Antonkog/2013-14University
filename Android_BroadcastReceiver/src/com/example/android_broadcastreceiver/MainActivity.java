package com.example.android_broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity 
{

  final String LOG_TAG = "MySrv";
  
  // ���������� ���������
  BroadcastReceiver broadcastResiever;

  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // �������� ����������� ���������
    broadcastResiever = new BroadcastReceiver() 
    {
    	// ����� ����������
	    public void onReceive(Context context, Intent intent) 
	    {
	    	// ��������� ��������� �� �������
		    int number = intent.getIntExtra("number", 0);
		    Log.d(LOG_TAG, "onReceive: number = " + number);
	    }
    };

    // �������� ������� ��� ���������
    IntentFilter intentFilter = new IntentFilter("com.example.android_broadcastreceiver");
    
    // ����������� ����������
    registerReceiver(broadcastResiever, intentFilter);
  }
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // ������ �����������
    unregisterReceiver(broadcastResiever);
  }

  public void onClickStart(View v) {
    Intent intent = new Intent(this, SimpleService.class);
    intent.putExtra("pause", 3);

    // ����� ������� � ��������� ����������
    startService(intent);
  }

}
