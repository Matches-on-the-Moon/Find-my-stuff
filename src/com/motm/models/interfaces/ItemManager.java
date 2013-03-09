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
    public void createItem(Integer ownerID, String name, String location, String reward, String type, String category, String description)
            throws Exception;
    
    public void deleteItem(Integer itemID);

    public void editItem(Integer itemID);

    public Item[] findItemsByUserID(Integer userID);

    public Item[] findItemsByLocation(String location);

    public Item[] findItemsByStatus(String status);

    public Item[] findItemsByCategory(String category);

    public Item[] findItemsByType(String type);

    public Item[] findItemsByDate(Date date);

}
