package com.example.android_simplecursoradapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity implements OnClickListener
{

	ListView lvData;
	AuthorsDB sqh;
	SQLiteDatabase db;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;
	
	LinearLayout layout;
	Button addButton, deleteButton;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    sqh = new AuthorsDB(this);  
	db = sqh.getWritableDatabase();
	
	addButton = (Button) this.findViewById(R.id.add);
	addButton.setOnClickListener(this);
	
	deleteButton = (Button) this.findViewById(R.id.delete);
	deleteButton.setOnClickListener(this);
	
	layout = (LinearLayout) this.findViewById(R.id.layout1);
	
	// Получение курсора для БД
	//cursor = db.query("authors", "rowid _id, *", null, null, null, null, null);
	cursor = db.rawQuery( "select rowid _id, * from authors", null);
	
	// Начальная настройка курсора на начало таблицы
    startManagingCursor(cursor);
    
    String[] from = new String[] { "au_fname", "au_lname", "city", "state" };
    int[] to = new int[] { R.id.au_fname, R.id.au_lname, R.id.city, R.id.state };

    // Инициализация SimpleCursorAdapter
    // 1 - activity
    // 2 - шаблон строки списка
    // 3 - курсор
    // 4 - массив названий полей таблицы для вывода
    // 5 - массив id виджетов в строке списка
    scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
    lvData = (ListView) findViewById(R.id.listview1);
    lvData.setAdapter(scAdapter);
    
  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onDestroy() {
	    super.onDestroy();
	    db.close();
	    sqh.close();
	  }

	public void onClick(View view) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "!!!", Toast.LENGTH_SHORT).show();
		
		switch(view.getId())
		{
		case R.id.add:
		{
			ContentValues cv = new ContentValues();
			cv.put("au_id", "12345");
		    cv.put("au_fname", "Alex");
		    cv.put("au_lname", "Petrov");
		    cv.put("city", "Kiev");
		    cv.put("state", "KI");
		    db.insert("authors", null, cv);
		    
		    // Обновить курсор для показа актуальных данных
		    cursor.requery();
			break;
		}
		case R.id.delete:
		{
			db.delete("authors", "au_id = '12345'", null);
			cursor.requery();
			break;
		}
		}
	}

}
