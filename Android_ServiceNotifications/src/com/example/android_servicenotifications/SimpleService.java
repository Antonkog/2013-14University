package com.example.android_servicenotifications;

import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SimpleService extends Service 
{
  NotificationManager notificationManager;
  
  @Override
  public void onCreate() 
  {
    super.onCreate();
    notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
  }

  public int onStartCommand(Intent intent, int flags, int startId) 
  {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    sendNotification();
    return super.onStartCommand(intent, flags, startId);
  }
  
  void sendNotification() 
  {
	  Intent intent = new Intent(this, MainActivity.class);
	  PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

	  Notification notification  = new Notification.Builder(this)
	          .setContentTitle("Service notification")
	          .setContentText("Task done!")
	          .setSmallIcon(R.drawable.ic_launcher)
	          .setContentIntent(pIntent)
	          .setAutoCancel(true)
	          .addAction(R.drawable.ic_launcher, "YES", pIntent)
	          .addAction(R.drawable.ic_launcher, "NO", pIntent).build();
	  
	  notification.flags |= Notification.FLAG_AUTO_CANCEL;

	  notificationManager.notify(0, notification);
  }
  
  public IBinder onBind(Intent arg0) 
  {
    return null;
  }
}
