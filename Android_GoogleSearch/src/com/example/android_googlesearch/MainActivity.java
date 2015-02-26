package com.example.android_googlesearch;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText editTextInput;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = (EditText) findViewById(R.id.editText1);
   }

    public void onSearchClick(View v)
    {
    	try {
    		 Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
             String term = editTextInput.getText().toString();
             intent.putExtra(SearchManager.QUERY, term);
             startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}

    }
}
