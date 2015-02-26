package com.example.android_textswitcher;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity implements ViewSwitcher.ViewFactory 
{

	private TextSwitcher switcher;
	private int mCounter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		switcher = (TextSwitcher) findViewById(R.id.textSwitcher1);
		switcher.setFactory(this);

		Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		switcher.setInAnimation(in);
		switcher.setOutAnimation(out);

		updateCounter();

	}

	public void nextClick(View v) 
	{
		mCounter++;
		updateCounter();
	}

	private void updateCounter() 
	{
		switcher.setText(String.valueOf(mCounter));
	}

	public View makeView() 
	{
		TextView t = new TextView(this);
		t.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
		t.setTextSize(70);
		t.setTextColor(Color.BLUE);
		return t;
	}
}