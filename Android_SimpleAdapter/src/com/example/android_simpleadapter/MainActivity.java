package com.example.android_simpleadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

  ListView lvSimple;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String[] texts = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
    boolean[] checked = { true, false, false, true, false, true, false, false, true, true };
    int img = R.drawable.ic_launcher;

    ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(texts.length);
    Map<String, Object> m;
    
    for (int i = 0; i < texts.length; i++) 
    {
      m = new HashMap<String, Object>();
      m.put("textbox", texts[i]);
      m.put("checkbox", checked[i]);
      m.put("image", img);
      data.add(m);
    }

    String[] from = { "textbox", "checkbox", "image" };
    int[] to = { R.id.textbox1, R.id.checkbox1, R.id.image1 };

    SimpleAdapter sAdapter = new MySimpleAdapter(this, data, R.layout.item, from, to);

    lvSimple = (ListView) findViewById(R.id.lvSimple);
    lvSimple.setAdapter(sAdapter);
  }
}

class MySimpleAdapter extends SimpleAdapter 
{

    public MySimpleAdapter(Context context,
        List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
      super(context, data, resource, from, to);
    }

    @Override
    public void setViewText(TextView v, String text) 
    {
      super.setViewText(v, text);

      if (v.getId() == R.id.textbox1) 
      {
        int i = text.length();
        if (i%2==1) v.setTextColor(Color.RED); 
        else v.setTextColor(Color.GREEN);
      }
    }

    @Override
    public void setViewImage(ImageView v, int value) {
      //super.setViewImage(v, value);
    	
      Random r = new Random();
      int i = r.nextInt(100);
      if (i%2==1) value = R.drawable.enotik;
      super.setViewImage(v, value);
    }
  }
