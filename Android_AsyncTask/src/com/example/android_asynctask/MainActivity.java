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
	
	// Передача параметров внутрь AsyncTask
	task.execute("One", "Two", "Three");
	
	/*
	try {
		// Обращение из потока за результатом выполнения
		// Если результат не готов, запрашивающий поток подождёт
		double d = task.get();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
	
	// Отмена работы потока
	//task.cancel(true);
  }

  private void showStatus() {
    if (task != null)
      Toast.makeText(this, task.getStatus().toString(), Toast.LENGTH_SHORT).show();
  }


  // 1 - входной параметр 
  // 2 - промежуточный параметр
  // 3 - выходной параметр
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

    // Возвращает результат работы потока
    @Override
    protected Double doInBackground(String... params) {
      try 
      {
    	params[0]="123";
        for (int i = 0; i < 10; i++) 
        {
        	// Нужно ли отменить работу потока
        	if (isCancelled()) return null;
        	TimeUnit.SECONDS.sleep(1);
        	progress.setProgress(i+1);
        	
        	// Отправка промежуточных параметров
        	publishProgress(i);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return 2.3;
    }

    // Получение выходного параметра (результат работы потока)
    @Override
    protected void onPostExecute(Double result) {
      super.onPostExecute(result);
      textView1.setText("End: "+result.toString());
      Toast.makeText(context, "Поток отработал", Toast.LENGTH_SHORT).show();
    }
    
    // Получение параметров от потока во время его работы
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