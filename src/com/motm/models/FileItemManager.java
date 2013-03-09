package com.motm.models;

import com.motm.models.interfaces.ItemManager;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class FileItemManager implements ItemManager
{
    private HashMap<Integer, Item> items;
    
    /**
     *
     * @param ownerID
     * @param name
     * @param location
     * @param reward
     * @param type
     * @param category
     * @param description
     * @param date
     * @return
     * @throws Exception
     */
    public void createItem(Integer ownerID, String name, String location, String reward, String type, String category, String description) throws Exception
    {
        // create a new, unique key
        Set<Integer> itemIDs = items.keySet();
        if(itemIDs.size() >= Integer.MAX_VALUE){
            throw new Exception("Can't create a new item, the max amount of items have been created");
        }
        Integer id = Collections.max(itemIDs) + 1;
        // keep incrementing the id until the set doesn't contain it
        while(itemIDs.contains(id)){
            id++;
        }
        
        Item item = new Item(id, ownerID, name, location, reward, type, category, description, new Date());
        items.put(id, item);
    }
    
    public void deleteItem(Integer itemID)
    {
        
    }
    
    public void editItem(Integer itemID)
    {
        
    }
    
    public Item[] findItemsByUserID(Integer userID)
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
