/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.helpers;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.motm.models.Item;

/**
 *
 * @author michael
 */
public class ItemHelper
{
    /**
     *
     * @param context
     * @return
     */
    public static ArrayAdapter<Item.Type> getItemTypeAdapter(Context context)
    {
        ArrayAdapter<Item.Type> itemTypeAdapter = new ArrayAdapter<Item.Type>(
                context,
                android.R.layout.simple_spinner_item,
                Item.Type.values());
        
        return itemTypeAdapter;
    }
}
