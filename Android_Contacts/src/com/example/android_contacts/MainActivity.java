package com.example.android_contacts;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	Button showContacts, addContact, deleteContact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showContacts = (Button) this.findViewById(R.id.showContacts);
		addContact = (Button) this.findViewById(R.id.addContact);
		deleteContact = (Button) this.findViewById(R.id.deleteContact);
		
		showContacts.setOnClickListener(this);
		addContact.setOnClickListener(this);
		deleteContact.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		switch(view.getId())
		{
			case R.id.showContacts:
				
				// Получение доступа к провайдеру служебной информации
				ContentResolver cr = getContentResolver();
				
				// Запрос к таблице с контактами
		        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		        if (cur.getCount() > 0) 
		        {
				    while (cur.moveToNext()) 
				    {
				        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
				        {
				        	// Запрос телефонов
				        	if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
				        	{
				               Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				            		null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
				                	new String[]{id}, null);
				                
				               // Движение по телефонам
					  	       while (pCur.moveToNext()) 
					  	       {
					  	    	   String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					  	    	   Log.d("contacts", name + " phone: " + phone);
					  	       } 
					  	       pCur.close();
				        	}
				        		
				        	// Email
				        	Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
				        			ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
				        			new String[]{id}, null);
				        		
				        	while (emailCur.moveToNext()) 
				        	{ 
				        		String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				        		 Log.d("contacts", name + " email: " + email);
				        	} 
				        	emailCur.close();
				        		
				        	// Address
				        	String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
				        	String[] addrWhereParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE}; 
				        	Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI, null, addrWhere, addrWhereParams, null); 
				        	while(addrCur.moveToNext()) 
				        	{
				        	 	String street = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
				        	 	String city = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
				        	 	String state = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
				        	 	String postalCode = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
				        	 	String country = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
				        	 	Log.d("contacts", name + " address: " + street);
				        	} 
				        	addrCur.close();
				        	
				        	// Instant messenger
				        	String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
				         	String[] imWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE}; 
				         	Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI, null, imWhere, imWhereParams, null); 
				         	if (imCur.moveToFirst()) 
				         	{ 
				         	    String imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
				         	    Log.d("contacts", name + " messenger: " + imName);
				         	} 
				         	imCur.close();
				         	
				         	// Notes
				         	String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"; 
				            String[] noteWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE}; 
				            Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null); 
				            if (noteCur.moveToFirst()) 
				            { 
				            	String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
				            	Log.d("contacts", name + " note: " + note);
				            }
				            noteCur.close();
				        }
				    }
		        }
				break;
				
			case R.id.deleteContact:
				try
				{
					cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
			        if (cur.getCount() > 0) 
			        {
					    while (cur.moveToNext()) 
					    {
					        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					        if(name.equals("Ivan Bunin"))
					        {
					        	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
					        	ops.add(ContentProviderOperation.newDelete(Data.CONTENT_URI)
					        			.withSelection(Data._ID + "=? and " + Data.MIMETYPE + "=?", new String[]{String.valueOf(id), Phone.CONTENT_ITEM_TYPE})
					        			.build());
					        	getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
					        }
					    }
			        }
				}
				catch(Exception ex)
				{
					Log.e("contacts", "ERROR DELETING!!!");
				}
				
				break;
			
			case R.id.addContact:
				
				// Список операций, которые будут применены к служебным таблицам
				ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		        
				// 0
				int rawContactInsertIndex = ops.size();
		 
				// Создание нового объекта
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
		                .withValue(RawContacts.ACCOUNT_TYPE, null)
		                .withValue(RawContacts.ACCOUNT_NAME, null)
		                .build());
		        
		        //INSERT NAME
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
		                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
		                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, "Ivan Bunin")
		                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "Bunin")
		                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Ivan")
		                .build());
		        
		        // INSERT PHONE
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		        	    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
		        	    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
		        	    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "0971234567")
		        	    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
		        	    .build());
		        
		        // INSERT e-mail
		        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
		                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
		                .withValue(ContactsContract.CommonDataKinds.Email.DATA, "ibunin@ya.ru")
		                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
		                .build());
		        
		        // Add contact
				ContentProviderResult[] res;
				try 
				{
					res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		            if (res!=null) Log.d("contacts", "URI added contact");
		            else Log.e("contacts", "Contact not added");
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				break;
		}
	}

}
