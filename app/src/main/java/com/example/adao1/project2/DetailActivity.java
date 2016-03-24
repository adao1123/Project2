package com.example.adao1.project2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView description;
    TextView cost;
    TextView reviewDisplayTextView;
    ImageView favoritesButton;
    Button reviewOpenButton;
    ImageView storelogo;
    int imageID;
    int mapID;
    int index;
    EditText reviewEditText;
    EditText reviewNameEditText;
    DatabaseHelper helper;
    Button reviewSubmitButton;
    Shop clickedShop;
    Cursor reviewCursor;
    ArrayList<String> reviewNamesList;
    ArrayList<String> reviewList;
    String reviewText;
    ListView reviewListView;
    CursorAdapter cursorAdapter;

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
        favoritesButton = (ImageView)findViewById(R.id.favButtonID);
        reviewEditText = (EditText)findViewById(R.id.reviewEditTextID);
        reviewNameEditText = (EditText)findViewById(R.id.reviewNameEditTextID);
        reviewSubmitButton = (Button)findViewById(R.id.reviewSubmitButtonID);
        reviewOpenButton = (Button)findViewById(R.id.reviewOpenButtonID);
        storelogo = (ImageView)findViewById(R.id.shopImageID);
        reviewListView = (ListView)findViewById(R.id.reviewListViewID);



        getReviews();

        reviewOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewEditText.setVisibility(View.VISIBLE);
                reviewNameEditText.setVisibility(View.VISIBLE);
                reviewSubmitButton.setVisibility(View.VISIBLE);
                reviewOpenButton.setVisibility(View.INVISIBLE);
            }
        });

        reviewSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewName = reviewNameEditText.getText().toString();
                String review = reviewEditText.getText().toString();
                helper.insertReview(reviewName, review, clickedShop.getName());
                reviewNameEditText.setText("");
                reviewEditText.setText("");
                getReviews();
                cursorAdapter.notifyDataSetChanged();
                reviewEditText.setVisibility(View.INVISIBLE);
                reviewNameEditText.setVisibility(View.INVISIBLE);
                reviewSubmitButton.setVisibility(View.INVISIBLE);
                reviewOpenButton.setVisibility(View.VISIBLE);
            }
        });

        reviewListView.setAdapter(cursorAdapter);
        storelogo.setImageResource(clickedShop.getShopImageResourceID());
        cost. setText(clickedShop.getCostSigns());
        title.setText(clickedShop.getName());
        description.setText(clickedShop.getDescription());
        Log.d("Main",clickedShop.getIsFav());
        if(clickedShop.getIsFav().equals("true")) favoritesButton.setImageResource(R.drawable.hearticon);
        else favoritesButton.setImageResource(R.drawable.heartemptyicon);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickedShop.getIsFav().equals("false")){
                    clickedShop.setIsFav("true");
                    favoritesButton.setImageResource(R.drawable.hearticon);
                }else{
                    clickedShop.setIsFav("false");
                    favoritesButton.setImageResource(R.drawable.heartemptyicon);
                }
            }
        });


    }

    private void getReviews(){
        if (helper.getReviews(clickedShop.getName())==null) return;
        reviewCursor = helper.getReviews(clickedShop.getName());
        reviewCursor.moveToFirst();
        cursorAdapter = new CursorAdapter(DetailActivity.this, reviewCursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.review_list_item,parent,false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView nameText = (TextView)view.findViewById(R.id.reviewNameTextViewID);
                TextView reviewText = (TextView)view.findViewById(R.id.reviewTextViewID);
                getReviews();
                nameText.setText(cursor.getString(cursor.getColumnIndex(helper.REVIEW_NAME)));
                reviewText.setText(cursor.getString(cursor.getColumnIndex(helper.REVIEW_WRITING)));
            }
        };
    }

    @Override
    public void onBackPressed() {
        helper.update(index,
                clickedShop.getName(),
                clickedShop.getDescription(),
                clickedShop.getCostSigns(),
                clickedShop.getShopImageResourceID(),
                clickedShop.getDirectoryMapResourceID(),
                clickedShop.getIsFav());
        super.onBackPressed();
    }
}
