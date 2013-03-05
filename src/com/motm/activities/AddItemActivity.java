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
import java.util.Date;

public class AddItemActivity extends Activity
{
    private ItemManager itemManager;
    private EditText itemNameInput;
    private EditText itemLocationInput;
    private EditText itemRewardInput;
    private EditText itemTypeInput;
    private EditText itemCategoryInput;
    private EditText itemDescriptionInput;
    private TextView addItemStatus;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.item_add);
        
        itemManager = new ItemManager();
        itemNameInput = (EditText)findViewById(R.id.itemNameInput);
        itemLocationInput = (EditText)findViewById(R.id.itemLocationInput);
        itemRewardInput = (EditText)findViewById(R.id.itemRewardInput);
        itemTypeInput = (EditText)findViewById(R.id.itemTypeInput);
        itemCategoryInput = (EditText)findViewById(R.id.itemCategoryInput);
        itemDescriptionInput = (EditText)findViewById(R.id.itemDescriptionInput);
        addItemStatus   = (TextView)findViewById(R.id.registrationStatus);
    }

    private void startViewItemActivity()
    {
        Intent intent = new Intent(this, ViewItemActivity.class);
        startActivity(intent);
        finish();
    }
    
    private void startFindItemActivity()
    {
    	Intent intent = new Intent(this, FindItemActivity.class);
    	startActivity(intent);
        finish();
    }

    private void startMainActivity()
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
            String message = getString(R.string.registrationRequiredFields);
            setAddItemStatus(message);
        } else {
        	String loginName = ((FMSApplication)getApplication()).getCurrentAccount().getLoginName();
        	if (!itemManager.createItem(loginName, new Date(), itemName, itemLocation, itemReward, itemType, itemCategory, itemDescription)) {
	            String message = getString(R.string.submissionUnsuccessful);
	            setAddItemStatus(message);
        	} else {
	            String message = getString(R.string.submissionSuccessful);
	            setAddItemStatus(message);
	            startViewItemActivity();
        	}
        }
    }
    
    public void cancelItemButtonPressed(View view) 
    {
    	startFindItemActivity();
    }
    
    private void setAddItemStatus(String message)
    {
    	addItemStatus.setText(message);
    	addItemStatus.setVisibility(View.VISIBLE);
    }
    
    public void onResume()
    {
        super.onResume();
        clearFields();
        clearAddItemStatus();
    }
    
    private void clearAddItemStatus()
    {
    	addItemStatus.setText("");
    	addItemStatus.setVisibility(View.INVISIBLE);
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

