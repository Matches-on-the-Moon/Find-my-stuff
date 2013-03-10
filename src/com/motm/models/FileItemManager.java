package com.motm.models;

import android.content.Context;
import com.motm.application.FMSApplication;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.ItemManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class FileItemManager implements ItemManager
{
    private static final String FILENAME = "itemsHM";
    
    private HashMap<Integer, Item> itemsHM;
    
    public FileItemManager()
    {
        itemsHM = new HashMap<Integer, Item>();
        
        loadData();
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
        Set<Integer> itemIDs = itemsHM.keySet();
        if(itemIDs.size() >= Integer.MAX_VALUE){
            throw new Exception("Can't create a new item, the max amount of items have been created");
        }
        Integer id = 1;
        if(itemIDs.size() > 0){
            id = Collections.max(itemIDs) + 1;
        }
        // keep incrementing the id until the set doesn't contain it
        while(itemIDs.contains(id)){
            id++;
        }
        
        Item item = new Item(id, ownerID, name, location, Item.Status.Open, reward, type, category, description, new Date());
        itemsHM.put(id, item);
        
        saveData();
        
        return id;
    }
    
    public void deleteItem(Integer itemID)
    {
        itemsHM.remove(itemID);
        
        saveData();
    }
    
    public ArrayList<Item> findItemsByUserID(Integer userID)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemsHM.values()){
            if(item.getOwnerID().equals(userID)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByLocation(String location)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemsHM.values()){
            if(item.getLocation().equals(location)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByStatus(Item.Status status)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemsHM.values()){
            if(item.getStatus().equals(status)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByCategory(String category)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        
        for(Item item : itemsHM.values()){
            if(item.getCategory().equals(category)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByType(String type)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        for(Item item : itemsHM.values()){
            if(item.getType().equals(type)){
                items.add(item);
            }
        }
        
        return items;
    }
    
    public ArrayList<Item> findItemsByDate(Date date)
    {
        ArrayList<Item> items = new ArrayList<Item>();
        for(Item item : itemsHM.values()){
            if(item.getDate().equals(date)){
                items.add(item);
            }
        }
        return items;
    }
    
    public ArrayList<Item> getAllItems()
    {
    	ArrayList<Item> items = new ArrayList<Item>(itemsHM.values());
        
		return items;
	}

	public Item getItem(Integer itemId)
    {
		Item item = itemsHM.get(itemId);
        
		return item;
	}
    
    /*
     *  File Operations
     */
    private void saveData()
    {
        try {
            FileOutputStream fs = FMSApplication.getAppContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream s = new ObjectOutputStream(fs);
            s.writeObject(itemsHM);
            s.close();
        }
        catch (Exception e) {
            Logger.d(e.getMessage());
        }
    }
    
    private void loadData()
    {
        try {
            FileInputStream fs = FMSApplication.getAppContext().openFileInput(FILENAME);
            ObjectInputStream s = new ObjectInputStream(fs);
            HashMap<Integer, Item> items = (HashMap<Integer, Item>)s.readObject();
            s.close();
            
            itemsHM = items;
        }
        catch (Exception e) {
            Logger.d(e.getMessage());
        }
    }
}
