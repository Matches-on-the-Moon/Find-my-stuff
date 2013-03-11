package com.motm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.helpers.ItemHelper;
import com.motm.models.Item;
import com.motm.models.interfaces.ItemManager;

public class AddItemActivity extends Activity
{
    private ItemManager itemManager;
    private EditText itemNameInput;
    private EditText itemLocationInput;
    private EditText itemRewardInput;
    private Spinner itemTypeInput;
    private EditText itemCategoryInput;
    private EditText itemDescriptionInput;
    private TextView addItemStatus;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add);
        
        itemManager = Factory.getItemManager();
        itemNameInput = (EditText)findViewById(R.id.itemNameInput);
        itemLocationInput = (EditText)findViewById(R.id.itemLocationInput);
        itemRewardInput = (EditText)findViewById(R.id.itemRewardInput);
        itemTypeInput = (Spinner)findViewById(R.id.itemTypeInput);
        itemCategoryInput = (EditText)findViewById(R.id.itemCategoryInput);
        itemDescriptionInput = (EditText)findViewById(R.id.itemDescriptionInput);
        addItemStatus   = (TextView)findViewById(R.id.registrationStatus);
        
        // set the spinner options
        itemTypeInput.setAdapter(ItemHelper.getItemTypeAdapter(this));
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();
        clearFields();
        clearAddItemStatus();
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
    	Item.Type itemType = Item.Type.valueOf(itemTypeInput.getSelectedItem().toString());
    	String itemCategory = itemCategoryInput.getText().toString().trim();
    	String itemDescription = itemDescriptionInput.getText().toString().trim();
        
        if(itemName.isEmpty() || itemLocation.isEmpty() || itemCategory.isEmpty() || itemDescription.isEmpty()) {
            String message = getString(R.string.registrationRequiredFields);
            setAddItemStatus(message);
            
        } else {
        	Integer accountID = FMSApplication.getInstance().getCurrentAccount().getAccountId();
        	try {
        		Integer targetItemId = itemManager.createItem(accountID, itemName, itemLocation, itemReward, itemType, itemCategory, itemDescription);
        		String message = getString(R.string.submissionSuccessful);
	            setAddItemStatus(message);
                // show the item list
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
    	addItemStatus.setText(message);
    	addItemStatus.setVisibility(View.VISIBLE);
    }
    
    /**
     * Clear status
     */
    private void clearAddItemStatus()
    {
    	addItemStatus.setText("");
    	addItemStatus.setVisibility(View.INVISIBLE);
    }
    
    /**
     * Clear fields
     */
    private void clearFields()
    {
        itemNameInput.setText("");
        itemLocationInput.setText("");
        itemRewardInput.setText("");
        itemTypeInput.setSelection(0);
        itemCategoryInput.setText("");
        itemDescriptionInput.setText("");
    }
}

