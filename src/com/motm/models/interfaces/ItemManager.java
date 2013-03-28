package com.motm.models.interfaces;

import com.motm.models.Item;
import java.util.ArrayList;
import java.util.Calendar;

public interface ItemManager
{
    public Integer createItem(Integer ownerID, String name, String location, String reward, Item.Type type, String category, String description)
            throws Exception;
    
    public void deleteItem(Integer itemID);

    public ArrayList<Item> findItemsByUserID(Integer userID);

    public ArrayList<Item> findItemsByLocation(String location);

    public ArrayList<Item> findItemsByStatus(Item.Status status);

    public ArrayList<Item> findItemsByCategory(String category);

    public ArrayList<Item> findItemsByType(Item.Type type);

    public ArrayList<Item> findItemsByCalendar(Calendar calendar);

	public ArrayList<Item> getAllItems();

	public Item getItem(Integer itemId);

}
