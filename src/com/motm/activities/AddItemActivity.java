package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.models.Item;
import com.motm.models.ItemManager;

public class AddItemActivity extends Activity
{
    // models
    private ItemManager itemManager;
    
    // view variables
    private EditText itemNameInput;
    private EditText itemLocationInput;
    private EditText itemRewardInput;
    private EditText itemTypeInput;
    private EditText itemCategoryInput;
    private EditText itemDescriptionInput;
    private TextView registrationStatus;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        itemManager = new ItemManager();

        setContentView(R.layout.item_add);
        
        itemNameInput = (EditText)findViewById(R.id.itemNameInput);
        itemLocationInput = (EditText)findViewById(R.id.itemLocationInput);
        itemRewardInput = (EditText)findViewById(R.id.itemRewardInput);
        itemTypeInput = (EditText)findViewById(R.id.itemTypeInput);
        itemCategoryInput = (EditText)findViewById(R.id.itemCategoryInput);
        itemDescriptionInput = (EditText)findViewById(R.id.itemDescriptionInput);
        registrationStatus   = (TextView)findViewById(R.id.registrationStatus);
    }

    private void startViewItemActivity()
    {
        Intent intent = new Intent(this, ViewItemActivity.class);
        startActivity(intent);
    }
    
    private void startFindItemActivity()
    {
    	Intent intent = new Intent(this, FindItemActivity.class);
    	startActivity(intent);
    }

    private void returnToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void itemPictureButtonPressed(View view) {
    	//steps to add a picture
    }
    
    
    public void submitItemButtonPressed(View view)
    {
    	
        String itemName = itemNameInput.getText().toString().trim();
    	String itemLocation = itemLocationInput.getText().toString().trim();
    	String itemReward = itemRewardInput.getText().toString().trim();
    	String itemType = itemTypeInput.getText().toString().trim();
    	String itemCategory = itemCategoryInput.getText().toString().trim();
    	String itemDescription = itemDescriptionInput.getText().toString().trim();
        
        if(itemName.isEmpty() || itemLocation.isEmpty() || itemType.isEmpty() || itemCategory.isEmpty() || itemDescription.isEmpty()) {
            // not all fields
            String message = getString(R.string.registrationRequiredFields);
            setStatus(message);
            
        } else {
            // success
        	if (!itemManager.createItem(itemName, itemLocation, itemReward, itemType, itemCategory, itemDescription)) {
	            String message = getString(R.string.registrationUnsuccessful);
	            setStatus(message);
        	} else {
	            // display the message
	            String message = getString(R.string.submissionSuccessful);
	            setStatus(message);
	            startViewItemActivity();
        	}
        	startFindItemActivity();
        }
    }
    
    public void cancelItemButtonPressed(View view) 
    {
    	startFindItemActivity();
    }
    
    private void setStatus(String message)
    {
        // set text
    	registrationStatus.setText(message);
        
        // make visible
    	registrationStatus.setVisibility(View.VISIBLE);
    }
    
    public void onResume()
    {
        super.onResume();
        
        // clear fields
        clearFields();
        clearStatus();
    }
    
    private void clearStatus()
    {
        // set text
    	registrationStatus.setText("");
        
        // hide
    	registrationStatus.setVisibility(View.INVISIBLE);
    }
    
    private void clearFields()
    {
        itemNameInput.setText("");
        itemLocationInput.setText("");
        itemRewardInput.setText("");
        itemTypeInput.setText("");
        itemCategoryInput.setText("");
        itemDescriptionInput.setText("");
    }
}

