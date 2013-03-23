package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.models.Account;

public class MainActivity extends Activity
{
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();

        if(currentAccount == null) {
            startLoginActivity();
        }
        
        setContentView(R.layout.main);
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
    
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.viewAccount:
            Intent intent = new Intent(this, ViewAccountActivity.class);
            Integer targetAccountID = FMSApplication.getInstance().getCurrentAccount().getAccountId();
            intent.putExtra("targetAccount", targetAccountID);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "\"Viewing your account.\"", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.logoutButton:
            Toast.makeText(getApplicationContext(), "\"See you next time!\"", Toast.LENGTH_SHORT).show();
            FMSApplication.getInstance().setCurrentAccount(null);
            startLoginActivity();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    

    /**
     * 
     */
    private void startLoginActivity()
    {
        // start login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        
        // finish main, to prevent back
        finish();
    }

    /**
     * 
     */
    private void startFindItemActivity()
    {
        Intent intent = new Intent(this, FindItemActivity.class);
        startActivity(intent);
    }
    
    /**
     * 
     */
    private void startFindAccountActivity()
    {
        Intent intent = new Intent(this, FindAccountActivity.class);
        startActivity(intent);
    }
    
    private void startRegisterItemActivity(String type)
    {
        Intent intent = new Intent(this, RegisterItemActivity.class);
        intent.putExtra("itemType", type);
        startActivity(intent);
    }
    
    /*
     *  Actions
     */
    
    /**
     * @param view
     */
    public void browseAccountsButtonPressed(View view)
    {
        startFindAccountActivity();
    }
    
    /**
     * @param view
     */
    public void browseItemsButtonPressed(View view)
    {
        startFindItemActivity();
    }
    
    /**
     * @param view
     */
    public void itemFoundButtonPressed(View view)
    {
        // start find item and start add item, set to found items
        Intent intent = new Intent(this, FindItemActivity.class);
        intent.putExtra("performAction", FindItemActivity.PERFROM_ACTION_ADD_FOUND_ITEM);
        startActivity(intent);
    }
    
    /**
     * @param view
     */
    public void itemLostButtonPressed(View view)
    {
        // start find item and start add item, set to found items
        Intent intent = new Intent(this, FindItemActivity.class);
        intent.putExtra("performAction", FindItemActivity.PERFROM_ACTION_ADD_LOST_ITEM);
        startActivity(intent);
    }
}
