package com.motm.activities;

import java.util.ArrayList;

import com.motm.adapters.ItemViewAdapter;
import com.motm.adapters.RowItem;
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
    private ArrayList<RowItem> rowItems;
    private ListView listView;
    private int targetItemId;
    private ItemViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        setContentView(R.layout.item_find);
        
        itemManager = Factory.getItemManager();
        
        rowItems = new ArrayList<RowItem>();
        adapter = new ItemViewAdapter(this, R.layout.item_find_list_rows, rowItems);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        
        updateRows();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        
    }
    
    public void updateRows()
    {
    	// quick and dirty, fix later
        ArrayList<Item> items = itemManager.getAllItems();
        int Id = 0;
    	for(Item item : items) {
    		RowItem rowItem = new RowItem(0, item.getName(), item.getDescription(), item.getType(), Id);
    		rowItems.add(rowItem);
    	}
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
    
    public void itemButtonClicked(View view) 
    {
    	targetItemId = Integer.parseInt((((TextView)(view.findViewById(R.id.itemId))).getText().toString()));
    	startViewItemActivity();
    }
    
    private void startViewItemActivity()
    {
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra("targetItem", targetItemId);
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
