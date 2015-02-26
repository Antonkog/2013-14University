package com.example.android_pixelbitmap;

import java.nio.ByteBuffer;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnSeekBarChangeListener
{
	Paint paint;
    Bitmap bmp, bmp2;
    ImageView imageView;
    SeekBar seekBar1;
    SeekBar seekBar2;
    SeekBar seekBar3;
    
    // Массив для хранения точек изображения
    byte[] array;
    byte[] dstArray;
    int bytesCount;
    int theR=0, theG=0, theB=0;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        imageView = (ImageView) this.findViewById(R.id.imageView1);
        seekBar1 = (SeekBar) this.findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) this.findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) this.findViewById(R.id.seekBar3);
        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);

        // Загрузка Bitmap из ресурсов
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.nature);
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.nature);
        
        // Получить количество байт изображения
        bytesCount = bmp.getByteCount();

        // Создать временный буфер в памяти для хранения байт изображения
        ByteBuffer buffer = ByteBuffer.allocate(bytesCount);
        
        // Скопировать байты из изображения в буфер
        bmp.copyPixelsToBuffer(buffer); 
      
        // Поместить точки в массив
        array = buffer.array();
        dstArray = array.clone();

        // Отобразить исходное изображение в imageView
        imageView.setImageBitmap(bmp);
    }

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
	{
		// Изменение красного канала
		if(seekBar == seekBar1)
		{
			theR = progress;
			for(int i = 0; i < array.length; i+=4)
			{
				int old = array[i];
				if(old < 0) old = 256+old; 
				int newR = old + theR - 256;
	        	if(newR > 255) newR = 255;
	        	if(newR < 0) newR = 0;
	    	  	dstArray[i] = (byte)newR;
			}
		}
		
		// Изменение зелёного канала
		if(seekBar == seekBar2)
		{
			theG = progress;
			for(int i = 1; i < array.length; i+=4)
			{
				int old = array[i];
				if(old < 0) old = 256+old; 
				int newG = old + theG - 256;
	        	if(newG > 255) newG = 255;
	        	if(newG < 0) newG = 0;
	    	  	dstArray[i] = (byte)newG;
			}
		}
		
		// Изменение синего канала
		if(seekBar == seekBar3)
		{
			theB = progress;
			for(int i = 2; i < array.length; i+=4)
			{
				int old = array[i];
				if(old < 0) old = 256+old; 
				int newB = old + theB - 256;
	        	if(newB > 255) newB = 255;
	        	if(newB < 0) newB = 0;
	    	  	dstArray[i] = (byte)newB;
			}
		}

        // Создание результирующего буфера
        ByteBuffer dst = ByteBuffer.allocate(bytesCount);
        
        // Помещение в него массива
        dst.put(dstArray);
      
        // Установка указателя буфера в начало !!!
        dst.rewind();
        
        // Запись буфера в изображение
        bmp2.copyPixelsFromBuffer(dst);

        // Установка изображение в imageView
        imageView.setImageBitmap(bmp2);
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
