package com.example.android_accelball;

import java.util.Date;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements SensorEventListener
{
	private SensorManager mSensorManager;
	ImageView ball;
	View mainView;
	
	// Время последнего передвижения объекта
	Date lastMoveTime;
	
	// Линейная скорость по X
	double velocityX;
	
	// Линейная скорость по Y
	double velocityY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ball = (ImageView) this.findViewById(R.id.imageView1);
		mainView = (View) this.findViewById(R.id.mainView);
		
		lastMoveTime = new Date();
		
		mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
		    
			// Получить интервал времени между текущим и предыдущим срабатыванием акселерометра
		    double interval = -(lastMoveTime.getTime()-new Date().getTime());
		    
		    // Пересчёт скоростей с учётом ускорений из акселерометра
	        velocityX = velocityX -(event.values[0]*interval/10000);
	        velocityY = velocityY +(event.values[1]*interval/10000);
	        
	        // Получение изменения расстояний за интервал времени
	        double deltaX = interval*velocityX;
	        double deltaY = interval*velocityY;
	        
	        //Log.i("OK", "deltaX: " + deltaX + " deltaY: " + deltaY);
	        
	        // Вычисление новых координат шарика
	        int newX = (int) (ball.getX()+deltaX);
	        int newY = (int) (ball.getY()+deltaY);
	        
	        // Проверка выхода за границы экрана
	        if(newX<0)
	        {
	            newX=0;
	            velocityX *= -0.5;
	        }
	        
	        if(newY<0)
	        {
	            newY=0;
	            velocityY *= -0.5;
	        }
	        
	        if(newX>mainView.getWidth()-50)
	        {
	            newX=mainView.getWidth()-50;
	            velocityX *= -0.5;
	        }
	        
	        if(newY>mainView.getHeight()-50)
	        {
	            newY=mainView.getHeight()-50;
	            velocityY *= -0.5;
	        }
		    
	        // Перемещение шарика
		    RelativeLayout.LayoutParams mParams = 
					(RelativeLayout.LayoutParams) ball.getLayoutParams();

            mParams.leftMargin = newX;
            mParams.topMargin = newY;
            ball.setLayoutParams(mParams);
            
            // Обновление времени взаимодействия
            lastMoveTime = new Date();
		}
	}

}
