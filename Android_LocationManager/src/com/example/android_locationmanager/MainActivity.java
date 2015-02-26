package com.example.android_locationmanager;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView altitudeTextView;
    TextView speedTextView;
    TextView accuracyTextView;
    
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
        
        latitudeTextView = (TextView) this.findViewById(R.id.latitude);
        longitudeTextView = (TextView) this.findViewById(R.id.longitude);
        altitudeTextView = (TextView) this.findViewById(R.id.altitude);
        speedTextView = (TextView) this.findViewById(R.id.speed);
        accuracyTextView = (TextView) this.findViewById(R.id.accuracy);
    }
    
    public void buttonClick(View view) 
    {
     switch(view.getId())
     {
	     case R.id.startButton:
	    	 	// отслеживание GPS
	        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

	        // отслеживание GSM
	        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

	    	 break;
	     case R.id.stopButton:
	    	 	locationManager.removeUpdates(locationListener);
	    	 	
	    	 	latitudeTextView.setText("Latitude: ");
        		longitudeTextView.setText("Longitude: ");
        		altitudeTextView.setText("Altitude: ");
        		speedTextView.setText("Speed: ");
        		accuracyTextView.setText("Accuracy: ");
	    	 break;
     }
    }

    public class MyLocationListener implements LocationListener 
    {
        @Override
        public void onLocationChanged(Location location) 
        {
        		latitudeTextView.setText("Latitude: " + location.getLatitude());
        		longitudeTextView.setText("Longitude: " + location.getLongitude());
        		if(location.hasAltitude())
        			altitudeTextView.setText("Altitude: " + location.getAltitude());
        		if(location.hasSpeed())
        			speedTextView.setText("Speed: " + location.getSpeed());
        		if(location.hasAccuracy())
        			accuracyTextView.setText("Accuracy: " + location.getAccuracy());
        		
        		float res[] = new float[3];
        		Location.distanceBetween(location.getLatitude(), location.getLongitude(), 50.0, 40.0, res);
        		Log.d("Distance", "Distance: " + res[0]);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps выключен", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps включен", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}