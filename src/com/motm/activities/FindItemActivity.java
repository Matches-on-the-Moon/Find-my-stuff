package com.motm.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.motm.R;
import com.motm.adapters.ItemViewAdapter;
import com.motm.helpers.Factory;
import com.motm.models.Item;
import com.motm.models.interfaces.ItemManager;
import java.util.ArrayList;

public class FindItemActivity extends ListActivity implements OnItemSelectedListener
{
    public static final String PERFORM_ACTION_ADD_FOUND_ITEM  = "addFoundItem";
    public static final String PERFORM_ACTION_ADD_LOST_ITEM  = "addLostItem";

    private ItemManager itemManager;
    private ItemViewAdapter adapter;
    private ArrayList<Item> rowItems;
    private String itemSortFilter;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.item_find);
        
        itemManager = Factory.getItemManager();
        
        EditText inputSearchEditText = (EditText)findViewById(R.id.inputSearch);
        
        // main activity can tell FoundItem to open Add item
        String performAction = getIntent().getStringExtra("performAction");
        if(performAction != null){
            if(performAction.equals(PERFORM_ACTION_ADD_FOUND_ITEM)){
                // open add item, set to found
                startAddItemActivityWithType(Item.Type.Found);
                
            } else if(performAction.equals(PERFORM_ACTION_ADD_LOST_ITEM)){
                // open add item, set to lost
                startAddItemActivityWithType(Item.Type.Lost);
            }
        }
        
        // spinner adapter
        Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                             R.array.itemSearchOptions,
                                                                             android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        
        // default filter
        this.itemSortFilter = "Name";
        
        // add listener
        inputSearchEditText.addTextChangedListener(new TextWatcher() {
 
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                // When user changed the Text
                String search = itemSortFilter + "|" + cs;
                FindItemActivity.this.adapter.getFilter().filter(search);
                
                EditText inputSearchEditText = (EditText)FindItemActivity.this.findViewById(R.id.inputSearch);
                if(itemSortFilter.equals("Status")){
                    // Open/Resolved
                    inputSearchEditText.setHint("Open/Resolved");
                    
                } else if(itemSortFilter.equals("Date")){
                    // yyyy-mm-dd
                    inputSearchEditText.setHint("yyyy-mm-dd");
                    
                } else {
                    // default: Search items...
                    inputSearchEditText.setHint("!Search items..!");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){}

            @Override
            public void afterTextChanged(Editable arg0){}
        });
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
    
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        String filter = (String)parent.getItemAtPosition(pos);
        this.itemSortFilter = filter;
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        // Another interface callback
    }

    
    /**
     * 
     */
    public void startAddItemActivity() 
    {
        Intent intent = new Intent(this, RegisterItemActivity.class);
        startActivity(intent);
    }

    /**
     * 
     */
    public void startAddItemActivityWithType(Item.Type type) 
    {
        Intent intent = new Intent(this, RegisterItemActivity.class);
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
