package com.example.newsparse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	private static String siteUrl = "http://www.itc.ua/news/";
	
	Button btnRefresh;
	ListView newsView;
	ProgressDialog progrDialog;
	
	String []title;
	String []img;
	String []link;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnRefresh = (Button) this.findViewById(R.id.btnRefresh);
		newsView = (ListView) this.findViewById(R.id.newsView);
		
		btnRefresh.setOnClickListener(this);
		newsView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnRefresh){
			
			progrDialog = new ProgressDialog(this);
			progrDialog.setTitle("Parsing site...  ");
			progrDialog.setMessage("Find&Download NEWS: ");
			progrDialog.setIndeterminate(false);
			progrDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progrDialog.setCancelable(true);
			progrDialog.show();
			
			ParseNews pNews = new ParseNews();
			pNews.execute(siteUrl);
			
		}
	}
	
	class ParseNews extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int count = 0;
			
			//File input = new File("/sdcard/news.htm");
			Document doc = null;
			try {
				//doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
				doc = Jsoup.connect(arg0[0]).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Elements titles = doc.select("h2.post-title");
			Elements imgs = doc.select("img.small-post-image");
			Elements links = doc.select("h2.post-title > a[href]");
						
			title = new String[titles.size()];
			int i=0; 
			for(Element titl: titles){
				title[i++] = titl.text();
				publishProgress(++count);
			}
			
			img = new String[imgs.size()];
 			i=0;
 			for(Element img_: imgs){
				img[i++] = img_.attr("abs:src");
			}
 			
 			link = new String[links.size()];
 			i=0;
 			for(Element lnk: links){
				link[i++] = lnk.attr("abs:href");
			}
			
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			progrDialog.setMessage("Find News: " +(values[0]));
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			progrDialog.dismiss();
			
			ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(title.length);
		    Map<String, Object> m;
		    
		    for (int i = 0; i < title.length; i++) 
		    {
		      m = new HashMap<String, Object>();
		      m.put("textbox", title[i]);
		      m.put("imagebox", img[i]);
		      //m.put("imagebox", R.drawable.ic_launcher);
		      data.add(m);
		    }

		    String[] from = { "textbox",  "imagebox" };
		    int[] to = { R.id.headerNews, R.id.imgNews};

		    SimpleAdapter sAdapter = new MySimpleAdapter(getBaseContext(), data, R.layout.item, from, to);
		    newsView.setAdapter(sAdapter);
						
			super.onPostExecute(result);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(this, newsText.class);
		intent.putExtra("url", link[arg2]);
		startActivity(intent);
	}

}

class MySimpleAdapter extends SimpleAdapter {

    public MySimpleAdapter(Context context,
        List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
      super(context, data, resource, from, to);
    } 

    @Override
    public void setViewText(TextView v, String text) 
    {
      super.setViewText(v, text);
    }

    @Override
    public void setViewImage(ImageView v, int value) {
                
      super.setViewImage(v, value);
    }
  }

