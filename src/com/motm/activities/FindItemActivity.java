package com.motm.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import com.motm.R;
import com.motm.adapters.ItemViewAdapter;
import com.motm.helpers.Factory;
import com.motm.models.Item;
import com.motm.models.interfaces.ItemManager;
import java.util.ArrayList;

public class FindItemActivity extends ListActivity
{
    public static final String PERFROM_ACTION_ADD_FOUND_ITEM  = "addFoundItem";
    public static final String PERFROM_ACTION_ADD_LOST_ITEM  = "addLostItem";

    private ItemManager itemManager;
    private SearchView itemSearchView;
    private ItemViewAdapter adapter;
    private ArrayList<Item> rowItems;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.item_find);
        
        itemManager = Factory.getItemManager();
        
        // main activity can tell FoundItem to open Add item
        String performAction = getIntent().getStringExtra("performAction");
        if(performAction != null){
            if(performAction.equals(PERFROM_ACTION_ADD_FOUND_ITEM)){
                // open add item, set to found
                startAddItemActivityWithType(Item.Type.Found);
                
            } else if(performAction.equals(PERFROM_ACTION_ADD_LOST_ITEM)){
                // open add item, set to lost
                startAddItemActivityWithType(Item.Type.Lost);
            }
        }
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        
        // update the list
        rowItems = itemManager.getAllItems();
        adapter = new ItemViewAdapter(this, R.layout.item_find_list_rows, rowItems);
        setListAdapter(adapter);
    }

    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Item item = rowItems.get(position);
        
    	startViewItemActivity(item.getItemID());
    }
    
    /**
     * 
     */
    public void startAddItemActivity() 
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    /**
     * 
     */
    public void startAddItemActivityWithType(Item.Type type) 
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("type", type.toString());
        startActivity(intent);
    }
    
    /**
     * @param itemID
     */
    private void startViewItemActivity(Integer itemID)
    {
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra("targetItemId", itemID);
        startActivity(intent);
    }
}
