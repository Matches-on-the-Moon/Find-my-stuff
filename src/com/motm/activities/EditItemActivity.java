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
    private Item.Type itemType;
    private Item item;
    
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
        
        
        Integer itemId = getIntent().getExtras().getInt("targetItemId");
        Logger.d("Here: "+itemId);
        item = itemManager.getItem(itemId);
        
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
    public void editSaveButtonPressed(View view)
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
        	
        	try {
        		
        		item.setName( itemName );
        		item.setLocation( itemLocation );
        		item.setReward( itemReward );
        		item.setCategory( itemCategory );
        		item.setDescription( itemDescription );
        		Type type = item.getType();
        		int ownerID = item.getOwnerID();
        		
        		itemManager.deleteItem( item.getItemID() );        		
        		int id = itemManager.createItem(ownerID, itemName, itemLocation, itemReward, type, itemCategory, itemDescription);
        		Logger.d("new id: "+id);
        		Intent intent = Intent.getIntentOld(ViewItemActivity.class.toString());
        		intent.putExtra("targetItemId", id);
        		
        		
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
    public void editCancelButtonPressed(View view) 
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
