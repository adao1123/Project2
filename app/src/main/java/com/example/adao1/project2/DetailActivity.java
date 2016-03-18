package com.example.adao1.project2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DatabaseHelper helper = DatabaseHelper.getInstance(DetailActivity.this);
        int index = getIntent().getIntExtra(MainActivity.KEY, -1);
        Shop clickedShop = helper.getShop(index);
        TextView textView = (TextView)findViewById(R.id.descriptionID);
        textView.setText(clickedShop.getName()+ "\n" + clickedShop.getDescription()+ "\n" +clickedShop.getCostSigns());

    }
}
