package com.example.android_filedownload;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
 
public class MainActivity extends Activity implements OnClickListener
{ 
    Button btnShowProgress;
 
    public ProgressDialog progress1;
    ImageView imageView1;
 
    private static String file_url = "http://www.hdwallpapers.in/walls/glimpse_of_universe-wide.jpg";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        btnShowProgress = (Button) findViewById(R.id.button1);

        imageView1 = (ImageView) findViewById(R.id.imageView1);

        btnShowProgress.setOnClickListener(this);
    }
 
    class DownloadTask extends AsyncTask<String, Integer, String> 
    {
 
        @Override
        protected void onPreExecute() 
        {
            super.onPreExecute();
        }
 
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                
                int lenghtOfFile = conection.getContentLength();
 
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
                OutputStream output = new FileOutputStream("/sdcard/picture.jpg");
 
                byte data[] = new byte[1024];
 
                long total = 0;
 
                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress((int)((total*100)/lenghtOfFile));
 
                    output.write(data, 0, count);
                }
 
                output.flush();
 
                output.close();
                input.close();
 
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
 
            return null;
        }
 
        protected void onProgressUpdate(Integer... progress) 
        {
        	progress1.setProgress(progress[0]);
        }
 
        @Override
        protected void onPostExecute(String file_url) 
        {
        	progress1.dismiss();
 
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/picture.jpg";

            imageView1.setImageDrawable(Drawable.createFromPath(imagePath));
        }
 
    }

	public void onClick(View arg0) 
	{
		progress1 = new ProgressDialog(this);
    	progress1.setMessage("Downloading file...");
    	progress1.setIndeterminate(false);
    	progress1.setMax(100);
    	progress1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    	progress1.setCancelable(true);
    	progress1.show();
		
		new DownloadTask().execute(file_url);	
	}
}