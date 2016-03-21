package com.example.adao1.project2;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "KEY";
    public static final String FIRST_CHECK_KEY = "FIRSTCHECKKEY";
    ListView searchListView;
    ListView favListView;
    Intent detailIntent;
    ArrayList<Shop> listOfShops;
    Button tagSearchButton;
    EditText tagSeachEditText;
    SearchListAdapter searchListAdapter;
    CursorAdapter searchCursorAdapter;
    CursorAdapter favoriteCursorAdapter;
    DatabaseHelper db;
    Cursor searchCursor;
    Cursor favoritesCursor;
    private boolean firstTime = true;
    SharedPreferences preferences;
    LinearLayout homeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(Context.MODE_PRIVATE);
        firstTime = preferences.getBoolean(FIRST_CHECK_KEY, true);
        tagSeachEditText = (EditText)findViewById(R.id.searchBarID);
        tagSearchButton = (Button)findViewById(R.id.searchButtonID);
        searchListView = (ListView)findViewById(R.id.listViewID);
        favListView = (ListView)findViewById(R.id.favListViewID);
        homeLayout = (LinearLayout)findViewById(R.id.homeLayoutID);
        detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        listOfShops = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(MainActivity.this, listOfShops);

        db = DatabaseHelper.getInstance(MainActivity.this);
        populateDatabases();
        favoritesCursor = db.getFavoriteShops();

        searchCursorAdapter = new CursorAdapter(MainActivity.this, searchCursor, 0) {
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

        favoriteCursorAdapter = new CursorAdapter(MainActivity.this, favoritesCursor, 0) {
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
        favoriteCursorAdapter.swapCursor(favoritesCursor);
        favListView.setAdapter(favoriteCursorAdapter);
        handleIntent(getIntent());

        tagSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCursor = DatabaseHelper.getInstance(MainActivity.this).getShopByTag(tagSeachEditText.getText().toString());
                homeLayout.setVisibility(View.INVISIBLE);
                searchListView.setVisibility(View.VISIBLE);
                searchCursorAdapter.swapCursor(searchCursor);
                searchListView.setAdapter(searchCursorAdapter);
                searchCursorAdapter.notifyDataSetChanged();
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                searchCursor.moveToFirst();
                searchCursor.moveToPosition(position);
                detailIntent.putExtra(KEY, searchCursor.getInt(searchCursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                startActivity(detailIntent);
            }
        });
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
                favoritesCursor.moveToFirst();
                favoritesCursor.moveToPosition(position);
                detailIntent.putExtra(KEY, favoritesCursor.getInt(favoritesCursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
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
            searchCursor = DatabaseHelper.getInstance(MainActivity.this).getShop(query);
            homeLayout.setVisibility(View.INVISIBLE);
            searchListView.setVisibility(View.VISIBLE);
            searchCursorAdapter.swapCursor(searchCursor);
            searchListView.setAdapter(searchCursorAdapter);
            searchCursorAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onBackPressed() {
        homeLayout.setVisibility(View.VISIBLE);
        searchListView.setVisibility(View.INVISIBLE);
        favoritesCursor = db.getFavoriteShops();
        favoriteCursorAdapter.swapCursor(favoritesCursor);
        favoriteCursorAdapter.notifyDataSetChanged();
        //super.onBackPressed();
    }

    private void populateDatabases(){
        if (!firstTime) return;
        firstTime = false;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_CHECK_KEY,firstTime);
        editor.commit();
        db.insert(getString(R.string.AbercrombieName), getString(R.string.AbercromieDescription), "$$", 1, 2);
        db.insert(getString(R.string.AdidasName), getString(R.string.AdidasDescription), "$$", 4, 5);
        db.insert(getString(R.string.AeropostaleName), getString(R.string.AeropostaleDescription), "$", 4, 5);
        db.insert(getString(R.string.AldoName), getString(R.string.AldoDescription), "$$", 4, 5);
        db.insert(getString(R.string.AmericanEagleName), getString(R.string.AmericanEagleDescription), "$$", 4, 5);
        db.insert(getString(R.string.AndersenBakeryName), getString(R.string.AndersenBakeryDescription), "$", 4, 5);
        db.insert(getString(R.string.ArmaniExchangeName), getString(R.string.ArmaniExchangeDescription), "$", 4, 5);
        db.insert(getString(R.string.AuntieAnneName), getString(R.string.AuntieAnneDescription), "$", 4, 5);
        db.insert(getString(R.string.BananaRepublicName), getString(R.string.BananaRepublicDescription), "$", 4, 5);
        db.insert(getString(R.string.BathBodyWorksName), getString(R.string.BathBodyWorksDescription), "$", 4, 5);
        db.insert(getString(R.string.BeautyPalaceName), getString(R.string.BeautyPalaceDescription), "$", 4, 5);
        db.insert(getString(R.string.BebeName), getString(R.string.BebeDescription), "$", 4, 5);
        db.insert(getString(R.string.BedBathName), getString(R.string.BedBathBeyondDescription), "$", 4, 5);
        db.insert(getString(R.string.BevmoName), getString(R.string.BevmoDescription), "$", 4, 5);
        db.insert(getString(R.string.BoseName), getString(R.string.BoseDescription), "$", 4, 5);
        db.insert(getString(R.string.BounceRamaName), getString(R.string.BounceARamaDescription), "$", 4, 5);
        db.insert(getString(R.string.BurlingtonName), getString(R.string.BurlingtonDescription), "$", 4, 5);
        db.insert(getString(R.string.CajunGrillName), getString(R.string.CajunGrillDescription), "$", 4, 5);
        db.insert(getString(R.string.CalvinKleinName), getString(R.string.CalvinKleinDesciption), "$", 4, 5);
        db.insert(getString(R.string.CenturyTheatresName), getString(R.string.CenturyTheatresDescription), "$", 4, 5);
        db.insert(getString(R.string.ChampsName), getString(R.string.ChampsDescription), "$", 4, 5);
        db.insert(getString(R.string.ChipotleName), getString(R.string.ChopotleDescription), "$", 4, 5);
        db.insert(getString(R.string.CinnabonName), getString(R.string.CinnabonDescription), "$", 4, 5);
        db.insert(getString(R.string.ClairesName), getString(R.string.ClairesDescription), "$", 4, 5);
        db.insert(getString(R.string.CoachName), getString(R.string.CoachDescription), "$", 4, 5);
        db.insert(getString(R.string.ColdStoneName), getString(R.string.ColdStoneDescription), "$", 4, 5);
        db.insert(getString(R.string.ColumbiaName), getString(R.string.ColumbiaDescription), "$", 4, 5);
        db.insert(getString(R.string.CottonOnName), getString(R.string.CottonOnDescription), "$", 4, 5);
        db.insert(getString(R.string.CrocsName), getString(R.string.CrocsDescription), "$", 4, 5);
        db.insert(getString(R.string.DaveBusterName), getString(R.string.DaveBusterDecription), "$", 4, 5);
        db.insert(getString(R.string.DippinDotsName), getString(R.string.DippinDotsDescription), "$", 4, 5);
        db.insert(getString(R.string.DKNYName), getString(R.string.DKNYDescription), "$", 4, 5);
        db.insert(getString(R.string.ExpressName), getString(R.string.ExpressDescription), "$", 4, 5);
        db.insert(getString(R.string.FamousFootwearName), getString(R.string.FamousFootwearDescription), "$", 4, 5);
        db.insert(getString(R.string.FinishLineName), getString(R.string.FinishLineDescription), "$", 4, 5);
        db.insert(getString(R.string.FootLockerName), getString(R.string.FootLockerDescription), "$", 4, 5);
        db.insert(getString(R.string.Forever21Name), getString(R.string.Forever21Description), "$", 4, 5);
        db.insert(getString(R.string.GameStopName), getString(R.string.GanmeStopDescription), "$", 4, 5);
        db.insert(getString(R.string.GapName), getString(R.string.GapDescription), "$", 4, 5);
        db.insert(getString(R.string.GhirardelliName), getString(R.string.GhirardelliDescription), "$", 4, 5);
        db.insert(getString(R.string.GNCName), getString(R.string.GNCDescription), "$", 4, 5);
        db.insert(getString(R.string.HMName), getString(R.string.HMDescription), "$", 4, 5);
        db.insert(getString(R.string.HollisterName), getString(R.string.HollisterDescription), "$", 4, 5);

        db.insertTAG(getString(R.string.Clothing), getString(R.string.AbercrombieName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.AdidasName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.AeropostaleName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.AmericanEagleName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.ArmaniExchangeName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.BananaRepublicName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.BebeName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.BurlingtonName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.CalvinKleinName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.ChampsName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.ColumbiaName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.CottonOnName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.DKNYName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.ExpressName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.FootLockerName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.Forever21Name));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.GapName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.HMName));
        db.insertTAG(getString(R.string.Clothing), getString(R.string.HollisterName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.AdidasName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.AldoName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.BurlingtonName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.ChampsName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.ColumbiaName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.CottonOnName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.CrocsName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.DKNYName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.FamousFootwearName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.FinishLineName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.FootLockerName));
        db.insertTAG(getString(R.string.Shoe), getString(R.string.GapName));
        db.insertTAG(getString(R.string.Electronics), getString(R.string.BoseName));
        db.insertTAG(getString(R.string.Electronics), getString(R.string.GameStopName));
        db.insertTAG(getString(R.string.Entertainment), getString(R.string.BounceRamaName));
        db.insertTAG(getString(R.string.Entertainment), getString(R.string.CenturyTheatresName));
        db.insertTAG(getString(R.string.Entertainment), getString(R.string.DaveBusterName));
        db.insertTAG(getString(R.string.Food), getString(R.string.AndersenBakeryName));
        db.insertTAG(getString(R.string.Food), getString(R.string.AuntieAnneName));
        db.insertTAG(getString(R.string.Food), getString(R.string.CajunGrillName));
        db.insertTAG(getString(R.string.Food), getString(R.string.ChipotleName));
        db.insertTAG(getString(R.string.Food), getString(R.string.CinnabonName));
        db.insertTAG(getString(R.string.Food), getString(R.string.ColdStoneName));
        db.insertTAG(getString(R.string.Food), getString(R.string.DippinDotsName));
        db.insertTAG(getString(R.string.Food), getString(R.string.GhirardelliName));
        db.insertTAG(getString(R.string.Jewelry), getString(R.string.BeautyPalaceName));
        db.insertTAG(getString(R.string.Jewelry), getString(R.string.BebeName));
        db.insertTAG(getString(R.string.Jewelry), getString(R.string.ClairesName));
        db.insertTAG(getString(R.string.Specialty), getString(R.string.BathBodyWorksName));
        db.insertTAG(getString(R.string.Specialty), getString(R.string.BeautyPalaceName));
        db.insertTAG(getString(R.string.Specialty), getString(R.string.BedBathName));
        db.insertTAG(getString(R.string.Specialty), getString(R.string.BevmoName));
        db.insertTAG(getString(R.string.Specialty), getString(R.string.GhirardelliName));
        db.insertTAG(getString(R.string.Specialty),getString(R.string.GNCName));
    }

}
