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
    
	// Класс поиска в отдельном потоке
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
		      
		      // Подготовка поискового запроса
		      search_query = search_url + search_item;
	     }
     
	  @Override
	  protected Void doInBackground(Void... arg0) 
	  {
	   
	   try 
	   {
		   // Выполнение поиска и получение результата в формате JSON
		   String result = sendQuery(search_query);
		   
		   // Парсинг результата
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
		  // Блокировка кнопки поиска до получения результата
		   buttonSearch.setEnabled(false);
		   buttonSearch.setText("Wait...");
		   super.onPreExecute();
	  }
	
	  @Override
	  protected void onPostExecute(Void result) 
	  {
		  // Загрузка в webView и разблокировка кнопки поиска
		   webView.loadData(searchResult, "text/html", "UTF-8");
		   buttonSearch.setEnabled(true);
		   buttonSearch.setText("Search");
		   super.onPostExecute(result);
	  }
	     
	    }
	
    	// Посылка запроса на сервер и получение результатов поиска
	    private String sendQuery(String query) throws IOException
	    {
	     String result = "";
	     
	     // Создание URL на основе строки запроса
	     URL searchURL = new URL(query);
	     
	     // Создание и открытие Http соединения с сервером
	     HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
	     
	     // Проверка статуса соединения
	     if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
	     {
	    	 
	    	// Буферизированное чтение с сервера 
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
	    
	    // Парсинг результатов поиска
	    private String ParseResult(String json) throws JSONException
	    {
	     String parsedResult = "";
	     
	     // Создание объекта JSONObject для парсинга
	     JSONObject jsonObject = new JSONObject(json);
	     JSONObject jsonObject_responseData = jsonObject.getJSONObject("responseData");
	     JSONArray jsonArray_results = jsonObject_responseData.getJSONArray("results");
	     
	     parsedResult += "Google Search APIs (JSON) for : <b>" + search_item + "</b><br/>";
	     parsedResult += "Number of results returned = <b>" + jsonArray_results.length() + "</b><br/><br/>";
	     
	     // Перебор результатов поиска
	     for(int i = 0; i < jsonArray_results.length(); i++)
	     {
	      
	    	 // Создать отдельный объект JSONObject для каждого результата поиска
		      JSONObject jsonObject_i = jsonArray_results.getJSONObject(i);
		      
		      String title = jsonObject_i.getString("title");
		      String content = jsonObject_i.getString("content");
		      String url = jsonObject_i.getString("url");
		      
		      parsedResult += "<a href='" + url + "'>" + title + "</a><br/>";
		      parsedResult += content + "<br/><br/>";
		      
		      Log.d("MySrv", "Title: " + title);
		      Log.d("MySrv", "Content: " + content);
	     }
	     
	     // Возвратить HTML с результатами поиска
	     return parsedResult;
	    }
}
