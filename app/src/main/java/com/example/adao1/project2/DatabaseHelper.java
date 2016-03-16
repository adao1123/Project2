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

    //Define the database name and version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Shops.db";
    public static final String SHOP_TABLE_NAME = "SHOPS_LIST";
    public static final String COLUMN_ID = "_id";
    public static final String NAME = "SHOP_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRICE = "PRICE";
    public static final String IMAGEID = "IMAGEID";
    public static final String MAPID = "MAPID";
    public static final String[] ALL_COLUMNS = {COLUMN_ID,NAME,DESCRIPTION,PRICE,IMAGEID,MAPID};
    private static final String CREATE_SHOPS_TABLE = "CREATE TABLE " + SHOP_TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + DESCRIPTION
            + " TEXT, " + PRICE + " TEXT, " + IMAGEID + " TEXT, " + MAPID + " TEXT )" ;
    public static final String DROP_SHOPS_TABLE = "DROP TABLE IF EXISTS " + SHOP_TABLE_NAME;

    //Implement Singleton
    private static DatabaseHelper instance;
    public static DatabaseHelper getInstance(Context context){
        if (instance==null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SHOPS_TABLE);
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

//    public Shop getShop(int id){
//        SQLiteDatabase db = getReadableDatabase();  //Get a reference to the database
//        String[] projection = ALL_COLUMNS;  //Define a projection, which tells the query to return only the columns mentioned, similar to "Select column1, column2, column3"
//        String selection = "_id = ?"; //???????????????????????????????????????????
//        String[] selectionArgs = new String[]{String.valueOf(id)};
//        Cursor cursor = db.query("shops",projection,selection,selectionArgs,null,null,null,null);
//        cursor.moveToFirst();
//        String name = cursor.getString(cursor.getColumnIndex("name"));
//        String year = cursor.getString(cursor.getColumnIndex("year"));
//        return new Shop(id,name,year);
//    }

    public Cursor getShopsList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SHOP_TABLE_NAME,ALL_COLUMNS,null,null,null,null,null,null);
        return cursor;
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.delete("shops",selection,selectionArgs);
    }

}
