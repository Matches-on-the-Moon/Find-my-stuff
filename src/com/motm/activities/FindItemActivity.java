package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import com.motm.R;
import com.motm.models.ItemManager;

public class FindItemActivity extends Activity
{
    private ItemManager itemManager;
    private SearchView itemSearchView;
    private TextView name;
    private TextView description;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.item_find);
        
        itemManager = new ItemManager();
        itemSearchView = (SearchView)findViewById(R.id.itemSearchView);
        name = (TextView)findViewById(R.id.nameOneTextView);
        description = (TextView)findViewById(R.id.descriptionOneTextView);
    }

    private void startAddItemActivity()
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
    
    private void startViewItemActivity()
    {
    	Intent intent = new Intent(this, ViewItemActivity.class);
    	startActivity(intent);
    }

    private void startMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addItemButtonPressed(View view)
    {
        startAddItemActivity();
    }
    
    public void itemOneButtonPressed(View view) 
    {
    	startViewItemActivity();
    }
    
    public void itemTwoButtonPressed(View view) 
    {
    	startViewItemActivity();
    }
    
    public void itemThreeButtonPressed(View view) 
    {
    	startViewItemActivity();
    }
}
