package com.example.android_layer_drawable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnClickListener, OnSeekBarChangeListener
{
	Button setLayersButton;
	Button saveButton;
	Button loadButton;
	
	EditText edit1;
	
	ImageView imageView1;
	SeekBar seekBar1;
	
	ArrayList <Drawable> layers = new ArrayList <Drawable>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setLayersButton = (Button) this.findViewById(R.id.button1);
        setLayersButton.setOnClickListener(this);
        
        saveButton = (Button) this.findViewById(R.id.button2);
        saveButton.setOnClickListener(this);
        
        loadButton = (Button) this.findViewById(R.id.button3);
        loadButton.setOnClickListener(this);
        
        edit1 = (EditText) this.findViewById(R.id.editText1);
        
        imageView1 = (ImageView) this.findViewById(R.id.imageView1);
        
        seekBar1 = (SeekBar) this.findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(this);
    }

	@Override
	public void onClick(View v) {

		if(v == loadButton)
		{
			File dirFiles = getFilesDir();
	        for (String strFile : dirFiles.list())
	        {
	        	Log.d("ok", strFile);
	        }
	        
			try {
				Editable filename = edit1.getText();
				InputStream is = openFileInput(filename.toString());
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				imageView1.setImageBitmap(bitmap);
				is.close();
			} 
	        catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(v == saveButton)
		{
			// Получить из списка массив
			Drawable[] layersArray = new Drawable[layers.size()];
			layersArray = layers.toArray(layersArray);
			
			// Создать объект LayerDrawable и передать ему массив слоёв
			LayerDrawable layerDrawable = new LayerDrawable(layersArray);
			
			// Преобразование в Bitmap и вывод в imageView1
			Bitmap resBitmap = Bitmap.createBitmap(1024, 768, Bitmap.Config.ARGB_8888);
			Canvas resBitmapCanvas = new Canvas(resBitmap);
			layerDrawable.setBounds(0, 0, 1024, 768);
			layerDrawable.draw(resBitmapCanvas);
			
			imageView1.setImageBitmap(resBitmap);
			
			try {
				Editable name = edit1.getText();
				
			    FileOutputStream out = openFileOutput(name.toString(), MODE_PRIVATE);
			    resBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			    out.close();
			} catch (Exception e) {
			       e.printStackTrace();
			}
		}
		
		if(v == setLayersButton)
		{
			//imageView1.setImageDrawable(getResources().getDrawable(R.layout.layers));
			
			// Получить ссылку на ресурсы
			Resources r = getResources();
			
			// Загрузить картинки из ресурсов и поместить в слои
			layers.add(r.getDrawable(R.drawable.lostsea));
			layers.add(r.getDrawable(R.drawable.onfire));
			
			// Задать прозрачность для слоя 1 (0..255)
			layers.get(1).setAlpha(100);
			
			// Создать Bitmap (третий слой)
			Bitmap bitmap = Bitmap.createBitmap(1024, 768, Bitmap.Config.ARGB_8888);
			
			// Получить canvas для рисования на нём
			Canvas bitmapCanvas = new Canvas(bitmap);
			
			// Рисуем прямоугольник и линию
			Paint paint = new Paint();
			paint.setColor(Color.BLUE);
			bitmapCanvas.drawRect(100, 100, 300, 300, paint);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);
			paint.setColor(Color.RED);
			paint.setStrokeWidth(5);
			bitmapCanvas.drawLine(200, 200, 400, 400, paint);
			
			// Преобразуем Bitmap в BitmapDrawable
			BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
			
			// Доюавляем слой в список слоёв
			layers.add(bitmapDrawable);
			
			// Выводим список слоёв в imageView
			bitmapRefresh();
		}
	}

	// Выводим список слоёв в imageView
	private void bitmapRefresh() 
	{
		// Получить из списка массив
		Drawable[] layersArray = new Drawable[layers.size()];
		layersArray = layers.toArray(layersArray);
		
		// Создать объект LayerDrawable и передать ему массив слоёв
		LayerDrawable layerDrawable = new LayerDrawable(layersArray);
		
		// Вывести БЫСТРО LayerDrawable в imageView1
		imageView1.setImageDrawable(layerDrawable);
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
	{
		layers.get(1).setAlpha(progress);
		bitmapRefresh();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}