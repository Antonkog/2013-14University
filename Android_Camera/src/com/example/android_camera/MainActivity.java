package com.example.android_camera;

import com.example.android_video.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends Activity {

  final int REQUEST_CODE_VIDEO = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onClickVideo(View view) {
    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY , 1);
    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT , 10);
     
    startActivityForResult(intent, REQUEST_CODE_VIDEO);
  }

}
