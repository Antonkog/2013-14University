package com.example.android_audioeffects;

import java.io.File;
import java.io.IOException;

import com.example.android_mediaplayer.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.media.audiofx.BassBoost;
import android.media.audiofx.EnvironmentalReverb;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements OnPreparedListener,
    OnCompletionListener {

  final String LOG_TAG = "Sound";

  MediaPlayer mediaPlayer;
  AudioManager am;
  private MediaRecorder mediaRecorder;
  private String fileName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    am = (AudioManager) getSystemService(AUDIO_SERVICE);
  }

  public void onClickStart(View view) {
    releaseMP();

    Log.d(LOG_TAG, "Start");
    mediaPlayer = MediaPlayer.create(this, R.raw.duranduran);
    mediaPlayer.start();

    if (mediaPlayer == null) return;

    mediaPlayer.setOnCompletionListener(this);
  }

  private void releaseMP() {
    if (mediaPlayer != null) {
      try {
        mediaPlayer.release();
        mediaPlayer = null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void onClick(View view) {
    if (mediaPlayer == null)
      return;
    switch (view.getId()) {
    case R.id.btnPause:
      if (mediaPlayer.isPlaying())
        mediaPlayer.pause();
      break;
    case R.id.btnResume:
      if (!mediaPlayer.isPlaying())
        mediaPlayer.start();
      break;
    case R.id.btnStop:
      mediaPlayer.stop();
      break;
    case R.id.btnBackward:
    		if(mediaPlayer.isPlaying() &&(mediaPlayer.getCurrentPosition() - 3000)>0)
    			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 3000);
      break;
    case R.id.btnForward:
    		if(mediaPlayer.isPlaying() &&(mediaPlayer.getCurrentPosition() + 3000)<mediaPlayer.getDuration())
    			mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 3000);
      break;
    case R.id.presetReverb:
	    	PresetReverb pReverb = new PresetReverb(1, 0);
	    	mediaPlayer.attachAuxEffect(pReverb.getId());
	    	pReverb.setPreset(PresetReverb.PRESET_LARGEHALL);
	    	pReverb.setEnabled(true);
	    	mediaPlayer.setAuxEffectSendLevel(1.0f);
        break;
        
      case R.id.environmentalReverb:
	    EnvironmentalReverb eReverb = new EnvironmentalReverb(1,0); 
	    mediaPlayer.attachAuxEffect(eReverb.getId());
	    eReverb.setEnabled(true);
	    
	    // Рассеивание
	    eReverb.setDiffusion((short)500); 
	    
	    // Плотность
	    eReverb.setDensity((short)500);
	    
	    // Уровень реверберации
	    eReverb.setReverbLevel((short) 1000);
	    
	    // Время разложения
	    eReverb.setDecayTime(1000);
	    
		mediaPlayer.setAuxEffectSendLevel(1.0f);
        break;
    }
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    Log.d(LOG_TAG, "onPrepared");
    mp.start();
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    Log.d(LOG_TAG, "onCompletion");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    releaseMP();
  }
}
