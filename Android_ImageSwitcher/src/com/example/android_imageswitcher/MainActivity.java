package com.example.android_imageswitcher;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity 
{
	 Button buttonNext;
	 Button buttonPrev;
	 ImageSwitcher imageSwitcher;
	
	 Animation slide_in_left, slide_out_right;
	
	 int imageResources[] = {
			 	R.drawable.admin,
			 	R.drawable.enotik,
			 	R.drawable.nature,
			   android.R.drawable.ic_dialog_alert,
			   android.R.drawable.ic_dialog_dialer,
			   android.R.drawable.ic_dialog_email,
			   android.R.drawable.ic_dialog_info, 
			   android.R.drawable.ic_dialog_map };
	
	 int curIndex;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	
	  buttonNext = (Button) findViewById(R.id.next);
	  buttonPrev = (Button) findViewById(R.id.prev);
	  
	  imageSwitcher = (ImageSwitcher) findViewById(R.id.imageswitcher);
	
	  slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
	  slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
	
	  imageSwitcher.setInAnimation(slide_in_left);
	  imageSwitcher.setOutAnimation(slide_out_right);
	
	  imageSwitcher.setFactory(new ViewFactory() 
	  {
		  @Override
		  public View makeView() 
		  {
				ImageView imageView = new ImageView(MainActivity.this);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
					
				LayoutParams params = new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					
				imageView.setLayoutParams(params);
				return imageView;
		   }
	  });
	
	  curIndex = 0;
	  imageSwitcher.setImageResource(imageResources[curIndex]);
	
	  buttonNext.setOnClickListener(new OnClickListener() 
	  {
		   @Override
		   public void onClick(View arg0) 
		   {
			   if (curIndex == imageResources.length - 1) 
			   {
				   curIndex = 0;
				   imageSwitcher.setImageResource(imageResources[curIndex]);
			   } else {
				   imageSwitcher.setImageResource(imageResources[++curIndex]);
			   }
		   }
	  });
	  
	  buttonPrev.setOnClickListener(new OnClickListener() 
	  {
		   @Override
		   public void onClick(View arg0) 
		   {
			   if (curIndex == 0) 
			   {
				   curIndex = imageResources.length - 1;
				   imageSwitcher.setImageResource(imageResources[curIndex]);
			   } else {
				   imageSwitcher.setImageResource(imageResources[--curIndex]);
			   }
		   }
	  });
	  
	 }

}
