package com.example.android_videoview;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity 
{
	VideoView videoView = null;
	TextView textViewTime;
	
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    videoView = (VideoView)findViewById(R.id.videoView1);
    textViewTime = (TextView)findViewById(R.id.textViewTime);

    //String viewSource ="rtsp://v5.cache1.c.youtube.com/CjYLENy73wIaLQklThqIVp_AsxMYESARFEIJbXYtZ29vZ2xlSARSBWluZGV4YIvJo6nmx9DvSww=/0/0/0/video.3gp";
    //myVideoView.setVideoURI(Uri.parse(viewSource));

    // Подключение MediaController к videoView для проигрывания
    videoView.setMediaController(new MediaController(this));

    // Установка слушателей для разных ситуаций
    videoView.setOnCompletionListener(completionListener);
    videoView.setOnPreparedListener(preparedListener);
    videoView.setOnErrorListener(errorListener);
}

public void buttonsClick(View view) {
    if (videoView == null) return;
    switch (view.getId()) {
    case R.id.buttonPause:
      if(videoView.isPlaying() && videoView.canPause()) videoView.pause();
      break;
    case R.id.buttonResume:
      if (!videoView.isPlaying()) videoView.start();
      break;
    case R.id.buttonStop:
    		videoView.stopPlayback();
      break;
    case R.id.buttonBackward:
    		if(videoView.isPlaying() &&(videoView.getCurrentPosition() - 3000)>0)
    			videoView.seekTo(videoView.getCurrentPosition() - 3000);
      break;
    case R.id.buttonForeward:
    		if(videoView.isPlaying() &&(videoView.getCurrentPosition() + 3000)<videoView.getDuration())
    			videoView.seekTo(videoView.getCurrentPosition() + 3000);
      break;

      case R.id.buttonStart:
    	  	File movies = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    	  	videoView.setVideoPath(new File(movies, "myvideo.mp4").getAbsolutePath());
    	  	videoView.requestFocus();
    	  	videoView.start();
    	  	new myAsync().execute();
        break;
    }
  }

	MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener(){

	  @Override
	  public void onCompletion(MediaPlayer arg0) {
	   Toast.makeText(MainActivity.this, "End of Video", Toast.LENGTH_LONG).show();
	  }};
  
  	MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener(){

	  @Override
	  public void onPrepared(MediaPlayer mediaPlayer) {
	   Toast.makeText(MainActivity.this, "Media file is loaded and ready to go", Toast.LENGTH_LONG).show();
	   mediaPlayer.setVolume(1.0f, 1.0f);
	  }};
  
  	MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener(){

	  @Override
	  public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
	   Toast.makeText(MainActivity.this, "Error!!!", Toast.LENGTH_LONG).show();
	   return true;
	  }};
	  
	  private class myAsync extends AsyncTask<Void, Integer, Void>
	    {
	        @Override
	        protected Void doInBackground(Void... params) 
	        {
	        		try {
						Thread.sleep(1000);
				} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
	        		Integer duration = videoView.getDuration();
	        		Integer pos = 0;
	        		Integer oldpos = 0;
	        		do
	        		{
	        			pos = videoView.getCurrentPosition();
	        			if(oldpos < pos)
	        			{
	        				publishProgress(pos / 1000);
	        				oldpos = pos;
	        			}
	        		} while(pos <= duration);
	        		Log.d("Video", "end");
	            return null;
	        }

	        @Override
	        protected void onProgressUpdate(Integer... values) {
	            super.onProgressUpdate(values);
	            textViewTime.setText(values[0].toString());
	        }
	    }
}
