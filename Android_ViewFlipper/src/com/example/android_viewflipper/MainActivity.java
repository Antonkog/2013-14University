package com.example.android_viewflipper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.viewflipper);
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		
		final Button buttonAutoFlip = (Button) findViewById(R.id.slideshow);

		flipper.setFlipInterval(2000);

		buttonAutoFlip.setOnClickListener(new Button.OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				if (flipper.isFlipping()) {
					flipper.stopFlipping();
					buttonAutoFlip.setText("Slideshow");
				} else {
					flipper.startFlipping();
					buttonAutoFlip.setText("Stop");
				}
			}
		});

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flipper.showNext();
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flipper.showPrevious();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
