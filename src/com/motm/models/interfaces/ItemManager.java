package com.motm.models.interfaces;

import com.motm.models.Item;
import java.util.List;

public interface ItemManager
{
     Integer createItem(Integer ownerID, String name, String location, String reward, Item.Type type, String category, String description)
            throws Exception;
    
     void deleteItem(Integer itemID);

     void deleteUsersItems(Integer userID);

	 List<Item> getAllItems();

	 Item getItem(Integer itemId);

	 List<Item> getMatches(Item item);

	 boolean editItem(Integer itemId, String itemName, String itemLocation,
			String itemReward, String itemCategory, String itemDescription);

}
