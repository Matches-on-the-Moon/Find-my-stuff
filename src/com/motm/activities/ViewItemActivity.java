package com.motm.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.helpers.Logger;
import com.motm.models.Account;
import com.motm.models.Item;
import com.motm.models.Item.Type;
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
	private ItemManager itemManager = Factory.getItemManager();
	private Item item;
	
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
        
        targetItemId = this.getIntent().getExtras().getInt("targetItemId");
        targetAccountId = itemManager.getItem(targetItemId).getOwnerID();
        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();
    	item = itemManager.getItem(targetItemId);
    	Logger.d("original id: "+targetItemId);
        
        if (currentAccount.getAccountId() == targetAccountId)
        	setButtonDisplay(true, item);
        else 
        	setButtonDisplay(false, item);
        
        setMatchButton(item,itemManager);
        setFields(item);
    }
    
    public void onResume() {
        super.onResume();
        if (accountManager.getAccount(targetAccountId) == null)
        	finish();
        
        Logger.d("Item: "+item);
        Logger.d("itemId: "+targetItemId);

        setMatchButton(itemManager.getItem(targetItemId),itemManager);
        setFields(itemManager.getItem(targetItemId));
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
        intent.putExtra("targetItemId", targetItemId);
        startActivity(intent);
    }
    private void startFoundMatchesActivity(){
    	
    	
    	Intent intent = new Intent(this, FindItemActivity.class);
        intent.putExtra("targetItem", targetItemId);
        intent.putExtra("performAction", FindItemActivity.PERFORM_SHOW_MATCHES);
        startActivity(intent);
    }
    public void foundMatchesButtonPressed(View view){
    
    	startFoundMatchesActivity();
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
        Type t = item.getType();
        type.setText("Type: " + (t==null?"":item.getType().toString()));
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
    private void setMatchButton(Item item, ItemManager itemManager){
    
    	if( item.getType() == Type.Lost && item.getOwnerID() == FMSApplication.getInstance().getCurrentAccount().getAccountId()){
    		
	    	ArrayList<Item> matches = itemManager.getMatches(item);
	        
	        int numMatches = 0;
	        if(matches!=null)
	        	numMatches = matches.size();
	        
	        
	        if(numMatches > 0 ){
	        	Button matchesButton = (Button)findViewById(R.id.foundMatchesButton);
	        	matchesButton.setText(numMatches+((numMatches==1)?" match":" matches"));
	        	matchesButton.setVisibility(View.VISIBLE);
	        	
	        }
    	}
    }
}
