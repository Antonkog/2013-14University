package com.example.android_audiofocus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;

public class MainActivity extends Activity implements OnCompletionListener
{
  AudioManager audioManager;

  FocusListener musicListener;
  FocusListener soundListener;

  MediaPlayer musicPlayer;
  MediaPlayer soundPlayer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
  }

  public void onClickMusic(View view) {
	  musicPlayer = MediaPlayer.create(this, R.raw.duranduran);
	  musicPlayer.setOnCompletionListener(this);

	  musicListener = new FocusListener(musicPlayer);
	  audioManager.requestAudioFocus(musicListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

	  musicPlayer.start();
  }

  public void onClickSound(View view) 
  {
    int mode = AudioManager.AUDIOFOCUS_GAIN;
    switch (view.getId()) {
    case R.id.gain:
    		mode = AudioManager.AUDIOFOCUS_GAIN;
      break;
    case R.id.tran:
    		mode = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
      break;
    case R.id.duck:
    		mode = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
      break;
    }

    soundPlayer = MediaPlayer.create(this, R.raw.tada);
    soundPlayer.setOnCompletionListener(this);

    soundListener = new FocusListener(soundPlayer);
    
    // Запросить аудио фокус
    audioManager.requestAudioFocus(soundListener, AudioManager.STREAM_MUSIC, mode);

    soundPlayer.start();

  }
  
  public void onClickStop(View view) 
  {
	  musicPlayer.stop();
  }

  @Override
  public void onCompletion(MediaPlayer mp) 
  {
    if (mp == musicPlayer) 
    {
    	// Отдать аудио фокус
    	audioManager.abandonAudioFocus(musicListener);
    } else if (mp == soundPlayer) {
    	audioManager.abandonAudioFocus(soundListener);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (musicPlayer != null) musicPlayer.release();
    if (soundPlayer != null) soundPlayer.release();
    
    // Отдать аудио фокус
    if (musicListener != null) audioManager.abandonAudioFocus(musicListener);
    if (soundListener != null) audioManager.abandonAudioFocus(soundListener);
  }

  // Объекты класса получают уведомление о смене аудио фокуса
  class FocusListener implements OnAudioFocusChangeListener
  {
    MediaPlayer player;

    public FocusListener(MediaPlayer player) 
    {
      this.player = player;
    }

    @Override
    public void onAudioFocusChange(int focusChange) 
    {
        switch (focusChange) {
        case AudioManager.AUDIOFOCUS_LOSS:
        	player.pause();
          break;
        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
        	player.pause();
          break;
        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
        	player.setVolume(0.5f, 0.5f);
          break;
        case AudioManager.AUDIOFOCUS_GAIN:
          if (!player.isPlaying()) player.start();
          player.setVolume(1.0f, 1.0f);
          break;
        }
      }
  }

}
