package com.example.android_searchview;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener
{
    private SuggestionsDatabase database;
    private SearchView searchView1;
    private TextView textView1;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.status_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        database = new SuggestionsDatabase(this);
        searchView1 = (SearchView) searchItem.getActionView();

        // ”становить в виде иконки или нет
        searchView1.setIconifiedByDefault(true);

        // —лушатель дл€ сообщений во врем€ нажатий на клавиатуре
        searchView1.setOnQueryTextListener(this);

        // —лушатель дл€ поисковых предложений
        searchView1.setOnSuggestionListener(this);

        return true;
    }

    public boolean onQueryTextChange(String newText)
    {
        //textView1.setText("Query = " + newText);

        // ѕолучить список вариантов, удовлетвор€ющих поисковому запросу
        cursor = database.getSuggestions(newText);
        if (cursor != null) {
            String[] columns = new String[] {SuggestionsDatabase.SUGGESTION };
            int[] columnTextId = new int[] {android.R.id.text1};

            // —оздание SimpleCursorAdapter дл€ вариантов поисковых предложений
            SimpleCursorAdapter simple =
                    new SimpleCursorAdapter(getBaseContext(), android.R.layout.simple_list_item_1, cursor, columns ,columnTextId, 0);
            searchView1.setSuggestionsAdapter(simple);
        }
            else if (!cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            return true;

    }

    public boolean onQueryTextSubmit(String query) {
        textView1.setText("Query submitted!");
        searchView1.setIconified(true);

        // скрыть клавиатуру searchView
        searchView1.clearFocus();

        long result = database.insertSuggestion(query);
        return result != -1;
    }

    public boolean onClose() {
        textView1.setText("Closed!");
        return false;
    }

    public boolean onSuggestionClick(int position) {
        SQLiteCursor cursor = (SQLiteCursor) searchView1.getSuggestionsAdapter().getItem(position);
        int indexColumnSuggestion = cursor.getColumnIndex(SuggestionsDatabase.SUGGESTION);

        searchView1.setQuery(cursor.getString(indexColumnSuggestion), false);

        return true;
    }

    public boolean onSuggestionSelect(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }
}
