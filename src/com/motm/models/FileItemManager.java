package com.motm.models;

import com.motm.models.interfaces.ItemManager;
import java.util.Date;

public class FileItemManager implements ItemManager
{
    public boolean createItem(String loginName, Date date, String itemName, String itemLocation, String itemReward, String itemType, String itemCategory, String itemDescription)
    {
    	return true;
    }
    
    public boolean deleteItem(int ItemID)
    {
        return true;
    }
    
    public boolean editItem(int ItemID)
    {
        return true;
    }
    
    public Item[] findItemsByUserID(int userID)
    {
        Item[] items = {};
        return items;
    }
    
    public Item[] findItemsByLocation(String location)
    {
        Item[] items = {};
        return items;
    }
    
    public Item[] findItemsByStatus(String status)
    {
        Item[] items = {};
        return items;
    }
    
    public Item[] findItemsByCategory(String category)
    {
        Item[] items = {};
        return items;
    }
    
    public Item[] findItemsByType(String type)
    {
        Item[] items = {};
        return items;
    }
    
    public Item[] findItemsByDate(Date date)
    {
        Item[] items = {};
        return items;
    }
}
