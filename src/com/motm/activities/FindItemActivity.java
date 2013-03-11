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

public class FindItemActivity extends ListActivity
{
    private ItemManager itemManager;
    private SearchView itemSearchView;
    private int targetItemId;
    private ItemViewAdapter adapter;
    private ArrayList<Item> rowItems;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.item_find);
        
        itemManager = Factory.getItemManager();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        // update the list
        rowItems = itemManager.getAllItems();
        adapter = new ItemViewAdapter(this, R.layout.item_find_list_rows, rowItems);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Item item = rowItems.get(position);
        
    	startViewItemActivity(item.getItemID());
    }
 
    public void addItemButtonPressed(View view) 
    {
    	startAddItemActivity();
    }
    
    public void startAddItemActivity() 
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
    
    private void startViewItemActivity(Integer itemID)
    {
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra("targetItemId", itemID);
        startActivity(intent);
    }
    
    public void listOnScroll () 
    {
    	//soon to be added
    }

    public void addRow(View view) 
    {
    	// soon to be added
    }
}
