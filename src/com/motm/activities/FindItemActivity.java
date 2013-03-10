package com.motm.activities;

import java.util.ArrayList;

import Adapters.AccountViewAdapter;
import Adapters.ItemViewAdapter;
import Adapters.RowAccount;
import Adapters.RowItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.Account;
import com.motm.models.Item;
import com.motm.models.interfaces.AccountManager;
import com.motm.models.interfaces.ItemManager;

public class FindItemActivity extends Activity
{
    private ItemManager itemManager;
    private SearchView itemSearchView;
    private ArrayList<RowItem> rowItems;
    private ListView listView;
    private int targetItemId;
    private ItemViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // moved the onCreate stuff here until we can figure out how to stop it from duplicating the list every time or not updating at all.
        setContentView(R.layout.item_find);
        rowItems = new ArrayList<RowItem>();
        itemManager = Factory.getItemManager();
        adapter = new ItemViewAdapter(this, R.layout.account_find_list_rows, rowItems);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        updateRows();
    }

    
    public void updateRows() {
    	// quick and dirty, fix later
        Item[] items = itemManager.getAllItems();
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
