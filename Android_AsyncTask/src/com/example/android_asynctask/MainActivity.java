package com.example.android_asynctask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{

  MyTask task;
  TextView textView1;
  ProgressBar progress;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    textView1 = (TextView) findViewById(R.id.textView1);
    progress = (ProgressBar) findViewById(R.id.progress);
  }

  public void onclick(View v) {
    switch (v.getId()) 
    {
    case R.id.btnStart:
      startTask();
      break;
    case R.id.btnStatus:
      showStatus();
      break;
    default:
      break;
    }
  }

  private void startTask() {
	task = new MyTask(this);
	
	// �������� ���������� ������ AsyncTask
	task.execute("One", "Two", "Three");
	
	/*
	try {
		// ��������� �� ������ �� ����������� ����������
		// ���� ��������� �� �����, ������������� ����� �������
		double d = task.get();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
	
	// ������ ������ ������
	//task.cancel(true);
  }

  private void showStatus() {
    if (task != null)
      Toast.makeText(this, task.getStatus().toString(), Toast.LENGTH_SHORT).show();
  }


  // 1 - ������� �������� 
  // 2 - ������������� ��������
  // 3 - �������� ��������
  class MyTask extends AsyncTask <String, Integer, Double> 
  {
	Context context;
	
	MyTask(Context context)
	{
	      this.context = context;
	}
	
    @Override
    protected void onPreExecute() 
    {
      super.onPreExecute();
      textView1.setText("Begin");
    }

    // ���������� ��������� ������ ������
    @Override
    protected Double doInBackground(String... params) {
      try 
      {
    	params[0]="123";
        for (int i = 0; i < 10; i++) 
        {
        	// ����� �� �������� ������ ������
        	if (isCancelled()) return null;
        	TimeUnit.SECONDS.sleep(1);
        	progress.setProgress(i+1);
        	
        	// �������� ������������� ����������
        	publishProgress(i);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 2.3;
    }

    // ��������� ��������� ��������� (��������� ������ ������)
    @Override
    protected void onPostExecute(Double result) {
      super.onPostExecute(result);
      textView1.setText("End: "+result.toString());
      Toast.makeText(context, "����� ���������", Toast.LENGTH_SHORT).show();
    }
    
    // ��������� ���������� �� ������ �� ����� ��� ������
    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      textView1.setText("Updated: " + values[0]);
    }

    
    @Override
    protected void onCancelled() {
      super.onCancelled();
      textView1.setText("Cancel");
    }
  }
}