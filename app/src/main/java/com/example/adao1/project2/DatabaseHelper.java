package com.example.adao1.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by adao1 on 3/14/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Define the database name and version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Shops.db";
    public static final String SHOP_TABLE_NAME = "SHOPS_LIST";
    public static final String TAG_TABLE_NAME = "TAGS_LIST";
    public static final String REVIEW_TABLE_NAME = "REVIEW_LIST";
    public static final String SHOP_TAG_TABLE_NAME = "TAGS_LIST";
    public static final String COLUMN_ID = "_id";
    public static final String TAG_ID = "_id";
    public static final String REVIEW_ID = "_id";
    public static final String RELATION_SHOP_ID = "shop_id";
    public static final String RELATION_TAG_ID = "tag_id";
    public static final String COLUMN_NAME = "SHOP_NAME";
    public static final String TAG_NAME = "TAG_NAME";
    public static final String REVIEW_NAME = "REVIEW_NAME";
    public static final String REVIEW_WRITING = "REVIEW_WRITING";
    public static final String COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static final String COLUMN_PRICE = "COLUMN_PRICE";
    public static final String COLUMN_IMAGEID = "COLUMN_IMAGEID";
    public static final String COLUMN_MAPID = "COLUMN_MAPID";
    public static final String COLUMN_ISFAV = "COLUMN_ISFAV";
    public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_PRICE, COLUMN_IMAGEID, COLUMN_MAPID,COLUMN_ISFAV};
    public static final String[] REVIEW_COLUMNS = {REVIEW_ID,REVIEW_NAME,REVIEW_WRITING};
    private static final String CREATE_SHOPS_TAGS_TABLE = "CREATE TABLE " + SHOP_TAG_TABLE_NAME
            + "(" + RELATION_SHOP_ID + " INTEGER, "
            + RELATION_TAG_ID + " INTEGER, "
            + " FOREIGN KEY("+RELATION_SHOP_ID+") REFERENCES "+SHOP_TABLE_NAME+"("+COLUMN_ID+"), "
            + " FOREIGN KEY("+RELATION_TAG_ID+") REFERENCES "+TAG_TABLE_NAME+"("+TAG_ID+"))";
    private static final String CREATE_TAGS_TABLE = "CREATE TABLE " + TAG_TABLE_NAME
            + "(" + TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TAG_NAME + " TEXT, "
            + COLUMN_NAME + " TEXT )" ;

    private static final String CREATE_SHOPS_TABLE = "CREATE TABLE " + SHOP_TABLE_NAME
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_PRICE + " TEXT, "
            + COLUMN_IMAGEID + " TEXT, "
            + COLUMN_MAPID + " TEXT, "
            + COLUMN_ISFAV + " TEXT )" ;
    private static final String CREATE_REVIEW_TABLE = "CREATE TABLE " + REVIEW_TABLE_NAME
            + "(" + REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + REVIEW_NAME + " TEXT, "
            + REVIEW_WRITING + " TEXT, "
            + COLUMN_NAME + " TEXT )" ;


    public static final String DROP_SHOPS_TABLE = "DROP TABLE IF EXISTS " + SHOP_TABLE_NAME;
    public static final String DROP_TAGS_TABLE = "DROP TABLE IF EXISTS " + TAG_TABLE_NAME;
    public static final String DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS " + REVIEW_TABLE_NAME;

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
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPS_TABLE);
        db.execSQL(CREATE_TAGS_TABLE);
        db.execSQL(CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SHOPS_TABLE);
        db.execSQL(DROP_TAGS_TABLE);
        db.execSQL(DROP_REVIEW_TABLE);
        onCreate(db);
    }

    /**
     * Inserts a new row into the Shop Table 
     * @param name
     * @param description
     * @param price
     * @param imageResourceID
     * @param mapResourceID
     */
    public void insert(String name, String description, String price, int imageResourceID, int mapResourceID){
        SQLiteDatabase db = getWritableDatabase();  //Get a reference to the database
        ContentValues values = new ContentValues();  //create a new content value to store values
        //values.put(COLUMN_ID,id);
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE,price);
        values.put(COLUMN_IMAGEID, Integer.valueOf(imageResourceID));
        values.put(COLUMN_MAPID, Integer.valueOf(mapResourceID));
        values.put(COLUMN_ISFAV, "false");
        db.insert(SHOP_TABLE_NAME, null, values);  //Inser the row into the table
    }

    /**
     * Inserts a new row in Tag Table
     * Stores a Tag and shopName
     * @param tag
     * @param shopName
     */
    public void insertTAG(String tag, String shopName){
        SQLiteDatabase db = getWritableDatabase();  //Get a reference to the database
        ContentValues values = new ContentValues();  //create a new content value to store values
        //values.put(COLUMN_ID,id);
        values.put(TAG_NAME,tag);
        values.put(COLUMN_NAME,shopName);
        db.insert(TAG_TABLE_NAME, null, values);  //Inser the row into the table
    }

    /**
     * Inserts a new row in the Reviews table
     * Stores a Review Name, Review, and ShopName
     * @param name
     * @param review
     * @param shopName
     */
    public void insertReview(String name, String review, String shopName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REVIEW_NAME,name);
        values.put(REVIEW_WRITING,review);
        values.put(COLUMN_NAME,shopName);
        db.insert(REVIEW_TABLE_NAME,null,values);
    }

    /**
     * Updates a row in the database with the passed arguments
     * @param index
     * @param name
     * @param description
     * @param price
     * @param imageResourceID
     * @param mapResourceID
     * @param isFav
     */
    public void update(int index, String name,String description,String price, int imageResourceID, int mapResourceID, String isFav){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE,price);
        values.put(COLUMN_IMAGEID, Integer.valueOf(imageResourceID));
        values.put(COLUMN_MAPID, Integer.valueOf(mapResourceID));
        values.put(COLUMN_ISFAV, isFav);
        db.update(SHOP_TABLE_NAME, values, "_id = " + index, null);
    }

    /**
     * Searches for an individual shop based on the ID
     * Grabs data from cursor and saves it into a shop object
     * Returns a shop object of the passed id
     * @param id
     * @return
     */
    public Shop getShop(int id){
        SQLiteDatabase db = getReadableDatabase();  //Get a reference to the database
        String[] projection = ALL_COLUMNS;  //Define a projection, which tells the query to return only the columns mentioned, similar to "Select column1, column2, column3"
        String selection = COLUMN_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.query(SHOP_TABLE_NAME, //a.table
                projection, //b. column names
                selection, // c. selections
                selectionArgs,//d. selections args
                null,//e. group by
                null,//f. having
                null,//g. order by
                null);//h. limit
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
        String price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE));
        int imageResourceID = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGEID));
        int mapResourceID = cursor.getInt(cursor.getColumnIndex(COLUMN_MAPID));
        String isFav = cursor.getString(cursor.getColumnIndex(COLUMN_ISFAV));
        return new Shop(name,description,price,imageResourceID,mapResourceID,isFav); //temp
    }

    /**
     * Returns cursor of list of all of the shops in the db
     * @return
     */
    public Cursor getShopsList(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOP_TABLE_NAME,
                ALL_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    /**
     * Searches Shop table for shops with passed query
     * Returns Cursor of list of shops
     * @param query
     * @return
     */
    public Cursor getShop(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOP_TABLE_NAME,
                ALL_COLUMNS,
                COLUMN_NAME+" LIKE ?",
                new String[]{"%"+query+"%"},
                null,
                null,
                null,
                null);
        return cursor;
    }

    /**
     * Searches Shop Table for all shops where ISFAV is true
     * Returns cursor of all favorite shops
     * @return
     */
    public Cursor getFavoriteShops(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOP_TABLE_NAME,
                ALL_COLUMNS,
                COLUMN_ISFAV+" LIKE ?",
                new String[]{"true"},
                null,
                null,
                null,
                null);
        return cursor;
    }

    /**
     * Searches Tag Table for all shop names with given tag
     * Then searches Shop table for all rows of those shop names
     * Returns cursor with all data of the shops with passed TAG
     * @param TAG
     * @return
     */
    public Cursor getShopByTag(String TAG){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TAG_TABLE_NAME,
                new String[]{COLUMN_NAME},
                TAG_NAME + " LIKE ?",
                new String[]{"%" + TAG + "%"},
                null,
                null,
                null,
                null);
        ArrayList<String> shopNames = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            shopNames.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            cursor.moveToNext();
        }
        String[] shopNameArray = new String[shopNames.size()];
        shopNameArray = shopNames.toArray(shopNameArray);
        String selection="";
        for (int i = 0; i < shopNameArray.length - 1; i++ ){
            selection = selection + COLUMN_NAME + " = ? OR ";
        }
        selection = selection + COLUMN_NAME + " = ?";
        cursor = db.query(SHOP_TABLE_NAME,
                ALL_COLUMNS,
                selection,
                shopNameArray,
                null,
                null,
                null,
                null);
        return cursor;
    }

    /**
     * Searches Tag Table for all shops with passed TAG then searches Shop table for all rows with those shops filtered by the passed query
     * Returns a Cursor of all the data for the shops with Tag and Query
     * @param TAG
     * @param QUERY
     * @return
     */
    public Cursor getShopByQueryAndTag(String TAG,String QUERY){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TAG_TABLE_NAME,
                new String[]{COLUMN_NAME},
                TAG_NAME + " LIKE ?",
                new String[]{"%" + TAG + "%"},
                null,
                null,
                null,
                null);
        ArrayList<String> shopNames = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex(COLUMN_NAME)).contains(QUERY))shopNames.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            cursor.moveToNext();
        }
        String[] shopNameArray = new String[shopNames.size()];
        shopNameArray = shopNames.toArray(shopNameArray);
        String selection="";
        for (int i = 0; i < shopNameArray.length - 1; i++ ){
            selection = selection + COLUMN_NAME + " = ? OR ";
        }
        selection = selection + COLUMN_NAME + " = ?";
        cursor = db.query(SHOP_TABLE_NAME,
                ALL_COLUMNS,
                selection,
                shopNameArray,
                null,
                null,
                null,
                null);
        return cursor;
    }

    /**
     * Searches review Table for reviews with shopName and returns all of the reviews, and review names
     * @param shopName
     * @return
     */
    public Cursor getReviews(String shopName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(REVIEW_TABLE_NAME,
                REVIEW_COLUMNS,
                COLUMN_NAME + " LIKE ?",
                new String[]{shopName},
                null,
                null,
                null,
                null);
        return cursor;

    }

    /**
     * Deletes row with passed id
     * @param id
     */
    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.delete("shops",selection,selectionArgs);
    }

}
