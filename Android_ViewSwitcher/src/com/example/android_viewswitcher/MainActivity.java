package com.example.android_viewswitcher;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewSwitcher;

public class MainActivity extends Activity 
{
 public void onCreate(Bundle savedInstanceState) 
 {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewSwitcher switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
        Button btn = (Button) findViewById(R.id.button1);
        new AnimationUtils();
        
        btn.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
                switcher.setAnimation(AnimationUtils.makeOutAnimation(getApplicationContext(), true));
                switcher.showNext();
            }
        });
        
        Button btn1 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(new OnClickListener() 
        {
            public void onClick(View v) 
            {
                switcher.setAnimation(AnimationUtils.makeInAnimation(getApplicationContext(), true));
                switcher.showPrevious();
            }
        });
    }
}
