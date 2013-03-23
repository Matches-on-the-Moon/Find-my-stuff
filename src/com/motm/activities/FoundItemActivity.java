package com.motm.activities;

import java.util.ArrayList;

import com.motm.adapters.ItemViewAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.motm.R;
import com.motm.helpers.Factory;
import com.motm.models.Item;
import com.motm.models.interfaces.ItemManager;

public class FoundItemActivity extends ListActivity
{
    private ItemManager itemManager;
    private SearchView itemSearchView;
    private int targetItemId;
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
     * @param view
     */
    public void addItemButtonPressed(View view) 
    {
    	startAddItemActivity();
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
     * @param itemID
     */
    private void startViewItemActivity(Integer itemID)
    {
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra("targetItemId", itemID);
        startActivity(intent);
    }
    
    /**
     * 
     */
    public void listOnScroll () 
    {
    	//soon to be added
    }

    /**
     * @param view
     */
    public void addRow(View view) 
    {
    	// soon to be added
    }
}
