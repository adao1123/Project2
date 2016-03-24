package com.example.adao1.project2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adao1.project2.R;
import com.example.adao1.project2.Shop;

import java.util.ArrayList;

/**
 * Created by adao1 on 3/13/2016.
 */
public class HomeHorizontalListAdapter extends ArrayAdapter<Shop> {
    ArrayList<Shop> shopArrayList;

    public HomeHorizontalListAdapter(Context context, ArrayList<Shop> shopArrayList) {
        super(context, -1, shopArrayList);
        this.shopArrayList = shopArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_scroll_item,parent,false);
        ImageView imageView = (ImageView)rowItem.findViewById(R.id.scrollItemImageID);
        TextView textView = (TextView)rowItem.findViewById(R.id.scrollItemTextID);
        Shop selectedShop = shopArrayList.get(position);
        imageView.setImageResource(selectedShop.getShopImageResourceID());
        textView.setText(selectedShop.getName());
        return rowItem;
    }
}
