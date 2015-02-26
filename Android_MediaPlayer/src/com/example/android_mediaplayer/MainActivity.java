package com.example.android_mediaplayer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements OnPreparedListener, OnCompletionListener 
{

  final String LOG_TAG = "Sound";

  MediaPlayer mediaPlayer;
  AudioManager manager;
  private MediaRecorder mediaRecorder;
  private String fileName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // Путь к SD-карте
    fileName = Environment.getExternalStorageDirectory() + "/new_record.3gpp";
    manager = (AudioManager) getSystemService(AUDIO_SERVICE);
  }

  public void onClickStart(View view) {
    releaseMP();

    try {
      switch (view.getId()) {
      case R.id.load:
        Log.d(LOG_TAG, "Load");
        mediaPlayer = new MediaPlayer();
        
        // Указангие источника музыки
        mediaPlayer.setDataSource("http://europaplus.hostingradio.ru:8014/europaplus256.mp3");
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        
        // Установка слушателя для окончания подготовки
        mediaPlayer.setOnPreparedListener(this);
        
        // Запуск подготовки (загрузка и кэширование)
        mediaPlayer.prepareAsync();
        Log.d(LOG_TAG, "prepareAsync");
        break;

      case R.id.start:
        Log.d(LOG_TAG, "Start");
        
        // Загрузка из проекта
        mediaPlayer = MediaPlayer.create(this, R.raw.duranduran);
        
        // Старт произрывания
        mediaPlayer.start();
        break;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    if (mediaPlayer == null)
      return;

    // Установка слушателя для окончания проигрывания
    mediaPlayer.setOnCompletionListener(this);
  }

  private void releaseMP() {
    if (mediaPlayer != null) {
      try {
    	// Освобождение ресурсов mediaPlayer
        mediaPlayer.release();
        mediaPlayer = null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void onClick(View view) {
    switch (view.getId()) {
    case R.id.btnPause:
    	if (mediaPlayer == null) return;
    	// Играет ли звук?
      if (mediaPlayer.isPlaying())
        mediaPlayer.pause();
      break;
    case R.id.btnResume:
    	  if (mediaPlayer == null) return;
      if (!mediaPlayer.isPlaying())
        mediaPlayer.start();
      break;
    case R.id.btnStop:
      if (mediaPlayer == null) return;
      mediaPlayer.stop();
      break;
    case R.id.btnBackward:
    		if (mediaPlayer == null) return;
    		// Играет ли музыка и можно ли перейти в другую позицию?
    		if(mediaPlayer.isPlaying() &&(mediaPlayer.getCurrentPosition() - 3000)>0)
    			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 3000);
      break;
    case R.id.btnForward:
    		if (mediaPlayer == null) return;
    		if(mediaPlayer.isPlaying() &&(mediaPlayer.getCurrentPosition() + 3000)<mediaPlayer.getDuration())
    			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 3000);
      break;
    case R.id.startRec:
    		try {
    		if (mediaRecorder != null) {
    	      mediaRecorder.release();
    	      mediaRecorder = null;
    	    }
    		
    		File outFile = new File(fileName);
    	      if (outFile.exists()) {
    	        outFile.delete();
    	      }

    	      // Создание рекордера
    	      mediaRecorder = new MediaRecorder();
    	      
    	      // Задание источника записи
    	      mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    	      
    	      // Указание формата записи
    	      mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    	      
    	   // Указание формата записи для формата
    	      mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    	      
    	      // Указание выходного файла
    	      mediaRecorder.setOutputFile(fileName);
    	      mediaRecorder.prepare();
    	      mediaRecorder.start();
    	      
    	      Log.d(LOG_TAG, "Recording started...");
    	    } catch (Exception e) {
    	      e.printStackTrace();
    	    }
        break;
      case R.id.stopRec:
    	  	if (mediaRecorder != null) {
    	      mediaRecorder.stop();
    	      Log.d(LOG_TAG, "Recording stoped.");
    	    }
        break;
      case R.id.playRec:
    	  try {
    		  if (mediaPlayer == null) return;
    		  
    	      mediaPlayer = new MediaPlayer();
    	      mediaPlayer.setDataSource(fileName);
    	      mediaPlayer.prepare();
    	      mediaPlayer.start();
    	    } catch (Exception e) {
    	      e.printStackTrace();
    	    }
        break;
    }
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    Log.d(LOG_TAG, "onPrepared");
    
    // Запуск звучания после окончания подготовки
    mp.start();
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    Log.d(LOG_TAG, "onCompletion");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Освобождение пямяти, занятой проигрывателями
    releaseMP();
  }
}
