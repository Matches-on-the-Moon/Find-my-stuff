package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.Account;
import com.motm.models.Item;
import com.motm.models.interfaces.ItemManager;

public class ViewItemActivity extends Activity {
	
	private Integer targetItemId;
	private ItemManager itemManager;
	private TextView name;
	private TextView description;
	private TextView type;
	//private TextView status;
	private TextView location;
	private TextView reward;
	private TextView category;
	private TextView date;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.item_view); 
        
        itemManager = Factory.getItemManager();
        
        name = (TextView)findViewById(R.id.name);
        type = (TextView)findViewById(R.id.type);
        description = (TextView)findViewById(R.id.description);
        //status = (TextView)findViewById(R.id.status);
        location = (TextView)findViewById(R.id.location);
        reward = (TextView)findViewById(R.id.reward);
        category = (TextView)findViewById(R.id.category);
        date = (TextView)findViewById(R.id.date);
        
        targetItemId = this.getIntent().getExtras().getInt("targetItemId");
    	Item item = itemManager.getItem(targetItemId);
        setFields(item);
        
        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();
        int targetOwnerId = -1;
        if (itemManager.getItem(targetItemId) != null) {
        	targetOwnerId = itemManager.getItem(targetItemId).getOwnerID();
        }
        if (currentAccount.getAccountId() == targetOwnerId)
        	setButtonDisplay(true, item);
        else 
        	setButtonDisplay(false, item);
    }
    
    /**
     * 
     */
    private void startViewAccountActivity()
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        intent.putExtra("targetAccount", itemManager.getItem(targetItemId).getOwnerID());
        startActivity(intent);
    }
    
    /**
     * 
     */
    private void startEditItemActivity()
    {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("targetItem", targetItemId);
        startActivity(intent);
        finish();
    }
    
    /**
     * @param view
     */
    public void editItemButtonPressed(View view)
    {
    	startEditItemActivity();
    }
    
    /**
     * @param view
     */
    public void lostFoundButtonPressed(View view)
    {
        startViewAccountActivity();
    }
    
    /**
     * 
     */
    private void setFields(Item item) 
    {
        
    	if (item == null) {
            return;
        }
        
        description.setText("Description: " + item.getDescription());
        name.setText("Name: " + item.getName());
        type.setText("Type: " + item.getType().toString());
        location.setText("Location: " + item.getLocation());
        reward.setText("Reward: " + item.getReward());
        category.setText("Category: " + item.getCategory());
        date.setText("Date Entered: " + item.getDate());
    }
    
    /**
     * @param set
     */
    private void setButtonDisplay(boolean isOwner, Item item)
    {
        if(isOwner == true) {
        	Button editItemButton = (Button)findViewById(R.id.editItemButton);
        	editItemButton.setVisibility(View.VISIBLE);
        } else {
        	Button lostFoundButton = (Button)findViewById(R.id.lostFoundButton);
        	if(item.getType() == Item.Type.Found)
        		lostFoundButton.setText("I lost this item.");
        	else
        		lostFoundButton.setText("I found this item.");
    		lostFoundButton.setVisibility(View.VISIBLE);
        }

    }
}
