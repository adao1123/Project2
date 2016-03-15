package com.example.adao1.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adao1 on 3/14/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Dfine the database name and version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Shops.db";
    public static final String SQL_CREATE_GAME_TABLE = "CREATE TABLE shops ( id INTEGER PRIMARY KEY, name TEXT, year TEXT )";
    public static final String SQL_DROP_GAME_TABLE = "DROP TABLE IF EXISTS shops";
    //override the SQLiteDataase's constructor, onCreate, and onUpgrade


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_GAME_TABLE);
        onCreate(db);
    }

    public void insert(int id, String name, String year){
        SQLiteDatabase db = getWritableDatabase();  //Get a reference to the database
        ContentValues values = new ContentValues();  //create a new content value to store values
        values.put("id",id);
        values.put("name",name);
        values.put("year", year);
        db.insert("shops", null, values);  //Inser the row into the games table
    }

    public Shop getShop(int id){
        SQLiteDatabase db = getReadableDatabase();  //Get a reference to the database
        String[] projection = new String[]{"id", "name", "year"};  //Define a projection, which tells the query to return only the columns mentioned, similar to "Select column1, column2, column3"
        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.query("shops",projection,selection,selectionArgs,null,null,null,null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String year = cursor.getString(cursor.getColumnIndex("year"));
        return new Shop(id,name,year);
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.delete("shops",selection,selectionArgs);
    }

}
