package com.example.android_checkconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MainActivity extends Activity implements OnClickListener
{

	Button checkButton, callButton, smsButton, sendRequestButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkButton = (Button) this.findViewById(R.id.checkButton);
		checkButton.setOnClickListener(this);
		
		callButton = (Button) this.findViewById(R.id.callButton);
		callButton.setOnClickListener(this);
		
		smsButton = (Button) this.findViewById(R.id.smsButton);
		smsButton.setOnClickListener(this);
		
		sendRequestButton = (Button) this.findViewById(R.id.sendRequestButton);
		sendRequestButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	public void onClick(View view) 
	{
		switch(view.getId())
		{
			case R.id.checkButton:
				if(CheckConnection.isConnected(this)) Log.d("Connection", "CONNECTED");
				else Log.d("Connection", "NOT CONNECTED");
				
				if(CheckConnection.isConnectedWifi(this)) Log.d("Connection", "CONNECTED Wifi");
				else Log.d("Connection", "NOT CONNECTED Wifi");
				
				if(CheckConnection.isConnectedMobile(this)) Log.d("Connection", "CONNECTED Mobile");
				else Log.d("Connection", "NOT CONNECTED Mobile");
				
				if(CheckConnection.isConnectedFast(this)) Log.d("Connection", "CONNECTED FAST");
				else Log.d("Connection", "CONNECTED SLOW");
				break;
			case R.id.callButton:
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + "5551234567"));
				startActivity(intent);
				break;
			case R.id.smsButton:
				intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("sms:" + "5551234567"));
				startActivity(intent);
				
				//SmsManager sms = SmsManager.getDefault();
			    //sms.sendTextMessage("5551234567", null, "Hello world!!!", null, null);
				break;
				
			case R.id.sendRequestButton:
				new Connection().execute();
				break;
		}
	}

}

class Connection extends AsyncTask<Object, Object, Object> 
{
        protected Object doInBackground(Object... arg0) 
        {
            connect();
            return null;
        }
        
        // Чтение из интернет-потока в StringBuilder
        private StringBuilder inputStreamToString(InputStream is)
        {
    	    String line = "";
    	    StringBuilder total = new StringBuilder();
    	    
    	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    	    try
    	    {
    		    while ((line = rd.readLine()) != null) 
    		    { 
    		        total.append(line); 
    		    }
    	    } catch(Exception ex){}
    	    
    	    return total;
    	}

    private void connect() 
    {
    	// Получить класс для выполнения запроса на сервер
    	HttpClient httpclient = new DefaultHttpClient();
    	
    	// Создать экземпляр запроса на сервер
	    HttpPost httppost = new HttpPost("http://ya.ru");
	    try 
	    {
	    	// Задать список параметров
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
	        nameValuePairs.add(new BasicNameValuePair("name", "Dmitry"));
	        
	        // Прикрепить параметры к запросу
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        
	        // Выполнить запрос на сервер и получить ответ от сервера
	        HttpResponse response = httpclient.execute(httppost);
	        
	        // Получить контент с сервера
	        StringBuilder content = inputStreamToString(response.getEntity().getContent());
	        Log.d("Connection", content.toString());
	    } 
	    catch (ClientProtocolException e) {
	    	Log.d("Connection", e.toString());

	    } 
	    catch (IOException e) {
	    	Log.d("Connection", e.toString());
	    }
    }
}
