package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.motm.application.FMSApplication;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.ItemManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBItemManager extends APIManager implements ItemManager
{
    public DBItemManager()
    {
        setBaseURL("http://www.gtpkt.org/cs2340api/item/");
        //setBaseURL("http://10.0.2.2/cs2340api/index.php/item/");

        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences("DBItem", Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)){
            preferences.edit().putBoolean("firstRun", false).commit();
            try {
                createItem(1, "Ring", "Atlanta", "$0", Item.Type.FOUND, "Keepsake", "MY PRECIOUS");
                createItem(2, "name", "location", "$0", Item.Type.LOST, "Category", "Description");
            }
            catch (Exception e) {
                Logger.d("Could not create firstRun items, exception: " + e.getMessage());
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
        JSONObject result = put("", createParams(
                "ownerID", ownerID.toString(),
                "name", name,
                "location", location,
                "reward", reward,
                "type", type.toString(),
                "category", category,
                "description", description));

        return result.getInt("result");
    }

    /**
     * Deletes an item
     *
     * @param itemID id of item to delete
     */
    public void deleteItem(Integer itemID)
    {
        JSONObject result = post("delete", createParams(
                "id", itemID.toString()
                ));
    }

    /**
     * Deletes user's items
     *
     * @param userID id of user
     */
    public void deleteUsersItems(Integer userID)
    {
        
    }

    /**
     * Get all items
     *
     * @return list of all items
     */
    public List<Item> getAllItems()
    {
        JSONObject result = get("all", null);
        
        ArrayList<Item> items = new ArrayList<Item>();
        
        Boolean boolResult = false;
        try {
            boolResult = result.getBoolean("success");
            if(!boolResult){
                return items;
            }
            
            // get the result array
            JSONArray itemsJSONArray = result.getJSONArray("result");
            // make items from the results
            for(int i = 0; i < itemsJSONArray.length(); i++){
                // get the jsonObject for an item
                JSONObject jsonObject = itemsJSONArray.getJSONObject(i);
                
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                calendar.setTime(sdf.parse(jsonObject.getString("calendar")));
                
                // make an item from the json data
                Item item = new Item(
                        jsonObject.getInt("id"),
                        jsonObject.getInt("ownerID"),
                        jsonObject.getString("name"),
                        jsonObject.getString("location"),
                        Item.Status.valueOf(jsonObject.getString("status")),
                        jsonObject.getString("reward"),
                        Item.Type.valueOf(jsonObject.getString("type")),
                        jsonObject.getString("category"),
                        jsonObject.getString("description"),
                        calendar);
                
                // add item
                items.add(item);
            }
            
        }
        catch(Exception e){
            // I don't care, continue false
            Logger.d("getAllItems unexpected result from JSON, "+e.getMessage());
        }
        
        return items;
    }

    /**
     * Lookup an item by id
     *
     * @param itemId id of the the item
     * @return item with specified id
     */
    public Item getItem(Integer itemId)
    {
        JSONObject result = get("", createParams(
                "id", itemId.toString()
                ));
        
        JSONObject jsonObject;
        Item item = new Item(0, 0, "", "", Item.Status.OPEN, "", Item.Type.LOST, "", "", Calendar.getInstance());
        
        try {
            jsonObject = result.getJSONObject("result");
            
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            calendar.setTime(sdf.parse(jsonObject.getString("calendar")));
            
            // make an item from the json data
            item = new Item(
                    jsonObject.getInt("id"),
                    jsonObject.getInt("ownerID"),
                    jsonObject.getString("name"),
                    jsonObject.getString("location"),
                    Item.Status.valueOf(jsonObject.getString("status")),
                    jsonObject.getString("reward"),
                    Item.Type.valueOf(jsonObject.getString("type")),
                    jsonObject.getString("category"),
                    jsonObject.getString("description"),
                    calendar);
        }
        catch (Exception e) {
            Logger.d("getItem wrong result from JSON, "+e.getMessage());
        }
        
        return item;
    }

    /**
     *
     * @param itemID
     * @param itemName
     * @param itemLocation
     * @param itemReward
     * @param itemCategory
     * @param itemDescription
     * @return true if edit was successful
     */
    public boolean editItem(Integer itemID, String itemName, String itemLocation, String itemReward, String itemCategory, String itemDescription)
    {
        JSONObject result = post("", createParams(
                "id", itemID.toString(),
                "name", itemName,
                "location", itemLocation,
                "reward", itemReward,
                "category", itemCategory,
                "description", itemDescription));
        
        Boolean boolResult = false;
        
        try {
            boolResult = result.getBoolean("success");
        }
        catch(Exception e){
            // I don't care, continue false
            Logger.d("editItem expected boolean result from JSON, "+e.getMessage());
        }
        
        return boolResult;
    }

    public List<Item> getMatches(Item item)
    {
        JSONObject result = get("matches", createParams(
                "id", item.getItemID().toString(),
                "name", item.getName(),
                "location", item.getLocation()
                ));
        
        ArrayList<Item> items = new ArrayList<Item>();
        
        Boolean boolResult = false;
        JSONArray itemsJSONArray = null;
        
        try {
            boolResult = result.getBoolean("success");
            if(!boolResult){
                return items;
            }
            
            // get the result array
            itemsJSONArray = result.getJSONArray("result");
        }
        catch(Exception e){
            // I don't care, continue false
            Logger.d("getMatches unexpected result from JSON, "+e.getMessage());
            return items;
        }
        
        // make items from the results
        for(int i = 0; i < itemsJSONArray.length(); i++){
            try {
                // get the jsonObject for an item
                JSONObject jsonObject = itemsJSONArray.getJSONObject(i);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                calendar.setTime(sdf.parse(jsonObject.getString("calendar")));

                Integer id = jsonObject.getInt("id");

                // make an item from the json data
                Item _item = new Item(
                        id,
                        jsonObject.getInt("ownerID"),
                        jsonObject.getString("name"),
                        jsonObject.getString("location"),
                        Item.Status.valueOf(jsonObject.getString("status")),
                        jsonObject.getString("reward"),
                        Item.Type.valueOf(jsonObject.getString("type")),
                        jsonObject.getString("category"),
                        jsonObject.getString("description"),
                        calendar);

                // add item
                items.add(_item);
            }
            catch(Exception e){
                // I don't care, continue false
                Logger.d("getMatches unexpected result from JSON, "+e.getMessage());
            }
        }
        
        return items;
    }
}
