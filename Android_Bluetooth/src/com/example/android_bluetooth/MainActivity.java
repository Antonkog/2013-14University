package com.example.android_bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{

   Button On, Off, Visible, list, scan;
   ListView lv;
   TextView messagesTextView;
   EditText messageEdit;
   String allMessages="";
   
   // Класс, который позволяет определить устройства с активным Bluetooth
   BluetoothAdapter bluetoothAdapter;
   
   // Множество спаренных устройств
   Set<BluetoothDevice>pairedDevices;
   
   // Получатель списка просканированных bluetooth-устройств
   BroadcastReceiver mReceiver;
   
   // Устройство, с которым происходит обмен информацией
   BluetoothDevice bluetoothDevice;
   
   // Задача, которая обменивается информацией
   ConnectedTask connectedThread;
   
   // Список обнаруженных во время сканирования устройств
   ArrayList<BluetoothDevice> devices;
   
   // Список имён обнаруженных во время сканирования устройств
   ArrayList<String> deviceNames;
   
   // Адаптер для вывода имён устройств в listview
   ArrayAdapter<String> adapter;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      On = (Button)findViewById(R.id.on);
      Off = (Button)findViewById(R.id.off);
      Visible = (Button)findViewById(R.id.make_visible);
      list = (Button)findViewById(R.id.device_list);
      scan = (Button)findViewById(R.id.scan);
      messagesTextView = (TextView)findViewById(R.id.messageTextView);
      messageEdit = (EditText)findViewById(R.id.messageEdit);
      lv = (ListView)findViewById(R.id.listView1);
      
      // Настройка адаптера для listview
      devices = new ArrayList<BluetoothDevice>();
      deviceNames = new ArrayList<String>();
      adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, deviceNames);
      lv.setAdapter(adapter);
      
      // Настройка слушателя нажатий для listview
      lv.setOnItemClickListener(new OnItemClickListener() 
      {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
          {
        	  	Toast.makeText(getApplicationContext(), devices.get(position).getName(), Toast.LENGTH_LONG).show();
        	  	messagesTextView.setText(devices.get(position).getName());
        	  	
        	  	// Выбор устройства с которым будет связь
        	  	bluetoothDevice = devices.get(position);
          }
        });

      // Создание адаптера для поиска устройств
      bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      
      // Настройка получателя устройств для соединения
      mReceiver = new BroadcastReceiver() 
      {
    	    public void onReceive(Context context, Intent intent) {
    	        String action = intent.getAction();
    	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
    	        		BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    	        		//Log.d("Bluetooth", device.getName() + "\n" + device.getAddress());
    	        		
    	        		// Добавление обнаруженных устройств в списки устройств
    	        		devices.add(device);
    	        		deviceNames.add(device.getName());
    	        		adapter.notifyDataSetChanged();
    	        		//Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_LONG).show();
    	        }
    	    }
    	};
    	
    	// Интент-фильтр для получения информации о Bluetooth устройствах
    	IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    	
    	// Регистрация получателя
    	registerReceiver(mReceiver, filter);
   }
   
   protected void onDestroy() 
   {
	    super.onDestroy();
	    
	    // Отмена регистрации получателя
	    unregisterReceiver(mReceiver);
   }

   public void on(View view){
      if (!bluetoothAdapter.isEnabled()) 
      {
    	 // Программное включение bluetooth
         Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         startActivityForResult(turnOn, 0);
         Toast.makeText(getApplicationContext(),"Turned on", Toast.LENGTH_LONG).show();
      }
      else Toast.makeText(getApplicationContext(),"Already on", Toast.LENGTH_LONG).show();

   }
   public void list(View view)
   {
	  // Получение списка спаренных устройств
      pairedDevices = bluetoothAdapter.getBondedDevices();

      ArrayList<String> list = new ArrayList<String>();
      for(BluetoothDevice bt : pairedDevices)
         list.add(bt.getName());

      Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
      final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
      lv.setAdapter(adapter);

   }
   
   public void scan(View view)
   {
	   // Старт сканирования Bluetooth-устройств
	   bluetoothAdapter.startDiscovery();
   }
   
   public void server(View view)
   {
	   // Запуск серверной задачи
	   new AcceptTask().execute();
   }
   
   public void client(View view)
   {
	   // запуск клиентской задачи
	   new ClientTask().execute(bluetoothDevice);
   }
   
   public void send(View view)
   {
	   // Получить строку из messageEdit
	   Editable message = messageEdit.getText();
	   
	   // Оправить по bluetooth туда...
	   connectedThread.write(message.toString());
	   messageEdit.setText("");
   }
   
   public void off(View view){
	  // Программное выключение bluetooth
	  bluetoothAdapter.disable();
      Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
   }
   
   // Разрешить нашему устройству быть видимым другими Bluetooth-устройствами
   public void visible(View view){
      Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
      startActivityForResult(getVisible, 0);
   }
   
   // Задача для ожидания соединения с клиентами
   class AcceptTask extends AsyncTask<Void, String, Void> 
   {
	   // Сокет для связи с клиентами
	   BluetoothServerSocket mmServerSocket; 
	   
	   @Override
	   protected void onPreExecute() 
	   {
	     super.onPreExecute();
	     BluetoothServerSocket tmp = null;
         try {
        	 // Создание и инициализация сокета для связи с клиентами
             tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Android_Bluetooth", UUID.fromString("FB694B90-F49E-4597-8306-171BBA78F846"));
         } catch (IOException e) { }
         mmServerSocket = tmp;
	   }

	   @Override
	   protected Void doInBackground(Void... params) 
	   {
		   // Сокет для обмена данными с клиентом
		   BluetoothSocket socket = null;
           while (true) {
               try {
            	   // Ожидать соединения с клиентом и вернуть сокет для передачи информации
                   socket = mmServerSocket.accept();

                   if (socket != null) 
                   {   	   
                	   // Запустить задачу по обмену информацией
                	   connectedThread = new ConnectedTask();
                	   connectedThread.execute(socket);
                	   		
                	   publishProgress("Connection accepted!");
                	   
                	   // Не искать больше клиентов
                	   mmServerSocket.close();
                   }
               } catch (Exception e) {
                   break;
               }
           }
	       return null;
	   }

	   @Override
	   protected void onProgressUpdate(String... values) 
	   {
	     super.onProgressUpdate(values);
	     Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
	   }

	   @Override
	   protected void onPostExecute(Void result) {
	     super.onPostExecute(result);
	   }
	   
	   public void cancel() 
	   {
           try {
        	   // Прекратить поиск клиентов
               mmServerSocket.close();
        } catch (IOException e) { }
      }
	
   }
   
   class ClientTask extends AsyncTask<BluetoothDevice, String, Void> 
   {
	   // Сокет для связи с сервером
	   BluetoothSocket mmSocket;
	   
	   // Устройство, с которым будет связь
	   BluetoothDevice mmDevice;
	   
	   @Override
	   protected void onPreExecute() 
	   {
	     super.onPreExecute();

	   }

	   @Override
	   protected Void doInBackground(BluetoothDevice... device) 
	   {
		   BluetoothSocket tmp = null;
	       mmDevice = device[0];
	       try {
	    	   // Сформировать сокет для связи с сервером
	           tmp = device[0].createRfcommSocketToServiceRecord(UUID.fromString("FB694B90-F49E-4597-8306-171BBA78F846"));
	       } catch (IOException e) { }
	       mmSocket = tmp;
	        
	       // Отменить сканирование, оно тормозит соединение
	       bluetoothAdapter.cancelDiscovery();
	 
	       try {
	    	   // Пытаться создать связь с сервером (поток стоит и ждёт)
	           mmSocket.connect();
	       } catch (IOException connectException) {
	           try {
	        	   // Всё пропало!
	               mmSocket.close();
	           } catch (IOException closeException) { }
	           return null;
	       }
	 
	       // Связь удалось установить. Запуск задачи для обмена информацией 
	       connectedThread = new ConnectedTask();
	       connectedThread.execute(mmSocket);
           publishProgress("Connection created!");

	       return null;
	   }

	   @Override
	   protected void onProgressUpdate(String... values) 
	   {
	     super.onProgressUpdate(values);
	     Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
	   }

	   @Override
	   protected void onPostExecute(Void result) {
	     super.onPostExecute(result);
	   }
	   
	   public void cancel() {
	      try {
	    	 // Прекратить поиск сервера для связи
	         mmSocket.close();
	      } catch (IOException e) { }
	  }
   }
   
   // Задача для обмена информацтей между сервером и клиентом
   class ConnectedTask extends AsyncTask<BluetoothSocket, String, Void> 
   {
	    // Сокет для передачи информации
	    BluetoothSocket mmSocket;
	    
	    // Поток для чтения информации
	    InputStream mmInStream;
	    
	    // Поток для записи информации
	    OutputStream mmOutStream;
	    
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();

	    }

	    // Чтение информации из сокета
	    @Override
	    protected Void doInBackground(BluetoothSocket... socket) {
	    	mmSocket = socket[0];
	    	
	    	// Получение потоков для обмена информацией из сокета
	        InputStream tmpIn = null;
	        OutputStream tmpOut = null;
	 
	        try {
	            tmpIn = socket[0].getInputStream();
	            tmpOut = socket[0].getOutputStream();
	        } catch (IOException e) { }
	 
	        mmInStream = tmpIn;
	        mmOutStream = tmpOut;
	        
	        // Цикл чтения информации
	        int bytes=0;
	        while (true) {
	            try {
	            	byte[] buffer = new byte[1024];
	            	
	            	// Читать в буфер
	                bytes = mmInStream.read(buffer);
	                
	                // Перевести байты в строку
	                String str = new String(buffer, "UTF-8");
	                
	                publishProgress(str);
	                
	            } catch (IOException e) {
	                break;
	            }
	        }
	        
	        return null;
	    }


	    @Override
	    protected void onProgressUpdate(String... values) 
	    {
	      super.onProgressUpdate(values);
	      //Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
	      
	      // Вывод полученного сообщения в messagesTextView
	      allMessages = allMessages + values[0]+"\n";
	      messagesTextView.setText(allMessages);
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	      super.onPostExecute(result);

	    }
	    
	    public void write(String message) {
	        try {
	        	// Преобразовать строку в массив байт
	        	byte[] bytes = message.getBytes("UTF-8");
	        	// Отправить на ту сторону
	            if(mmOutStream!=null) mmOutStream.write(bytes);
	        } catch (IOException e) { }
	    }
	 

	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }

	}
}
