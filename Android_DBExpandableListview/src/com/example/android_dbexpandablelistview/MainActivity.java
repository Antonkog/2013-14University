package com.example.android_dbexpandablelistview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

public class MainActivity extends Activity 
{

  ExpandableListView exListView1;
  MyDB db;

  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    db = new MyDB(this);
    db.open();

    Cursor cursor = db.getCompanyData();
    startManagingCursor(cursor);

    String[] groupFrom = { "name" };
    int[] groupTo = { android.R.id.text1 };

    String[] childFrom = { "name" };
    int[] childTo = { android.R.id.text1 };

    SimpleCursorTreeAdapter sctAdapter = new MyTreeAdapter(this, cursor,
        android.R.layout.simple_expandable_list_item_1, groupFrom,
        groupTo, android.R.layout.simple_list_item_1, childFrom,
        childTo);
    
    exListView1 = (ExpandableListView) findViewById(R.id.exListView1);
    
    exListView1.setAdapter(sctAdapter);
  }

  protected void onDestroy() {
    super.onDestroy();
    db.close();
  }

  class MyTreeAdapter extends SimpleCursorTreeAdapter 
  {

    public MyTreeAdapter(Context context, Cursor cursor, int groupLayout,
        String[] groupFrom, int[] groupTo, int childLayout,
        String[] childFrom, int[] childTo) {
      super(context, cursor, groupLayout, groupFrom, groupTo,
          childLayout, childFrom, childTo);
    }

    protected Cursor getChildrenCursor(Cursor groupCursor) {
      int idColumn = groupCursor.getColumnIndex("_id");
      return db.getCarData(groupCursor.getInt(idColumn));
    }
  }
}
