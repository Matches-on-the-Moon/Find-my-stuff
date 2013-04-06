package com.motm.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.helpers.Logger;
import com.motm.models.Item;
import com.motm.models.interfaces.ItemManager;

public class RegisterItemActivity extends Activity
{
    private ItemManager itemManager;
    private EditText itemNameInput;
    private EditText itemLocationInput;
    private EditText itemRewardInput;
    private EditText itemCategoryInput;
    private EditText itemDescriptionInput;
    private Item.Type itemType;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_register);
        
        itemManager = Factory.getItemManager();
        itemNameInput = (EditText)findViewById(R.id.itemNameInput);
        itemLocationInput = (EditText)findViewById(R.id.itemLocationInput);
        itemRewardInput = (EditText)findViewById(R.id.itemRewardInput);
        itemCategoryInput = (EditText)findViewById(R.id.itemCategoryInput);
        itemDescriptionInput = (EditText)findViewById(R.id.itemDescriptionInput);
        
        // get the item type that started this intent
        String typeString = getIntent().getStringExtra("type");
        if(typeString == null){
            // default: lost
            itemType = Item.Type.Lost;
        } else {
            itemType = Item.Type.valueOf(typeString);
        }
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
            startActivity(intent);
            finish();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    } 
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();
        clearFields();
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
    public void submitItemButtonPressed(View view)
    {
        String itemName = itemNameInput.getText().toString().trim();
    	String itemLocation = itemLocationInput.getText().toString().trim();
    	String itemReward = itemRewardInput.getText().toString().trim();
    	String itemCategory = itemCategoryInput.getText().toString().trim();
    	String itemDescription = itemDescriptionInput.getText().toString().trim();
        
        if(itemName.isEmpty() || itemLocation.isEmpty() || itemCategory.isEmpty() || itemDescription.isEmpty()) {
            String message = getString(R.string.registrationRequiredFields);
            setAddItemStatus(message);
            
        } else {
        	Integer accountID = FMSApplication.getInstance().getCurrentAccount().getAccountId();
        	try {
        		Integer id = itemManager.createItem(accountID, itemName, itemLocation, itemReward, itemType, itemCategory, itemDescription);
        		String message = getString(R.string.submissionSuccessful);
	            setAddItemStatus(message);
                // show the item list
	            Logger.d("id: "+id);
	            Item item = itemManager.getItem(id);
	            Logger.d("item: "+item);
	            ArrayList<Item> matches = itemManager.getMatches(item);
	            
	            if( matches!= null ){
	            	Logger.d("NumMatches: "+matches.size());
	            	/*
	            	int numMatches = matches.size();
	            	Context context = getApplicationContext();
	            	CharSequence text = numMatches+((numMatches==1)?" match":" matches");
	            	int duration = Toast.LENGTH_SHORT;

	            	Toast toast = Toast.makeText(context, text, duration);
	            	toast.show();
	            	*/
	            }
                startFindItemActivity();
            }
            catch(Exception e) {
                String message = getString(R.string.submissionUnsuccessful);
                setAddItemStatus(message);
            }
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
    public void cancelItemButtonPressed(View view) 
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
    
    /**
     * Clear fields
     */
    private void clearFields()
    {
        itemNameInput.setText("");
        itemLocationInput.setText("");
        itemRewardInput.setText("");
        itemCategoryInput.setText("");
        itemDescriptionInput.setText("");
    }
}

