package com.example.adao1.project2;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adao1 on 3/11/2016.
 */
public class Shop implements Serializable{
    private int id;

    public Shop(int id, String name, String costSigns) {
        this.id = id;
        this.name = name;
        this.costSigns = costSigns;
    }

    private String name;
    private String costSigns;
    private String description;
    private ArrayList<String> tags;
    private int shopImageResourceID;
    private int directoryMapResourceID;
    private String isFav;

    public Shop(String name, String costSigns) {
        this.name = name;
        this.costSigns = costSigns;
    }

    public Shop(String name, String description, String costSigns, int shopImageRecourseID, int directoryMapResourceID, String isFav) {
        this.name = name;
        this.costSigns = costSigns;
        this.description = description;
        //this.tags = tags;
        this.shopImageResourceID = shopImageRecourseID;
        this.directoryMapResourceID = directoryMapResourceID;
        this.isFav = isFav;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCostSigns() {
        return costSigns;
    }

    public void setCostSigns(String costSigns) {
        this.costSigns = costSigns;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getShopImageResourceID() {
        return shopImageResourceID;
    }

    public void setShopImageResourceID(int shopImageResourceID) {
        this.shopImageResourceID = shopImageResourceID;
    }

    public int getDirectoryMapResourceID() {
        return directoryMapResourceID;
    }

    public void setDirectoryMapResourceID(int directoryMapResourceID) {
        this.directoryMapResourceID = directoryMapResourceID;
    }
}
