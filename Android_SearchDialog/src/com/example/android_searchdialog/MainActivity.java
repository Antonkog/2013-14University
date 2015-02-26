package com.example.android_searchdialog;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
	private EditText text;
	private Button add;
	private RecordsDbHelper mDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDbHelper = new RecordsDbHelper(this);

		mDbHelper.open();

		Intent intent = getIntent();

		// Запущена ли activity как результат работы диалога поиска
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 

			// Получить строку поиска
			String query = intent.getStringExtra(SearchManager.QUERY);

			// Показать результаты поиска
			showResults(query);
		}

		add = (Button) findViewById(R.id.add);
		text = (EditText) findViewById(R.id.text);
		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String data = text.getText().toString();
				if (!data.equals("")) {
					saveTask(data);
					text.setText("");
				}
			}
		});
	}

	private void saveTask(String data) {
		mDbHelper.createRecord(data);
	}

	private void showResults(String query) {

		Cursor cursor = mDbHelper.fetchRecordsByQuery(query);
		startManagingCursor(cursor);
		String[] from = new String[] { RecordsDbHelper.KEY_DATA };
		int[] to = new int[] { R.id.text1 };

		SimpleCursorAdapter records = new SimpleCursorAdapter(this, R.layout.record, cursor, from, to);

		// Занести результаты поиска в ListActivity
		setListAdapter(records);
	}

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;	
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.search_record:
        	
        	// Программный способ вызова Search Dialog
        	onSearchRequested();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
