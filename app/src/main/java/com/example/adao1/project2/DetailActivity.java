package com.example.adao1.project2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView description;
    TextView cost;
    TextView reviewDisplayTextView;
    Button favoritesButton;
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
        reviewEditText = (EditText)findViewById(R.id.reviewEditTextID);
        reviewNameEditText = (EditText)findViewById(R.id.reviewNameEditTextID);
        reviewSubmitButton = (Button)findViewById(R.id.reviewSubmitButtonID);
        reviewDisplayTextView = (TextView)findViewById(R.id.reviewDisplayTextViewID);
        getReviews();

        reviewSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewName = reviewNameEditText.getText().toString();
                String review  = reviewEditText.getText().toString();
                helper.insertReview(reviewName,review,clickedShop.getName());
                reviewNameEditText.setText("");
                reviewEditText.setText("");
                getReviews();
            }
        });

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

    private void getReviews(){
        if (helper.getReviews(clickedShop.getName())==null) return;
        reviewCursor = helper.getReviews(clickedShop.getName());
        reviewNamesList = new ArrayList<>();
        reviewList = new ArrayList<>();
        reviewText = "";
        reviewCursor.moveToFirst();
        while(!reviewCursor.isAfterLast()){
            reviewNamesList.add(reviewCursor.getString(reviewCursor.getColumnIndex(helper.REVIEW_NAME)));
            reviewCursor.moveToNext();
        }
        reviewCursor.moveToFirst();
        while(!reviewCursor.isAfterLast()){
            reviewList.add(reviewCursor.getString(reviewCursor.getColumnIndex(helper.REVIEW_WRITING)));
            reviewCursor.moveToNext();
        }
        for (int i = 0; i<reviewList.size();i++){
            reviewText = reviewText + reviewNamesList.get(i) + "\n" + reviewList.get(i) + "\n\n";
        }
        reviewDisplayTextView.setText(reviewText);
    }

    @Override
    public void onBackPressed() {
        helper.update(index, clickedShop.getName(), clickedShop.getDescription(), clickedShop.getCostSigns(), clickedShop.getShopImageResourceID(), clickedShop.getDirectoryMapResourceID(), clickedShop.getIsFav());
        super.onBackPressed();
    }
}
