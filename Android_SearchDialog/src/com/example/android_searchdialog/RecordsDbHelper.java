package com.example.android_searchdialog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecordsDbHelper {

	public static final String KEY_DATA = "data";
	public static final String KEY_ROWID = "_id";

	private static final String TAG = "RecordsDbHelper";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_CREATE = "CREATE TABLE records(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "data TEXT NOT NULL);";

	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "records";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS tasks");
			onCreate(db);
		}
	}

	public RecordsDbHelper(Context ctx) {
		this.mCtx = ctx;
	}

	public RecordsDbHelper open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}
    
	public long createRecord(String data) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DATA, data);
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Поиск, основанный на операторе LIKE
	public Cursor fetchRecordsByQuery(String query) {
		return mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_DATA }, KEY_DATA + " LIKE" + "'%" + query + "%'", null,
				null, null, null, null);
	}
}