package com.example.adao1.project2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText searchEditText;
    ListView searchListView;
    Intent detailIntent;
    ArrayList<Shop> listOfShops;
    SearchListAdapter searchListAdapter;
    CursorAdapter searchCursorAdapter;

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

        //DatabaseHelper db = new DatabaseHelper(this);
        DatabaseHelper db = DatabaseHelper.getInstance(MainActivity.this);
        Cursor cursor = db.getShopsList();
        searchCursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.search_list_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nameText = (TextView)view.findViewById(R.id.searchTextID);
                nameText.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
            }
        };
        searchListView.setAdapter(searchCursorAdapter);



        db.insert(1,"Kohls","Cheap price for cheap clothing","$",1,2);
        db.insert(2,"Century Theatres","Pretty cool theaters","$",4,5);
        db.insert(3,"Abercrombie Outlet","","$",4,5);

        handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = DatabaseHelper.getInstance(MainActivity.this).getShopsList(query);
            searchCursorAdapter.swapCursor(cursor);
            searchCursorAdapter.notifyDataSetChanged();
        }
    }

    private void addStores(){
        //listOfShops.add(new Shop("Kohls","$$","A department store for everyone for a great price", new ArrayList<String>("Clothing","dfs","df"),android.R.drawable.ic_menu_report_image,android.R.drawable.ic_dialog_map));
    }

}
