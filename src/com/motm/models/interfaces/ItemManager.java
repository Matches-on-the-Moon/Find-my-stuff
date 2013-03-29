package com.motm.models.interfaces;

import com.motm.models.Item;
import java.util.ArrayList;

public interface ItemManager
{
    public Integer createItem(Integer ownerID, String name, String location, String reward, Item.Type type, String category, String description)
            throws Exception;
    
    public void deleteItem(Integer itemID);

    public void deleteUsersItems(Integer userID);

	public ArrayList<Item> getAllItems();

	public Item getItem(Integer itemId);

}
