package com.example.android_scrollview;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity implements OnClickListener
{
	
	ScrollView vScrollView;
	HorizontalScrollView hScrollView;
	RelativeLayout layout;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		vScrollView = (ScrollView) this.findViewById(R.id.vscroll);
		hScrollView = (HorizontalScrollView) this.findViewById(R.id.hscroll);
		
		// �������� �� ���������
		hScrollView.setScrollbarFadingEnabled(true);
		
		// ������ ����� � ������������� �� ������� ����������
		hScrollView.setScrollBarDefaultDelayBeforeFade(2000);
		
		// ������ ����� � ������������� ��������� ����������
		hScrollView.setScrollBarFadeDuration(2000);
		
		// ������ ����� ������ ���������� (������ ��� ������� ������������� �������)
		hScrollView.setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_OVERLAY);
		
		// �� �������� !!!
		hScrollView.setScrollBarSize(50);
		
		
		button = (Button) this.findViewById(R.id.button1);
		button.setOnClickListener(this);
		
		layout = (RelativeLayout) this.findViewById(R.id.relative1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		// ��������� �������� ������������� �������
		//ScrollView.LayoutParams params = new ScrollView.LayoutParams(5000, 5000);
		//layout.setLayoutParams(params);
		
		// �������������� � �����-�� ����� � ������������
		//vScrollView.scrollTo(300, 300);
		//hScrollView.scrollTo(300, 300);
		
		// �������������� �� �����-�� �������� ��������
		//vScrollView.scrollBy(50, 50);
		//hScrollView.scrollBy(50, 50);
		
		// ������������ ��������������
		vScrollView.pageScroll(ScrollView.FOCUS_DOWN);
		hScrollView.pageScroll(ScrollView.FOCUS_RIGHT);
	}

}
