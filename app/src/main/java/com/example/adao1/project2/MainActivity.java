package com.example.adao1.project2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText searchEditText;
    ListView searchListView;
    Intent detailIntent;
    ArrayList<Shop> listOfShops;
    SearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEditText = (EditText)findViewById(R.id.searchBarID);
        searchListView = (ListView)findViewById(R.id.listViewID);
        detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        listOfShops = new ArrayList<>();
        addStores();
        searchListAdapter = new SearchListAdapter(MainActivity.this, listOfShops);
        searchListView.setAdapter(searchListAdapter);


        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //TODO: Look through database and change displayed list of shops depending on edit text
                searchListAdapter.notifyDataSetChanged();
                return false;
            }
        });

        //DatabaseHelper db = new DatabaseHelper(this);
        DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.this);
        Cursor cursor = db.getShopsList();
        CursorAdapter cursorAdapter = new CursorAdapter() {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return null;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }
        }
        db.insert(1,"Kohls","$");
        db.insert(2,"Century Theaters","$$");

        Cursor cursor = db.getShopsList();
        Shop retrievedShop = db.getShop(2);

        Log.d("MainTest",retrievedShop.getName());



    }
    private void addStores(){
        //listOfShops.add(new Shop("Kohls","$$","A department store for everyone for a great price", new ArrayList<String>("Clothing","dfs","df"),android.R.drawable.ic_menu_report_image,android.R.drawable.ic_dialog_map));
    }
}
