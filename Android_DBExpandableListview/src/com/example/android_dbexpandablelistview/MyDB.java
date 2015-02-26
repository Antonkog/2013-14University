package com.example.android_dbexpandablelistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDB 
{
  private static final String DB_NAME = "cardb";
  private static final int DB_VERSION = 1;

  private static final String COMPANY_TABLE_CREATE = "create table company(_id integer primary key, name text);";
  private static final String CARS_TABLE_CREATE = "create table car(_id integer primary key autoincrement, name text, company_id integer);";

  private final Context context;

  private DBHelper mDBHelper;
  private SQLiteDatabase database;

  public MyDB(Context ctx) {
	  context = ctx;
  }

  public void open() {
    mDBHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    database = mDBHelper.getWritableDatabase();
  }

  public void close() {
    if (mDBHelper != null)
      mDBHelper.close();
  }

  public Cursor getCompanyData() {
    return database.query("company", null, null, null, null, null, null);
  }

  public Cursor getCarData(long companyID) {
    return database.query("car", null, "company_id = " + companyID, null, null, null, null);
  }

  private class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, CursorFactory factory,
        int version) {
      super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      ContentValues cv = new ContentValues();

      String[] companies = new String[] { "Audi", "Mercedes", "Toyota" };
      
      db.execSQL(COMPANY_TABLE_CREATE);
      for (int i = 0; i < companies.length; i++) {
        cv.put("_id", i + 1);
        cv.put("name", companies[i]);
        db.insert("company", null, cv);
      }

      String[] audi = new String[] { "A4", "A6", "A8", "TT" };
      String[] mercedes = new String[] { "S600", "E450", "SL500" };
      String[] toyota = new String[] { "Camry", "Celica", "Auris", "Corolla" };

      db.execSQL(CARS_TABLE_CREATE);
      cv.clear();
      for (int i = 0; i < audi.length; i++) {
        cv.put("company_id", 1);
        cv.put("name", audi[i]);
        db.insert("car", null, cv);
      }
      for (int i = 0; i < mercedes.length; i++) {
        cv.put("company_id", 2);
        cv.put("name", mercedes[i]);
        db.insert("car", null, cv);
      }
      for (int i = 0; i < toyota.length; i++) {
        cv.put("company_id", 3);
        cv.put("name", toyota[i]);
        db.insert("car", null, cv);
      }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
  }

}