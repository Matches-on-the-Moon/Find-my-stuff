package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.motm.application.FMSApplication;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.ItemManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;

public class FileItemManager implements ItemManager
{
    private static final String FILENAME = "itemsHM";
    private static HashMap<Integer, Item> itemsHM;
    
    public FileItemManager()
    {
        if(itemsHM == null){
            itemsHM = new HashMap<Integer, Item>();
        }
        
        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)) {
            preferences.edit().putBoolean("firstRun", false).commit();
            itemsHM.put(0, new Item(0, 0, "Ring", "Atlanta", Item.Status.Open, "$0", Item.Type.Found, "Keepsake", "MY PRECIOUS", new GregorianCalendar(1962, 1, 2)));
            itemsHM.put(1, new Item(1, 1, "name", "location", Item.Status.Open, "$0", Item.Type.Lost, "Category", "Description", new GregorianCalendar(2005, 8, 21)));
            saveData();
            return;
        }
        loadData();
    }
    
    /**
     *
     * @param ownerID owner of item
     * @param name name of item
     * @param location location of item
     * @param reward reward for item
     * @param type type of item
     * @param category category of item
     * @param description description of item
     * @return item id
     * @throws Exception
     */
    public Integer createItem(Integer ownerID, String name, String location, String reward, Item.Type type, String category, String description) throws Exception
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
        
        Item item = new Item(id, ownerID, name, location, Item.Status.Open, reward, type, category, description, new GregorianCalendar());
        itemsHM.put(id, item);
        
        saveData();
        
        return id;
    }
    
    /**
     * Deletes an item
     * @param itemID id of item to delete
     */
    public void deleteItem(Integer itemID)
    {
        itemsHM.remove(itemID);
        
        saveData();
    }
    
    /**
     * Deletes user's items
     * @param userID id of user
     */
    public void deleteUsersItems(Integer userID)
    {
        for(Item item : new ArrayList<Item>(itemsHM.values())) {
            if(item.getOwnerID().equals(userID)) {
                itemsHM.remove(item.getItemID());
            }
        }
        saveData();
    }
    /**
     * Get all items
     * @return list of all items
     */
    public ArrayList<Item> getAllItems()
    {
    	ArrayList<Item> items = new ArrayList<Item>(itemsHM.values());
        
		return items;
	}
	/**
	 * Lookup an item by id
	 * @param itemId id of the the item
	 * @return item with specified id
	 */
	public Item getItem(Integer itemId)
    {
		Item item = itemsHM.get(itemId);
        
		return item;
	}
    
    /**
     * Save FileItemManager information to file
     */
    private void saveData()
    {
        try {
<<<<<<< HEAD
            FileOutputStream fs = FMSApplication.getAppContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
=======
            FileOutputStream fs = FMSApplication.getAppContext().openFileOutput(FILENAME, Context.MODE_APPEND);
>>>>>>> No append we only read 1 object
            ObjectOutputStream s = new ObjectOutputStream(fs);
            s.writeObject(itemsHM);
            s.close();

        }
        catch (Exception e) {
            Logger.d(e.getMessage());
        }
    }
    /**
     * Load FileItemManager information from file
     */
    private void loadData()
    {
        try {
            FileInputStream fs = FMSApplication.getAppContext().openFileInput(FILENAME);
            ObjectInputStream s = new ObjectInputStream(fs);
            HashMap<Integer, Item> items = (HashMap<Integer, Item>)s.readObject();
            s.close();
            itemsHM = items;
        }
        catch (FileNotFoundException e){
            // ignore
        }
        catch (InvalidClassException e){
            // ignore
        }
        catch (Exception e) {
            Logger.d("Could not load item data: "+e.getMessage());
        }
    }
}
