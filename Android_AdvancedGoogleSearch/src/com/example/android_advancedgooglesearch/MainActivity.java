package com.example.android_advancedgooglesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

 EditText input;
 Button buttonSearch;
 WebView webView;
 
 String search_item;
 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        input = (EditText)findViewById(R.id.input);
        buttonSearch = (Button)findViewById(R.id.search);
        webView = (WebView)findViewById(R.id.webView);
        
        buttonSearch.setOnClickListener(searchOnClickListener);

    }
    
    OnClickListener searchOnClickListener = new OnClickListener()
    {
	  public void onClick(View arg0) 
	  {
	   String item = input.getText().toString();
	   SearchTask task = new SearchTask(item);
	   task.execute();
	  }
	};
    
	// ����� ������ � ��������� ������
    private class SearchTask extends AsyncTask<Void, Void, Void>
    {
	     String searchResult = "";
	     String search_url = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=8&start=1&q=";
	     String search_query;
	     
	     SearchTask(String item)
	     {
		      try 
		      {
		    	  	search_item = URLEncoder.encode(item, "utf-8");
		      } catch (UnsupportedEncodingException e) 
		      {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
		      }
		      
		      // ���������� ���������� �������
		      search_query = search_url + search_item;
	     }
     
	  @Override
	  protected Void doInBackground(Void... arg0) 
	  {
	   
	   try 
	   {
		   // ���������� ������ � ��������� ���������� � ������� JSON
		   String result = sendQuery(search_query);
		   
		   // ������� ����������
		   searchResult = ParseResult(result);
	   } catch (JSONException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	   
	   return null;
	  }
	
	  @Override
	  protected void onPreExecute() 
	  {
		  // ���������� ������ ������ �� ��������� ����������
		   buttonSearch.setEnabled(false);
		   buttonSearch.setText("Wait...");
		   super.onPreExecute();
	  }
	
	  @Override
	  protected void onPostExecute(Void result) 
	  {
		  // �������� � webView � ������������� ������ ������
		   webView.loadData(searchResult, "text/html", "UTF-8");
		   buttonSearch.setEnabled(true);
		   buttonSearch.setText("Search");
		   super.onPostExecute(result);
	  }
	     
	    }
	
    	// ������� ������� �� ������ � ��������� ����������� ������
	    private String sendQuery(String query) throws IOException
	    {
	     String result = "";
	     
	     // �������� URL �� ������ ������ �������
	     URL searchURL = new URL(query);
	     
	     // �������� � �������� Http ���������� � ��������
	     HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
	     
	     // �������� ������� ����������
	     if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
	     {
	    	 
	    	// ���������������� ������ � ������� 
	      InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
	      BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
	      
	      String line = null;
	      while((line = bufferedReader.readLine()) != null){
	       result += line;
	      }
	      
	      bufferedReader.close();
	     }
	     
	     return result;
	    }
	    
	    // ������� ����������� ������
	    private String ParseResult(String json) throws JSONException
	    {
	     String parsedResult = "";
	     
	     // �������� ������� JSONObject ��� ��������
	     JSONObject jsonObject = new JSONObject(json);
	     JSONObject jsonObject_responseData = jsonObject.getJSONObject("responseData");
	     JSONArray jsonArray_results = jsonObject_responseData.getJSONArray("results");
	     
	     parsedResult += "Google Search APIs (JSON) for : <b>" + search_item + "</b><br/>";
	     parsedResult += "Number of results returned = <b>" + jsonArray_results.length() + "</b><br/><br/>";
	     
	     // ������� ����������� ������
	     for(int i = 0; i < jsonArray_results.length(); i++)
	     {
	      
	    	 // ������� ��������� ������ JSONObject ��� ������� ���������� ������
		      JSONObject jsonObject_i = jsonArray_results.getJSONObject(i);
		      
		      String title = jsonObject_i.getString("title");
		      String content = jsonObject_i.getString("content");
		      String url = jsonObject_i.getString("url");
		      
		      parsedResult += "<a href='" + url + "'>" + title + "</a><br/>";
		      parsedResult += content + "<br/><br/>";
		      
		      Log.d("MySrv", "Title: " + title);
		      Log.d("MySrv", "Content: " + content);
	     }
	     
	     // ���������� HTML � ������������ ������
	     return parsedResult;
	    }
}
