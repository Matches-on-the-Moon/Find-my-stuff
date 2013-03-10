package com.motm.models;

import com.motm.models.interfaces.ItemManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class FileItemManager implements ItemManager
{
    private HashMap<Integer, Item> itemHM;
    
    public FileItemManager()
    {
        if(itemHM == null) {
            itemHM = new HashMap<Integer, Item>();
        }
    }
    
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
    public Integer createItem(Integer ownerID, String name, String location, String reward, String type, String category, String description) throws Exception
    {
        // create a new, unique key
        Set<Integer> itemIDs = itemHM.keySet();
        if(itemIDs.size() >= Integer.MAX_VALUE){
            throw new Exception("Can't create a new item, the max amount of items have been created");
        }
        Integer id = Collections.max(itemIDs) + 1;
        // keep incrementing the id until the set doesn't contain it
        while(itemIDs.contains(id)){
            id++;
        }
        
        Item item = new Item(id, ownerID, name, location, Item.Status.Open, reward, type, category, description, new Date());
        itemHM.put(id, item);
        return id;
    }
    
    public void deleteItem(Integer itemID)
    {
        itemHM.remove(itemID);
    }
    
    public ArrayList<Item> findItemsByUserID(Integer userID)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemHM.values()){
            if(item.getOwnerID().equals(userID)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByLocation(String location)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemHM.values()){
            if(item.getLocation().equals(location)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByStatus(Item.Status status)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemHM.values()){
            if(item.getStatus().equals(status)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByCategory(String category)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemHM.values()){
            if(item.getCategory().equals(category)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByType(String type)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        for(Item item : itemHM.values()){
            if(item.getType().equals(type)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByDate(Date date)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        for(Item item : itemHM.values()){
            if(item.getDate().equals(date)){
                items.add(item);
            }
        }
        return items;
    }

	public Item[] getAllItems() {
    	Item[] items = new Item[itemHM.size()];
    	int Id = 0;
    	for(Item item : itemHM.values()) {
    		items[Id] = item;
    		Id++;
    	}
		return items;
	}

	public Item getItem(Integer itemId) {
		Item item = itemHM.get(itemId);
		return item;
	}
}
