package com.example.android_soundpool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements OnLoadCompleteListener 
{
	  
	  final String LOG_TAG = "Sound";
	  
	  SoundPool pool;
	  int sound1;
	  int sound2;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    // �������� ���� ������
	    // 1 - ������������ ���������� ������������ ������������� ������
	    pool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
	    
	    // ��������� ��� ��������� ��������
	    pool.setOnLoadCompleteListener(this);

	    // �������� �� raw (��������� �������� - ���������)
	    sound1 = pool.load(this, R.raw.tada, 1);
	    Log.d(LOG_TAG, "tada loaded.");
	    
	    try {
	    	// �������� �� asset
	    	sound2 = pool.load(getAssets().openFd("notify.wav"), 1);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    Log.d(LOG_TAG, "notify loaded.");
	    
	  }
	  
	  public void onClick(View view) {
		// 1 - ID �������������� �����
		// 2 - ��������� ������ ������
		// 3 - ��������� ������� ������
		// 4 - ��������� �����
		// 5 - ���������� ��������
		// 6 - �������� ���������������
		int p1 = pool.play(sound1, 1, 1, 0, 0, 1);
		int p2 = pool.play(sound2, 1, 1, 0, 0, 1);
		pool.pause(p1);
	    
	    try {
	        TimeUnit.SECONDS.sleep(1);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }

	    pool.setVolume(p1, 0, 1);
	    pool.setVolume(p2, 1, 0);
	    
	    pool.resume(p1);
	    
	    try {
	        TimeUnit.SECONDS.sleep(1);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    
	    //pool.stop(p1);
	    //pool.stop(p2);
	    
	    // �������������� �������� �����
	    //pool.unload(sound1);
	  }

	  @Override
	  public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
	    Log.d(LOG_TAG, "onLoadComplete");
	  }
	  
	}
