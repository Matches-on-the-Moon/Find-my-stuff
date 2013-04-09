package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import com.motm.application.FMSApplication;
import com.motm.helpers.Logger;
import com.motm.models.Item.Type;
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
import java.util.Locale;
import java.util.Set;

public class FileItemManager implements ItemManager
{
    private static final String FILENAME = "itemsSA";
    private static SparseArray<Item> itemsSA;
    
    public FileItemManager()
    {
        if(itemsSA == null){
            itemsSA = new SparseArray<Item>();
        }
        
        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)) {
            preferences.edit().putBoolean("firstRun", false).commit();
            itemsSA.put(0, new Item(0, 0, "Ring", "Atlanta", Item.Status.Open, "$0", Item.Type.Found, "Keepsake", "MY PRECIOUS", new GregorianCalendar(1962, 1, 2)));
            itemsSA.put(1, new Item(1, 1, "name", "location", Item.Status.Open, "$0", Item.Type.Lost, "Category", "Description", new GregorianCalendar(2005, 8, 21)));
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
        if(itemsSA.size() >= Integer.MAX_VALUE){
            throw new Exception("Can't create a new item, the max amount of items have been created");
        }
        Integer id = 1;
        // keep incrementing the id until the set doesn't contain it
        while(itemsSA.get(id) != null){
            id++;
        }
        
        Item item = new Item(id, ownerID, name, location, Item.Status.Open, reward, type, category, description, new GregorianCalendar());
        itemsSA.put(id, item);
        
        saveData();
        
        return id;
    }
    
    /**
     * Deletes an item
     * @param itemID id of item to delete
     */
    public void deleteItem(Integer itemID)
    {
        itemsSA.remove(itemID);
        
        saveData();
    }
    
    /**
     * Deletes user's items
     * @param userID id of user
     */
    public void deleteUsersItems(Integer userID)
    {
        int key = 0;
        for(int i = 0; i < itemsSA.size(); i++) {
           key = itemsSA.keyAt(i);
           // get the object by the key.
           Item item = itemsSA.get(key);
           if(item.getOwnerID().equals(userID)) {
                itemsSA.remove(item.getItemID());
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
    	ArrayList<Item> items = new ArrayList<Item>();
        
        int key = 0;        
        for(int i = 0; i < itemsSA.size(); i++) {
            key = itemsSA.keyAt(i);
            // get the object by the key.
            Item item = itemsSA.get(key);
            items.add(item);
        }
        
		return items;
	}
	/**
	 * Lookup an item by id
	 * @param itemId id of the the item
	 * @return item with specified id
	 */
	public Item getItem(Integer itemId)
    {
		Item item = itemsSA.get(itemId);
        
		return item;
	}
    
	public ArrayList<Item> getMatches(Item item){
		
		ArrayList<Item> items = new ArrayList<Item>();
        int key = 0;
        for(int i = 0; i < itemsSA.size(); i++) {
           key = itemsSA.keyAt(i);
           items.add(itemsSA.get(key));
        }
        
		ArrayList<Item> matches = new ArrayList<Item>();
		
		for(Item currItem:items){
			
			String currName = currItem.getName().toLowerCase(Locale.ENGLISH);
			String itemName = item.getName().toLowerCase(Locale.ENGLISH);
			String currLocation = currItem.getLocation().toLowerCase(Locale.ENGLISH);
			String itemLocation = item.getLocation().toLowerCase(Locale.ENGLISH);
			
			if( (currName.contains(itemName) ||
					itemName.contains(currName))
					&& (currLocation.contains(itemLocation)|| itemLocation.contains(currLocation))
					&& currItem.getType()==Type.Found){
				
				matches.add(currItem);
				
			}
		}
		
		if( matches.size() == 0 )
			return null;
		else
			return matches;
	}
    /**
     * Save FileItemManager information to file
     */
    private void saveData()
    {
        try {
            FileOutputStream fs = FMSApplication.getAppContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream s = new ObjectOutputStream(fs);
            s.writeObject(itemsSA);
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
            SparseArray<Item> items = (SparseArray<Item>)s.readObject();
            s.close();
            itemsSA = items;
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
