package com.example.android_sendmail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends Activity 
{
	 
    private EditText toEmail = null;
    private EditText emailSubject = null;
    private EditText emailBody = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        toEmail = (EditText) findViewById(R.id.toEmail);
        emailSubject = (EditText) findViewById(R.id.subject);
        emailBody = (EditText) findViewById(R.id.emailBody);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_clear:
            toEmail.setText("");
            emailBody.setText("");
            emailSubject.setText("");
            break;
        case R.id.menu_send:
            
        	String to = toEmail.getText().toString();
            String subject = emailSubject.getText().toString();
            String message = emailBody.getText().toString();
 
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);
            
            email.setType("plain/text");
 
            startActivity(Intent.createChooser(email, "Choose an Email client"));
 
            break;
        }
        return true;
    }
}
