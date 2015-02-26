package com.example.android_video;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity 
{
  SurfaceView sv;
  SurfaceHolder holder;
  HolderCallback holderCallback;
  Camera camera;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);

    sv = (SurfaceView) findViewById(R.id.surfaceView);
    holder = sv.getHolder();

    holderCallback = new HolderCallback();
    holder.addCallback(holderCallback);
  }

  @Override
  protected void onResume() 
  {
    super.onResume();
    
    // Включение камеры (параметр - номер камеры)
    camera = Camera.open(0);
    
    // Задание размеров SurfaceView из размеров камеры
    Size size = camera.getParameters().getPreviewSize();
    sv.getLayoutParams().height = size.height*2;
    sv.getLayoutParams().width = (int)(size.width*1.57);
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (camera != null) camera.release();
    camera = null;
  }

  class HolderCallback implements SurfaceHolder.Callback {

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
      try {
    	  
    	// Подготовка камеры
        camera.setPreviewDisplay(holder);
        
        // Старт трансляции камерой
        camera.startPreview();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      camera.stopPreview();
      setCameraDisplayOrientation();
      try {
        camera.setPreviewDisplay(holder);
        camera.startPreview();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

  }

  void setCameraDisplayOrientation() {

    int rotation = getWindowManager().getDefaultDisplay().getRotation();
    int degrees = 0;
    switch (rotation) {
    case Surface.ROTATION_0:
      degrees = 0;
      break;
    case Surface.ROTATION_90:
      degrees = 90;
      break;
    case Surface.ROTATION_180:
      degrees = 180;
      break;
    case Surface.ROTATION_270:
      degrees = 270;
      break;
    }
    
    int result = 0;
    
    CameraInfo info = new CameraInfo();
    Camera.getCameraInfo(0, info);

    // задняя камера
    if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
      result = ((360 - degrees) + info.orientation);
    } else
    // передняя камера
    if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
      result = ((360 - degrees) - info.orientation);
      result += 360;
    }
    result = result % 360;
    
    // Задание угла поворота камеры
    camera.setDisplayOrientation(result);
  }
 
}
