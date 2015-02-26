package com.example.newsparse;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class newsText extends Activity{

	TextView newsText;
	String text;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avtivity_newstext);
		
		newsText = (TextView) this.findViewById(R.id.newsText);
				
		Intent intent = getIntent();
		String str = intent.getStringExtra("url");
		Log.i("link", str);
		
		showNews sNews = new showNews();
		sNews.execute(str);
		 
		//File input = new File("/sdcard/2.htm");
		/*;
		}*/
		//newsText.setText((CharSequence) doc1.select("div.article-content").text());
	}

	class showNews extends AsyncTask<String, Integer, String> 
    {
 
        @Override
        protected void onPreExecute() 
        {
            super.onPreExecute();
        }
 
        @Override
        protected String doInBackground(String... url) {
            
        	Document doc1 = null;
        	try {
			//doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			doc1 = Jsoup.connect(url[0]).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	text = doc1.select("div.article-content").text();
        	
            return null;
        }
 
        protected void onProgressUpdate(Integer... progress) 
        {
        	
        }
 
        @Override
        protected void onPostExecute(String file_url) 
        {
        	newsText.setText(text); 
        }
 
    }
}
