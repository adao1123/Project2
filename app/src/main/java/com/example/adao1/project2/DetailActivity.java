package com.example.adao1.project2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView description;
    TextView cost;
    Button favoritesButton;
    int imageID;
    int mapID;
    int index;
    DatabaseHelper helper;
    Shop clickedShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        helper = DatabaseHelper.getInstance(DetailActivity.this);
        index = getIntent().getIntExtra(MainActivity.KEY, -1);
        clickedShop = helper.getShop(index);
        title = (TextView)findViewById(R.id.shopNameID);
        cost = (TextView)findViewById(R.id.costID);
        description = (TextView)findViewById(R.id.descriptionID);
        favoritesButton = (Button)findViewById(R.id.favButtonID);

        title.setText(clickedShop.getName());
        description.setText(clickedShop.getDescription());
        cost. setText(clickedShop.getCostSigns());
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickedShop.getIsFav()=="false"){
                    clickedShop.setIsFav("true");
                }else{
                    clickedShop.setIsFav("false");
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        helper.update(index, clickedShop.getName(), clickedShop.getDescription(), clickedShop.getCostSigns(), clickedShop.getShopImageResourceID(), clickedShop.getDirectoryMapResourceID(), clickedShop.getIsFav());
        super.onBackPressed();
    }
}
