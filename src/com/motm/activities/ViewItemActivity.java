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
import com.motm.models.interfaces.AccountManager;
import com.motm.models.interfaces.ItemManager;

public class ViewItemActivity extends Activity {
	
	private Integer targetItemId;
	private Integer targetAccountId;
	private TextView name;
	private TextView description;
	private TextView type;
	private TextView location;
	private TextView reward;
	private TextView category;
	private TextView date;
	private AccountManager accountManager = Factory.getAccountManager();
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view); 
        
        name = (TextView)findViewById(R.id.name);
        type = (TextView)findViewById(R.id.type);
        description = (TextView)findViewById(R.id.description);
        location = (TextView)findViewById(R.id.location);
        reward = (TextView)findViewById(R.id.reward);
        category = (TextView)findViewById(R.id.category);
        date = (TextView)findViewById(R.id.date);
        
        ItemManager itemManager = Factory.getItemManager();
        targetItemId = this.getIntent().getExtras().getInt("targetItemId");
        targetAccountId = itemManager.getItem(targetItemId).getOwnerID();
        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();
    	Item item = itemManager.getItem(targetItemId);
        
        if (currentAccount.getAccountId() == targetAccountId)
        	setButtonDisplay(true, item);
        else 
        	setButtonDisplay(false, item);
        
        setFields(item);
    }
    
    public void onResume() {
        super.onResume();
        if (accountManager.getAccount(targetAccountId) == null)
        	finish();
    }
    
    /**
     * 
     */
    private void startViewAccountActivity()
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        intent.putExtra("targetAccount", targetAccountId);
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
        date.setText("Date Entered: " + item.getFormattedCalendar());
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
