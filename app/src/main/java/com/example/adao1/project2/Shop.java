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

    public Shop(String name, String costSigns) {
        this.name = name;
        this.costSigns = costSigns;
    }

    public Shop(String name, String description, String costSigns, int shopImageRecourseID, int directoryMapResourceID) {
        this.name = name;
        this.costSigns = costSigns;
        this.description = description;
        //this.tags = tags;
        this.shopImageResourceID = shopImageRecourseID;
        this.directoryMapResourceID = directoryMapResourceID;
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
//
//    public Image getShopImage() {
//        return shopImage;
//    }
//
//    public void setShopImage(Image shopImage) {
//        this.shopImage = shopImage;
//    }
//
//    public Image getDirectoryMap() {
//        return directoryMap;
//    }
//
//    public void setDirectoryMap(Image directoryMap) {
//        this.directoryMap = directoryMap;
//    }
}
