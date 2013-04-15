package com.motm.activities;

import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.helpers.Logger;
import com.motm.models.Item;
import com.motm.models.Item.Type;
import com.motm.models.interfaces.ItemManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends Activity {

    private ItemManager itemManager;
    private EditText itemNameInput;
    private EditText itemLocationInput;
    private EditText itemRewardInput;
    private EditText itemCategoryInput;
    private EditText itemDescriptionInput;
    private Integer itemId;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit);
        
        itemManager = Factory.getItemManager();
        itemNameInput = (EditText)findViewById(R.id.itemNameInput);
        itemLocationInput = (EditText)findViewById(R.id.itemLocationInput);
        itemRewardInput = (EditText)findViewById(R.id.itemRewardInput);
        itemCategoryInput = (EditText)findViewById(R.id.itemCategoryInput);
        itemDescriptionInput = (EditText)findViewById(R.id.itemDescriptionInput);
        
        
        itemId = getIntent().getExtras().getInt("targetItemId");
        Logger.d("Here: "+itemId);
        Item item = itemManager.getItem(itemId);
        
        itemNameInput.setText( item.getName() );
        itemLocationInput.setText( item.getLocation() );
        itemRewardInput.setText( item.getReward() );
        itemCategoryInput.setText( item.getCategory() );
        itemDescriptionInput.setText( item.getDescription() );
        
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();
        
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent intent;
        switch (item.getItemId())
        {
        case R.id.viewAccount:
            intent = new Intent(this, ViewAccountActivity.class);
            Integer targetAccountID = FMSApplication.getInstance().getCurrentAccount().getAccountId();
            intent.putExtra("targetAccount", targetAccountID);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "\"Viewing your account.\"", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.logoutButton:
            Toast.makeText(getApplicationContext(), "\"See you next time!\"", Toast.LENGTH_SHORT).show();
            FMSApplication.getInstance().setCurrentAccount(null);
            // start login
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    } 
    
    /**
     * To add picture
     * @param view
     */
    public void itemPictureButtonPressed(View view)
    {
    	//steps to add a picture
    }
    
    /**
     * Submits an item
     * @param view
     */
    public void submitItemEditButtonPressed(View view)
    {
        String itemName = itemNameInput.getText().toString().trim();
    	String itemLocation = itemLocationInput.getText().toString().trim();
    	String itemReward = itemRewardInput.getText().toString().trim();
    	String itemCategory = itemCategoryInput.getText().toString().trim();
    	String itemDescription = itemDescriptionInput.getText().toString().trim();
        
        if(itemName.isEmpty() || itemLocation.isEmpty() || itemCategory.isEmpty() || itemDescription.isEmpty() || itemReward.isEmpty()) {
            String message = getString(R.string.registrationRequiredFields);
            setAddItemStatus(message);
            
        } else {
        	
        	String message;
        	if(itemManager.editItem(itemId, itemName, itemLocation, itemReward, itemCategory, itemDescription)) {
        		message = getString(R.string.submissionSuccessful);
        		setAddItemStatus(message);
        	} else {
        		message = getString(R.string.submissionSuccessful);
        		setAddItemStatus(message);
        	}
        	finish();
        }
   
    }
    
    /**
     * 
     */
    private void startFindItemActivity()
    {
        // go back to find item
    	finish();
    }
    
    /**
     * @param view
     */
    public void cancelItemEditButtonPressed(View view) 
    {
    	startFindItemActivity();
    }
    
    /**
     * Set status message
     * @param message
     */
    private void setAddItemStatus(String message)
    {
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
