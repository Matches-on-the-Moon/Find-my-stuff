package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.motm.application.FMSApplication;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.ItemManager;
import java.util.List;

public class DBItemManager implements ItemManager
{
    public DBItemManager()
    {
        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences("DBItem", Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)) {
            preferences.edit().putBoolean("firstRun", false).commit();
            try {
                createItem(1, "Ring", "Atlanta", "$0", Item.Type.FOUND, "Keepsake", "MY PRECIOUS");
                createItem(2, "name", "location", "$0", Item.Type.LOST, "Category", "Description");
            }
            catch(Exception e){
                Logger.d("Could not create firstRun items, exception: "+e.getMessage());
            }
        }
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
        return null;
    }
    
    /**
     * Deletes an item
     * @param itemID id of item to delete
     */
    public void deleteItem(Integer itemID)
    {
        
    }
    
    /**
     * Deletes user's items
     * @param userID id of user
     */
    public void deleteUsersItems(Integer userID)
    {
        
    }
    /**
     * Get all items
     * @return list of all items
     */
    public List<Item> getAllItems()
    {
        return null;
	}
	/**
	 * Lookup an item by id
	 * @param itemId id of the the item
	 * @return item with specified id
	 */
	public Item getItem(Integer itemId)
    {
        return null;
	}
	
	/**
	*
	* @param itemID
	* @param itemName
	* @param itemLocation
	* @param itemReward
	* @param itemCategory
	* @param itemDescription
	* @return true if edit was sucessful
	*/
    public boolean editItem(Integer itemID, String itemName, String itemLocation, String itemReward, String itemCategory, String itemDescription)
    {
        return false;
    }
    
	public List<Item> getMatches(Item item)
	{
        return null;
	}
}
