package com.example.android_viewanimator;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
 
public class MainActivity extends Activity {
	
    ViewAnimator animator;
    RelativeLayout layout;
 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        /*
        animator = (ViewAnimator)findViewById(R.id.viewAnimator1);
        
	    	Animation inAlpha = new AlphaAnimation(0, 1);
	    	inAlpha.setDuration(1000);
	    	Animation outAlpha = new AlphaAnimation(1, 0);
	    	outAlpha.setDuration(1000);
	 
	    	animator.setInAnimation(inAlpha);
	    	animator.setOutAnimation(outAlpha);
	    	*/
        
        
        layout = (RelativeLayout) this.findViewById(R.id.layout1);
        
        layout.removeAllViews();
        
        animator = new ViewAnimator (MainActivity.this);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        
        animator.setLayoutParams(params);
                
        ImageView image = new ImageView(MainActivity.this);
        image.setImageResource(R.drawable.enotik);
        
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        param1.addRule(RelativeLayout.CENTER_IN_PARENT);
        image.setLayoutParams(param1);
        
        Button button = new Button(MainActivity.this);
        button.setText("OK!");
        
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        param2.addRule(RelativeLayout.CENTER_IN_PARENT);
        button.setLayoutParams(param2);
        
        TextView textView = new TextView(MainActivity.this);
        textView.setText("Hello world!!!");
        textView.setTextSize(20);
        
        RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
        param3.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(param3);
        
        animator.addView(image);
        animator.addView(button);
        animator.addView(textView);
        
        layout.addView(animator);
        
        Animation inAlpha = new AlphaAnimation (0, 1);
        inAlpha.setDuration(1000);
        
        Animation outAlpha = new AlphaAnimation(1, 0);
        outAlpha.setDuration(1000);
	    	
	    animator.setInAnimation(inAlpha);
	    animator.setOutAnimation(outAlpha);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
	    	if(event.getAction() == MotionEvent.ACTION_UP) 
	    	{
	    		animator.showNext();
	    	}
	    	return true;
    }
}
