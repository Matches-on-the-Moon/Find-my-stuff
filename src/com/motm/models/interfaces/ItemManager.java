/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models.interfaces;

import com.motm.models.Item;
import java.util.Date;

/**
 *
 * @author michael
 */
public interface ItemManager
{
    public boolean createItem(String loginName, Date date, String itemName, String itemLocation, String itemReward, String itemType, String itemCategory, String itemDescription);

    public boolean deleteItem(int ItemID);

    public boolean editItem(int ItemID);

    public Item[] findItemsByUserID(int userID);

    public Item[] findItemsByLocation(String location);

    public Item[] findItemsByStatus(String status);

    public Item[] findItemsByCategory(String category);

    public Item[] findItemsByType(String type);

    public Item[] findItemsByDate(Date date);

}
