package com.example.searchgridview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Андрей on 12.11.13.
 */
public class ShowImage extends Activity {
    private ImageView imageView;
    private TextView textView;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_one);

        x = this.getIntent().getIntExtra("position", 1);
        imageView = (ImageView)findViewById(R.id.img);
        textView = (TextView)findViewById(R.id.text);
        new loadPhoto().execute();
    }

    public class loadPhoto extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        GoogleImageBean bean;
        Bitmap b;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ShowImage.this, "", "Загрузка...");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bean = (GoogleImageBean) GridAdapter._listImages.get(x);
            b = getImageBitmap(bean.getUrl());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
            imageView.setImageBitmap(b);
            textView.setText(Html.fromHtml(bean.getTitle()));
        }
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("", "Ошибка добавления", e);
        }
        return bm;
    }
}

