package com.paresh.googlesearchexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.paresh.googlesearchexample.adapters.GoogleImageBean;
import com.paresh.googlesearchexample.adapters.ListViewImageAdapter;

public class GoogleSearchAPIExampleActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ListView listViewImages;
	private EditText txtSearchText;	
	
    private ListViewImageAdapter adapter;
    private ArrayList<Object> listImages;
	private Activity activity;
	
	String strSearch = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        activity = this;
        listViewImages = (ListView) findViewById(R.id.lviewImages);
        txtSearchText = (EditText) findViewById(R.id.txtViewSearch);
    }
    
   public class getImagesTask extends AsyncTask<Void, Void, Void>
   {
	   JSONObject json;
	   ProgressDialog dialog;
	   
	   @Override
	   protected void onPreExecute() {
		   // TODO Auto-generated method stub
		   super.onPreExecute();
		   
		   dialog = ProgressDialog.show(GoogleSearchAPIExampleActivity.this, "", "Please wait...");
	   }
	   
	   @Override
	   protected Void doInBackground(Void... params) {
		   // TODO Auto-generated method stub
		   
		   URL url;
		   try {
			   url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
			   	"v=1.0&q="+strSearch+"&rsz=8"); //&key=ABQIAAAADxhJjHRvoeM2WF3nxP5rCBRcGWwHZ9XQzXD3SWg04vbBlJ3EWxR0b0NVPhZ4xmhQVm3uUBvvRF-VAA&userip=192.168.0.172");
		   
		   URLConnection connection = url.openConnection();
		   //connection.addRequestProperty("Referer", "http://technotalkative.com");
		   
		   String line;
		   StringBuilder builder = new StringBuilder();
		   BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		   while((line = reader.readLine()) != null) {
			   builder.append(line);
		   }

		   //System.out.println("Builder string => "+builder.toString());
		   
		   json = new JSONObject(builder.toString());
		   } catch (MalformedURLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return null;
	   }
	   
	   @Override
	   protected void onPostExecute(Void result) {
		   // TODO Auto-generated method stub
		   super.onPostExecute(result);
		   
		   if(dialog.isShowing())
		   {
			   dialog.dismiss();
		   }
		   
		   try {
			   JSONObject responseObject = json.getJSONObject("responseData");
			   JSONArray resultArray = responseObject.getJSONArray("results");
			   
			   // Разбор картинок
			   listImages = getImageList(resultArray);
			   
			   // Помещение картинок в адаптер для показа
			   SetListViewAdapter(listImages);
			   //System.out.println("Result array length => "+resultArray.length());
		   } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }
   }
   
   public ArrayList<Object> getImageList(JSONArray resultArray)
   {
	   ArrayList<Object> listImages = new ArrayList<Object>();
	   GoogleImageBean bean;

		try 
		{
			// Просмотр всех полученных изображений
			for(int i=0; i<resultArray.length(); i++)
			{
				JSONObject obj;
				obj = resultArray.getJSONObject(i);
				bean = new GoogleImageBean();
			   
				bean.setTitle(obj.getString("title"));
				bean.setThumbUrl(obj.getString("tbUrl"));
				
				//System.out.println("Thumb URL => "+obj.getString("tbUrl"));
				
				listImages.add(bean);
			   
			} 
			return listImages;
		 }
		 catch (JSONException e) 
		 {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
		 
		 return null;
	}
   
   public void SetListViewAdapter(ArrayList<Object> images)
   {
	   adapter = new ListViewImageAdapter(activity, images);
	   listViewImages.setAdapter(adapter);
   }
   
   // Запуск поиска
   public void btnSearchClick(View v)
   {
	   strSearch = txtSearchText.getText().toString();
	   strSearch = Uri.encode(strSearch);
	   
	   //System.out.println("Search string => "+strSearch);
	   new getImagesTask().execute();
   }
}