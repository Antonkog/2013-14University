package com.example.android_audiotrack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity 
{
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	  }
	
  public static byte[] convertStreamToByteArray(InputStream is) throws IOException 
  {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buff = new byte[10240];
	    int i = Integer.MAX_VALUE;
	    while ((i = is.read(buff, 0, buff.length)) > 0) 
	    {
	        baos.write(buff, 0, i);
	    }

	    return baos.toByteArray();
  }
  
  AudioTrack audioTrack = null;
  
  public void audioTrackPlay(View v)
  {
	  
	  new Thread(new Runnable() {
	      @Override
	      public void run() 
	      {
	    	audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 22050, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, 44100, AudioTrack.MODE_STREAM);
	  	    InputStream in1=getResources().openRawResource(R.raw.duranduran);      

	  	    // Загрузка входной последовательности
	  	    byte[] music1 = null;
	  	    try {
				music1= new byte[in1.available()];
		  	    music1=convertStreamToByteArray(in1);
		  	    in1.close();
	  	    }
	  	    	catch (IOException e) {
				e.printStackTrace();
			} 
	  	    
	  	    // Выходная изменённая последовательность байт
	  	    byte[] output = new byte[music1.length];

	  	    // Включить режим проигрывания
	  	    audioTrack.play();

	  	    // Редактирование звука
	  	    for(int i=0; i < output.length; i++)
	  	    {
	  	    	// Отключение старщего байта
	  	        float samplef1 = music1[i] / 128.0f;      //     2^7=128

	  	        float mixed = samplef1;

	  	        mixed *= 0.8f;

	  	        // Выравнивание результирующего диапазона
	  	        if (mixed > 1.0f) mixed = 1.0f;
	  	        if (mixed < -1.0f) mixed = -1.0f;

	  	        // Возврат старщего байта
	  	        byte outputSample = (byte)(mixed * 128f);
	  	        output[i] = outputSample;
	  	    }
	  	    
	  	    // Послать массив звуковых байтов в audioTrack для проигрывания
	  	    audioTrack.write(output, 0, output.length);	    
	      }
	    }).start();
  }
  
  public void audioTrackStop(View v)
  {
	  audioTrack.stop();
  }

  @Override
  protected void onDestroy() 
  {
    super.onDestroy();


  }
}
