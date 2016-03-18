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
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "KEY";
    EditText searchEditText;
    ListView searchListView;
    Intent detailIntent;
    ArrayList<Shop> listOfShops;
    SearchListAdapter searchListAdapter;
    CursorAdapter searchCursorAdapter;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEditText = (EditText)findViewById(R.id.searchBarID);
        searchListView = (ListView)findViewById(R.id.listViewID);
        detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        listOfShops = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(MainActivity.this, listOfShops);
        searchListView.setAdapter(searchListAdapter);

        //DatabaseHelper db = new DatabaseHelper(this);
        db = DatabaseHelper.getInstance(MainActivity.this);
        final Cursor cursor = db.getShopsList();
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


        populateDatabases();
        handleIntent(getIntent());
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
                cursor.moveToPosition(position);
                detailIntent.putExtra(KEY,cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                startActivity(detailIntent);
            }
        });

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
            Cursor cursor = DatabaseHelper.getInstance(MainActivity.this).getShop(query);
            searchCursorAdapter.swapCursor(cursor);
            searchCursorAdapter.notifyDataSetChanged();
        }
    }

    private void populateDatabases(){
        db.insert(getString(R.string.AbercrombieName),getString(R.string.AbercromieDescription),"$$",1,2);
        db.insert(getString(R.string.AdidasName),getString(R.string.AdidasDescription),"$$",4,5);
        db.insert(getString(R.string.AeropostaleName),getString(R.string.AeropostaleDescription),"$",4,5);
        db.insert(getString(R.string.AldoName),getString(R.string.AldoDescription),"$$",4,5);
        db.insert(getString(R.string.AmericanEagleName),getString(R.string.AmericanEagleDescription),"$$",4,5);
        db.insert(getString(R.string.AndersenBakeryName),getString(R.string.AndersenBakeryDescription),"$",4,5);
        db.insert(getString(R.string.ArmaniExchangeName),getString(R.string.ArmaniExchangeDescription),"$",4,5);
        db.insert(getString(R.string.AuntieAnneName),getString(R.string.AuntieAnneDescription),"$",4,5);
        db.insert(getString(R.string.BananaRepublicName),getString(R.string.BananaRepublicDescription),"$",4,5);
        db.insert(getString(R.string.BathBodyWorksName),getString(R.string.BathBodyWorksDescription),"$",4,5);
        db.insert(getString(R.string.BeautyPalaceName),getString(R.string.BeautyPalaceDescription),"$",4,5);
        db.insert(getString(R.string.BebeName),getString(R.string.BebeDescription),"$",4,5);
        db.insert(getString(R.string.BedBathName),getString(R.string.BedBathBeyondDescription),"$",4,5);
        db.insert(getString(R.string.BevmoName),getString(R.string.BevmoDescription),"$",4,5);
        db.insert(getString(R.string.BoseName),getString(R.string.BoseDescription),"$",4,5);
        db.insert(getString(R.string.BounceRamaName),getString(R.string.BounceARamaDescription),"$",4,5);
        db.insert(getString(R.string.BurlingtonName),getString(R.string.BurlingtonDescription),"$",4,5);

    }

}
