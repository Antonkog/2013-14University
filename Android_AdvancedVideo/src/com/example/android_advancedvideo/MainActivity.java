package com.example.android_advancedvideo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity 
{
  SurfaceView surfaceView;
  Camera camera;
  MediaRecorder mediaRecorder;

  File photoFile;
  File videoFile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    photoFile = new File(pictures, "photo.jpg");
    videoFile = new File(pictures, "video.mp4");
    String files[] = pictures.list();
    for(String file: files)
    {
    		Log.v("Video", "!!!"+file);
    }

    surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

    SurfaceHolder holder = surfaceView.getHolder();
    holder.addCallback(new SurfaceHolder.Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        try {
          camera.setPreviewDisplay(holder);
          camera.startPreview();
          
       // старт поиска лиц в кадре
          startFaceDetection();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format,
          int width, int height) {
      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
      }
    });

  }

  @Override
  protected void onResume() {
    super.onResume();
    camera = Camera.open(0);
    
    // ƒобавление слушател€ дл€ Face Detection
    camera.setFaceDetectionListener(new MyFaceDetectionListener());
    initSpinners();
  }

  @Override
  protected void onPause() {
    super.onPause();
    releaseMediaRecorder();
    if (camera != null)
      camera.release();
    camera = null;
  }
  
  // старт поиска лиц в кадре
  public void startFaceDetection()
  {
      Camera.Parameters params = camera.getParameters();
      if (params.getMaxNumDetectedFaces() > 0){
          camera.startFaceDetection();
      } else Log.d("FaceDetection", "Face detection not supported:");
  }
  
  // —лушатель поиска лиц
  class MyFaceDetectionListener implements Camera.FaceDetectionListener 
  {
	    @Override
	    public void onFaceDetection(Face[] faces, Camera camera) {
	      if (faces.length > 0) 
	      {
	        Log.d("FaceDetection", "Faces: " + faces.length + " Face 1 Location X: " + faces[0].rect.centerX() + "Y: " + faces[0].rect.centerY());
	      }
	    }
  }
  
  void initSpinners() {
	    final List<String> colorEffects = camera.getParameters().getSupportedColorEffects();
	    Spinner spEffect = initSpinner(R.id.spEffect, colorEffects, camera.getParameters().getColorEffect());
	    spEffect.setOnItemSelectedListener(new OnItemSelectedListener() {
	      @Override
	      public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	      {
	        Parameters params = camera.getParameters();
	        params.setColorEffect(colorEffects.get(arg2));
	        camera.setParameters(params);
	      }

	      @Override
	      public void onNothingSelected(AdapterView<?> arg0) {
	      }
	    });

	    final List<String> flashModes = camera.getParameters().getSupportedFlashModes();
	    String mode =  camera.getParameters().getFlashMode();
	    
	    if(mode!=null && flashModes!=null)
	    {
		    Spinner spFlash = initSpinner(R.id.spFlash, flashModes, mode);
		    spFlash.setOnItemSelectedListener(new OnItemSelectedListener() 
		    {
		      @Override
		      public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		        Parameters params = camera.getParameters();
		        params.setFlashMode(flashModes.get(arg2));
		        camera.setParameters(params);
		      }
	
		      @Override
		      public void onNothingSelected(AdapterView<?> arg0) {}
		    });
	    }
	  }

	  Spinner initSpinner(int spinnerId, List<String> data, String currentValue) {
	    Spinner spinner = (Spinner) findViewById(spinnerId);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);

	    for (int i = 0; i < data.size(); i++) {
	      String item = data.get(i);
	      if (item.equals(currentValue)) {
	        spinner.setSelection(i);
	      }
	    }

	    return spinner;
	  }

  public void onClickPicture(View view) {
    camera.takePicture(null, null, new PictureCallback() {
      @Override
      public void onPictureTaken(byte[] data, Camera camera) {
        try {
          FileOutputStream fos = new FileOutputStream(photoFile);
          fos.write(data);
          fos.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }

  public void onClickStartRecord(View view) {
    if (prepareVideoRecorder()) {
      mediaRecorder.start();
    } else {
      releaseMediaRecorder();
    }
  }

  public void onClickStopRecord(View view) {
    if (mediaRecorder != null) {
      mediaRecorder.stop();
      releaseMediaRecorder();
    }
  }

  private boolean prepareVideoRecorder() {

    camera.unlock();

    mediaRecorder = new MediaRecorder();

    mediaRecorder.setCamera(camera);
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
    CamcorderProfile profile = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH);
    mediaRecorder.setProfile(profile);
    mediaRecorder.setOutputFile(videoFile.getAbsolutePath());
    mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

    try {
      mediaRecorder.prepare();
    } catch (Exception e) {
      e.printStackTrace();
      releaseMediaRecorder();
      return false;
    }
    return true;
  }

  private void releaseMediaRecorder() {
    if (mediaRecorder != null) {
      mediaRecorder.reset();
      mediaRecorder.release();
      mediaRecorder = null;
      camera.lock();
    }
  }

}
