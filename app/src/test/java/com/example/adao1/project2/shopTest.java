package com.example.adao1.project2;

import org.junit.Test;
import static org.junit.Assert.*;

import static junit.framework.Assert.assertEquals;

/**
 * Created by adao1 on 3/25/2016.
 */
public class shopTest {
    private static final String
            targetName = "Target",
            targetDescription = "Pretty cheap, has almost anything you need, oh and its right downstairs",
            targetCost = "$",
            targetIsFav = "true";
    private static final int
            targetImageID = R.drawable.greatmall, //temp picture
            targetMapID = R.drawable.directory;

    @Test
    public void testShopName(){
        Shop shop = new Shop(targetName,
                targetDescription,
                targetCost,
                targetImageID,
                targetMapID,
                targetIsFav);
        String expected = targetName;
        String actual = shop.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void testShopDescription(){
        Shop shop = new Shop(targetName,
                targetDescription,
                targetCost,
                targetImageID,
                targetMapID,
                targetIsFav);
        String expected = targetDescription;
        String actual = shop.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    public void testShopCost(){
        Shop shop = new Shop(targetName,
                targetDescription,
                targetCost,
                targetImageID,
                targetMapID,
                targetIsFav);
        String expected = targetCost;
        String actual = shop.getCostSigns();
        assertEquals(expected, actual);
    }

    @Test
    public void testShopImageID(){
        Shop shop = new Shop(targetName,
                targetDescription,
                targetCost,
                targetImageID,
                targetMapID,
                targetIsFav);
        int expected = targetImageID;
        int actual = shop.getShopImageResourceID();
        assertEquals(expected, actual);
    }

    @Test
    public void testShopMapID(){
        Shop shop = new Shop(targetName,
                targetDescription,
                targetCost,
                targetImageID,
                targetMapID,
                targetIsFav);
        int expected = targetMapID;
        int actual = shop.getDirectoryMapResourceID();
        assertEquals(expected, actual);
    }

    @Test
    public void testShopIsFav(){
        Shop shop = new Shop(targetName,
                targetDescription,
                targetCost,
                targetImageID,
                targetMapID,
                targetIsFav);
        String expected = targetIsFav;
        String actual = shop.getIsFav();
        assertEquals(expected, actual);
    }
}
