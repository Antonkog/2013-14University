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
   
   // �����, ������� ��������� ���������� ���������� � �������� Bluetooth
   BluetoothAdapter bluetoothAdapter;
   
   // ��������� ��������� ���������
   Set<BluetoothDevice>pairedDevices;
   
   // ���������� ������ ���������������� bluetooth-���������
   BroadcastReceiver mReceiver;
   
   // ����������, � ������� ���������� ����� �����������
   BluetoothDevice bluetoothDevice;
   
   // ������, ������� ������������ �����������
   ConnectedTask connectedThread;
   
   // ������ ������������ �� ����� ������������ ���������
   ArrayList<BluetoothDevice> devices;
   
   // ������ ��� ������������ �� ����� ������������ ���������
   ArrayList<String> deviceNames;
   
   // ������� ��� ������ ��� ��������� � listview
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
      
      // ��������� �������� ��� listview
      devices = new ArrayList<BluetoothDevice>();
      deviceNames = new ArrayList<String>();
      adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, deviceNames);
      lv.setAdapter(adapter);
      
      // ��������� ��������� ������� ��� listview
      lv.setOnItemClickListener(new OnItemClickListener() 
      {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
          {
        	  	Toast.makeText(getApplicationContext(), devices.get(position).getName(), Toast.LENGTH_LONG).show();
        	  	messagesTextView.setText(devices.get(position).getName());
        	  	
        	  	// ����� ���������� � ������� ����� �����
        	  	bluetoothDevice = devices.get(position);
          }
        });

      // �������� �������� ��� ������ ���������
      bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      
      // ��������� ���������� ��������� ��� ����������
      mReceiver = new BroadcastReceiver() 
      {
    	    public void onReceive(Context context, Intent intent) {
    	        String action = intent.getAction();
    	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
    	        		BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    	        		//Log.d("Bluetooth", device.getName() + "\n" + device.getAddress());
    	        		
    	        		// ���������� ������������ ��������� � ������ ���������
    	        		devices.add(device);
    	        		deviceNames.add(device.getName());
    	        		adapter.notifyDataSetChanged();
    	        		//Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_LONG).show();
    	        }
    	    }
    	};
    	
    	// ������-������ ��� ��������� ���������� � Bluetooth �����������
    	IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    	
    	// ����������� ����������
    	registerReceiver(mReceiver, filter);
   }
   
   protected void onDestroy() 
   {
	    super.onDestroy();
	    
	    // ������ ����������� ����������
	    unregisterReceiver(mReceiver);
   }

   public void on(View view){
      if (!bluetoothAdapter.isEnabled()) 
      {
    	 // ����������� ��������� bluetooth
         Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
         startActivityForResult(turnOn, 0);
         Toast.makeText(getApplicationContext(),"Turned on", Toast.LENGTH_LONG).show();
      }
      else Toast.makeText(getApplicationContext(),"Already on", Toast.LENGTH_LONG).show();

   }
   public void list(View view)
   {
	  // ��������� ������ ��������� ���������
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
	   // ����� ������������ Bluetooth-���������
	   bluetoothAdapter.startDiscovery();
   }
   
   public void server(View view)
   {
	   // ������ ��������� ������
	   new AcceptTask().execute();
   }
   
   public void client(View view)
   {
	   // ������ ���������� ������
	   new ClientTask().execute(bluetoothDevice);
   }
   
   public void send(View view)
   {
	   // �������� ������ �� messageEdit
	   Editable message = messageEdit.getText();
	   
	   // �������� �� bluetooth ����...
	   connectedThread.write(message.toString());
	   messageEdit.setText("");
   }
   
   public void off(View view){
	  // ����������� ���������� bluetooth
	  bluetoothAdapter.disable();
      Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
   }
   
   // ��������� ������ ���������� ���� ������� ������� Bluetooth-������������
   public void visible(View view){
      Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
      startActivityForResult(getVisible, 0);
   }
   
   // ������ ��� �������� ���������� � ���������
   class AcceptTask extends AsyncTask<Void, String, Void> 
   {
	   // ����� ��� ����� � ���������
	   BluetoothServerSocket mmServerSocket; 
	   
	   @Override
	   protected void onPreExecute() 
	   {
	     super.onPreExecute();
	     BluetoothServerSocket tmp = null;
         try {
        	 // �������� � ������������� ������ ��� ����� � ���������
             tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("Android_Bluetooth", UUID.fromString("FB694B90-F49E-4597-8306-171BBA78F846"));
         } catch (IOException e) { }
         mmServerSocket = tmp;
	   }

	   @Override
	   protected Void doInBackground(Void... params) 
	   {
		   // ����� ��� ������ ������� � ��������
		   BluetoothSocket socket = null;
           while (true) {
               try {
            	   // ������� ���������� � �������� � ������� ����� ��� �������� ����������
                   socket = mmServerSocket.accept();

                   if (socket != null) 
                   {   	   
                	   // ��������� ������ �� ������ �����������
                	   connectedThread = new ConnectedTask();
                	   connectedThread.execute(socket);
                	   		
                	   publishProgress("Connection accepted!");
                	   
                	   // �� ������ ������ ��������
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
        	   // ���������� ����� ��������
               mmServerSocket.close();
        } catch (IOException e) { }
      }
	
   }
   
   class ClientTask extends AsyncTask<BluetoothDevice, String, Void> 
   {
	   // ����� ��� ����� � ��������
	   BluetoothSocket mmSocket;
	   
	   // ����������, � ������� ����� �����
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
	    	   // ������������ ����� ��� ����� � ��������
	           tmp = device[0].createRfcommSocketToServiceRecord(UUID.fromString("FB694B90-F49E-4597-8306-171BBA78F846"));
	       } catch (IOException e) { }
	       mmSocket = tmp;
	        
	       // �������� ������������, ��� �������� ����������
	       bluetoothAdapter.cancelDiscovery();
	 
	       try {
	    	   // �������� ������� ����� � �������� (����� ����� � ���)
	           mmSocket.connect();
	       } catch (IOException connectException) {
	           try {
	        	   // �� �������!
	               mmSocket.close();
	           } catch (IOException closeException) { }
	           return null;
	       }
	 
	       // ����� ������� ����������. ������ ������ ��� ������ ����������� 
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
	    	 // ���������� ����� ������� ��� �����
	         mmSocket.close();
	      } catch (IOException e) { }
	  }
   }
   
   // ������ ��� ������ ����������� ����� �������� � ��������
   class ConnectedTask extends AsyncTask<BluetoothSocket, String, Void> 
   {
	    // ����� ��� �������� ����������
	    BluetoothSocket mmSocket;
	    
	    // ����� ��� ������ ����������
	    InputStream mmInStream;
	    
	    // ����� ��� ������ ����������
	    OutputStream mmOutStream;
	    
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();

	    }

	    // ������ ���������� �� ������
	    @Override
	    protected Void doInBackground(BluetoothSocket... socket) {
	    	mmSocket = socket[0];
	    	
	    	// ��������� ������� ��� ������ ����������� �� ������
	        InputStream tmpIn = null;
	        OutputStream tmpOut = null;
	 
	        try {
	            tmpIn = socket[0].getInputStream();
	            tmpOut = socket[0].getOutputStream();
	        } catch (IOException e) { }
	 
	        mmInStream = tmpIn;
	        mmOutStream = tmpOut;
	        
	        // ���� ������ ����������
	        int bytes=0;
	        while (true) {
	            try {
	            	byte[] buffer = new byte[1024];
	            	
	            	// ������ � �����
	                bytes = mmInStream.read(buffer);
	                
	                // ��������� ����� � ������
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
	      
	      // ����� ����������� ��������� � messagesTextView
	      allMessages = allMessages + values[0]+"\n";
	      messagesTextView.setText(allMessages);
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	      super.onPostExecute(result);

	    }
	    
	    public void write(String message) {
	        try {
	        	// ������������� ������ � ������ ����
	        	byte[] bytes = message.getBytes("UTF-8");
	        	// ��������� �� �� �������
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
