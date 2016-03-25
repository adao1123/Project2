package com.example.adao1.project2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import it.sephiroth.android.library.widget.HListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String DETAIL_KEY = "DETAIL_KEY";
    public static final String FIRST_CHECK_KEY = "FIRSTCHECKKEY";
    private ListView searchListView;
    private ScrollView scrollView;
    private Intent detailIntent;
    private String [] typesOfShops;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private Cursor searchCursor;
    private Cursor favoritesCursor;
    private Cursor clothingCursor;
    private Cursor shoesCursor;
    private Cursor electronicCursor;
    private Cursor entertainmentCursor;
    private Cursor foodCursor;
    private Cursor jewelryCursor;
    private Cursor specialtyCursor;
    private CursorAdapter searchCursorAdapter;
    private CursorAdapter favoriteCursorAdapter;
    private CursorAdapter clothingCursorAdapter;
    private CursorAdapter shoesCursorAdapter;
    private CursorAdapter electronicsCursorAdapter;
    private CursorAdapter entertainmentCursorAdapter;
    private CursorAdapter foodCursorAdapter;
    private CursorAdapter jewelryCursorAdapter;
    private CursorAdapter specialtyCursorAdapter;
    private HListView favoriteHlistview;
    private HListView clothingHlistview;
    private HListView shoesHlistview;
    private HListView electronicsHlistview;
    private HListView entertainmentHlistview;
    private HListView foodHlistview;
    private HListView jewelryHlistview;
    private HListView specialtyHlistview;
    private DatabaseHelper db;
    private SharedPreferences preferences;
    private static boolean firstTime = true;
    private static boolean justOpened = true;
    private static String currentTag;
    private static int currentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(Context.MODE_PRIVATE);
        firstTime = preferences.getBoolean(FIRST_CHECK_KEY, true);
        detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        db = DatabaseHelper.getInstance(MainActivity.this);
        initViews();
        populateDatabases();
        getCursors();
        setSpinnerOptions();
        setSpinner();
        setAllCursorAdapters();
        handleIntent(getIntent());
        setItemClickListerners();
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

    @Override
    protected void onResume() {
        favoritesCursor = db.getFavoriteShops();
        favoritesCursor.moveToFirst();
        favoriteCursorAdapter.swapCursor(favoritesCursor);
        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0) Log.d("Main","on item selected");
        else if(position==1)searchCursor = db.getShopsList();
        else searchCursor = db.getShopByTag(typesOfShops[position]);
        currentTag = typesOfShops[position];
        currentSpinner = position;
        if(!justOpened) {
            scrollView.setVisibility(View.INVISIBLE);
            searchListView.setVisibility(View.VISIBLE);
        }
        justOpened = false;
        searchCursorAdapter.swapCursor(searchCursor);
        searchListView.setAdapter(searchCursorAdapter);
        searchCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        scrollView.setVisibility(View.VISIBLE);
        searchListView.setVisibility(View.INVISIBLE);
        favoritesCursor = db.getFavoriteShops();
        favoriteCursorAdapter.swapCursor(favoritesCursor);
    }

    private void initViews(){
        searchListView = (ListView)findViewById(R.id.listViewID);
        spinner = (Spinner)findViewById(R.id.spinnerID);
        scrollView = (ScrollView)findViewById(R.id.scrollViewID);
        favoriteHlistview = (HListView)findViewById(R.id.favoriteHorizontalListViewID);
        clothingHlistview = (HListView)findViewById(R.id.clothingHorizontalListViewID);
        shoesHlistview = (HListView)findViewById(R.id.shoesHorizontalListViewID);
        electronicsHlistview = (HListView)findViewById(R.id.electronicsHorizontalListViewID);
        entertainmentHlistview = (HListView)findViewById(R.id.entertainmentHorizontalListViewID);
        foodHlistview = (HListView)findViewById(R.id.foodHorizontalListViewID);
        jewelryHlistview = (HListView)findViewById(R.id.jewelryHorizontalListViewID);
        specialtyHlistview = (HListView)findViewById(R.id.specialtyHorizontalListViewID);
    }

    private void getCursors(){
        favoritesCursor = db.getFavoriteShops();
        clothingCursor = db.getShopByTag(getString(R.string.Clothing));
        shoesCursor = db.getShopByTag(getString(R.string.Shoe));
        electronicCursor = db.getShopByTag(getString(R.string.Electronics));
        entertainmentCursor = db.getShopByTag(getString(R.string.Entertainment));
        foodCursor = db.getShopByTag(getString(R.string.Food));
        jewelryCursor = db.getShopByTag(getString(R.string.Jewelry));
        specialtyCursor = db.getShopByTag(getString(R.string.Specialty));
    }

    private void setSpinnerOptions(){
        String[] typesOfShops2 = {"","All",
                getString(R.string.Clothing),
                getString(R.string.Shoe),
                getString(R.string.Electronics),
                getString(R.string.Entertainment),
                getString(R.string.Food),
                getString(R.string.Jewelry),
                getString(R.string.Specialty)};
        typesOfShops = typesOfShops2;
    }

    private void setAllCursorAdapters(){
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
        setFavoriteHorizontalCursorAdapters(favoritesCursor,favoriteHlistview);
        setHorizontalCursorAdapters(clothingCursorAdapter,clothingCursor,clothingHlistview);
        setHorizontalCursorAdapters(shoesCursorAdapter,shoesCursor,shoesHlistview);
        setHorizontalCursorAdapters(electronicsCursorAdapter,electronicCursor,electronicsHlistview);
        setHorizontalCursorAdapters(entertainmentCursorAdapter,entertainmentCursor,entertainmentHlistview);
        setHorizontalCursorAdapters(foodCursorAdapter, foodCursor, foodHlistview);
        setHorizontalCursorAdapters(jewelryCursorAdapter, jewelryCursor, jewelryHlistview);
        setHorizontalCursorAdapters(specialtyCursorAdapter, specialtyCursor, specialtyHlistview);
    }

    private void setItemClickListerners(){
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                searchCursor.moveToFirst();
                searchCursor.moveToPosition(position);
                detailIntent.putExtra(DETAIL_KEY, searchCursor.getInt(searchCursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                startActivity(detailIntent);
            }
        });
        setFavoriteHorizontalListClickListener(favoriteHlistview);
        setHorizontalListClickListener(clothingCursor, clothingHlistview);
        setHorizontalListClickListener(shoesCursor, shoesHlistview);
        setHorizontalListClickListener(electronicCursor, electronicsHlistview);
        setHorizontalListClickListener(entertainmentCursor, entertainmentHlistview);
        setHorizontalListClickListener(foodCursor, foodHlistview);
        setHorizontalListClickListener(jewelryCursor, jewelryHlistview);
        setHorizontalListClickListener(specialtyCursor, specialtyHlistview);
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (currentSpinner==0||currentSpinner==1)searchCursor = db.getShop(query);
            else{
                searchCursor = db.getShopByQueryAndTag(currentTag,query);
            }
            scrollView.setVisibility(View.INVISIBLE);
            searchListView.setVisibility(View.VISIBLE);
            searchCursorAdapter.swapCursor(searchCursor);
            searchListView.setAdapter(searchCursorAdapter);
            searchCursorAdapter.notifyDataSetChanged();
        }
    }

    private void setSpinner(){
        spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,typesOfShops);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setHorizontalCursorAdapters(CursorAdapter cursorAdapter, Cursor cursor, HListView hListView){
        cursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.home_scroll_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ImageView imageView = (ImageView)view.findViewById(R.id.scrollItemImageID);
                TextView textView = (TextView)view.findViewById(R.id.scrollItemTextID);
                imageView.setImageResource(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGEID)));
                textView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
            }
        };
        hListView.setAdapter(cursorAdapter);
    }

    private void setFavoriteHorizontalCursorAdapters( Cursor cursor, HListView hListView){
        favoriteCursorAdapter = new CursorAdapter(MainActivity.this, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.home_scroll_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ImageView imageView = (ImageView)view.findViewById(R.id.scrollItemImageID);
                TextView textView = (TextView)view.findViewById(R.id.scrollItemTextID);
                imageView.setImageResource(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGEID)));
                textView.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
            }
        };
        hListView.setAdapter(favoriteCursorAdapter);
    }

    private void setHorizontalListClickListener(final Cursor cursor, HListView hListView){
        hListView.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                cursor.moveToFirst();
                cursor.moveToPosition(position);
                detailIntent.putExtra(DETAIL_KEY, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                startActivity(detailIntent);
            }
        });
    }

    private void setFavoriteHorizontalListClickListener(HListView hListView){
        hListView.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                favoritesCursor.moveToFirst();
                favoritesCursor.moveToPosition(position);
                detailIntent.putExtra(DETAIL_KEY, favoritesCursor.getInt(favoritesCursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                startActivity(detailIntent);
            }
        });
    }

    private void populateDatabases(){
        if (!firstTime) return;
        firstTime = false;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_CHECK_KEY, firstTime);
        editor.commit();
        db.insert(getString(R.string.AbercrombieName), getString(R.string.AbercromieDescription), "$$", R.drawable.abercrombie, 2);
        db.insert(getString(R.string.AdidasName), getString(R.string.AdidasDescription), "$$", R.drawable.adidas, 5);
        db.insert(getString(R.string.AeropostaleName), getString(R.string.AeropostaleDescription), "$",  R.drawable.aeropostle, 5);
        db.insert(getString(R.string.AldoName), getString(R.string.AldoDescription), "$$", R.drawable.aldo, 5);
        db.insert(getString(R.string.AmericanEagleName), getString(R.string.AmericanEagleDescription), "$$", R.drawable.americaneaglelogo, 5);
        db.insert(getString(R.string.AndersenBakeryName), getString(R.string.AndersenBakeryDescription), "$", R.drawable.andersonsbakerylogo, 5);
        db.insert(getString(R.string.ArmaniExchangeName), getString(R.string.ArmaniExchangeDescription), "$", R.drawable.armaniexchangelogo, 5);
        db.insert(getString(R.string.AuntieAnneName), getString(R.string.AuntieAnneDescription), "$", R.drawable.auntieannelogo, 5);
        db.insert(getString(R.string.BananaRepublicName), getString(R.string.BananaRepublicDescription), "$", R.drawable.bananarepubliclogo, 5);
        db.insert(getString(R.string.BathBodyWorksName), getString(R.string.BathBodyWorksDescription), "$", R.drawable.bathbodyworkslogo, 5);
        db.insert(getString(R.string.BeautyPalaceName), getString(R.string.BeautyPalaceDescription), "$", R.drawable.beautypalacelogo, 5);
        db.insert(getString(R.string.BebeName), getString(R.string.BebeDescription), "$", R.drawable.bebe, 5);
        db.insert(getString(R.string.BedBathName), getString(R.string.BedBathBeyondDescription), "$", R.drawable.bedbathbeyond, 5);
        db.insert(getString(R.string.BevmoName), getString(R.string.BevmoDescription), "$", R.drawable.bevmologo, 5);
        db.insert(getString(R.string.BoseName), getString(R.string.BoseDescription), "$", R.drawable.boselogo, 5);
        db.insert(getString(R.string.BounceRamaName), getString(R.string.BounceARamaDescription), "$", R.drawable.bounceramalogo, 5);
        db.insert(getString(R.string.BurlingtonName), getString(R.string.BurlingtonDescription), "$", R.drawable.burlingtonlogo, 5);
        db.insert(getString(R.string.CajunGrillName), getString(R.string.CajunGrillDescription), "$", R.drawable.cajungrilllogo, 5);
        db.insert(getString(R.string.CalvinKleinName), getString(R.string.CalvinKleinDesciption), "$", R.drawable.calvinkleinlogo, 5);
        db.insert(getString(R.string.CenturyTheatresName), getString(R.string.CenturyTheatresDescription), "$", R.drawable.centurytheatres, 5);
        db.insert(getString(R.string.ChampsName), getString(R.string.ChampsDescription), "$", R.drawable.champs, 5);
        db.insert(getString(R.string.ChipotleName), getString(R.string.ChopotleDescription), "$", R.drawable.chipotlelogo, 5);
        db.insert(getString(R.string.CinnabonName), getString(R.string.CinnabonDescription), "$", R.drawable.cinnabonlogo, 5);
        db.insert(getString(R.string.ClairesName), getString(R.string.ClairesDescription), "$", R.drawable.claireslogo, 5);
        db.insert(getString(R.string.CoachName), getString(R.string.CoachDescription), "$", R.drawable.coach, 5);
        db.insert(getString(R.string.ColdStoneName), getString(R.string.ColdStoneDescription), "$", R.drawable.coldstonelogo, 5);
        db.insert(getString(R.string.ColumbiaName), getString(R.string.ColumbiaDescription), "$", R.drawable.columbialogo, 5);
        db.insert(getString(R.string.CottonOnName), getString(R.string.CottonOnDescription), "$", R.drawable.cottononlogo, 5);
        db.insert(getString(R.string.CrocsName), getString(R.string.CrocsDescription), "$", R.drawable.crocslogo, 5);
        db.insert(getString(R.string.DaveBusterName), getString(R.string.DaveBusterDecription), "$", R.drawable.dnblogo, 5);
        db.insert(getString(R.string.DippinDotsName), getString(R.string.DippinDotsDescription), "$", R.drawable.dippindotslogo, 5);
        db.insert(getString(R.string.DKNYName), getString(R.string.DKNYDescription), "$", R.drawable.dknylogo, 5);
        db.insert(getString(R.string.ExpressName), getString(R.string.ExpressDescription), "$", R.drawable.express, 5);
        db.insert(getString(R.string.FamousFootwearName), getString(R.string.FamousFootwearDescription), "$", R.drawable.famousfootwearlogo, 5);
        db.insert(getString(R.string.FinishLineName), getString(R.string.FinishLineDescription), "$", R.drawable.finishlinelogo, 5);
        db.insert(getString(R.string.FootLockerName), getString(R.string.FootLockerDescription), "$", R.drawable.footlockerlogo, 5);
        db.insert(getString(R.string.Forever21Name), getString(R.string.Forever21Description), "$", R.drawable.forever21logo, 5);
        db.insert(getString(R.string.GameStopName), getString(R.string.GanmeStopDescription), "$", R.drawable.gamestoplogo, 5);
        db.insert(getString(R.string.GapName), getString(R.string.GapDescription), "$", R.drawable.gaplogo, 5);
        db.insert(getString(R.string.GhirardelliName), getString(R.string.GhirardelliDescription), "$", R.drawable.ghirardellilogo, 5);
        db.insert(getString(R.string.GNCName), getString(R.string.GNCDescription), "$", R.drawable.gnclogo, 5);
        db.insert(getString(R.string.HMName), getString(R.string.HMDescription), "$", R.drawable.hmlogo, 5);
        db.insert(getString(R.string.HollisterName), getString(R.string.HollisterDescription), "$", R.drawable.hollisterlogo, 5);

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
