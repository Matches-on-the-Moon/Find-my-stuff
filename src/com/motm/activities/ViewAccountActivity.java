package com.motm.activities;

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
import com.motm.models.Account;
import com.motm.models.interfaces.AccountManager;

public class ViewAccountActivity extends Activity {
	
	private int targetAccountId;
	private TextView name;
	private TextView email;
	private TextView loginName;
	private AccountManager accountManager = Factory.getAccountManager();
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.account_view); 
        
        loginName = (TextView)findViewById(R.id.loginName);
        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        targetAccountId = getIntent().getExtras().getInt("targetAccount");
        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();
        
        if (currentAccount.getAccountId() == targetAccountId || accountManager.isAdmin(currentAccount.getAccountId()))
        	setButtonDisplay();
        
        setFields(accountManager.getAccount(targetAccountId));
    }
    
    public void onResume() {
        super.onResume();
        if (accountManager.getAccount(targetAccountId) == null)
        	finish();
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
    private void startEditAccountActivity()
    {
        Intent intent = new Intent(this, EditAccountActivity.class);
        intent.putExtra("targetAccount", targetAccountId);
        startActivity(intent);
    }
    
    /**
     * @param view
     */
    public void editAccountButtonPressed(View view)
    {
    	startEditAccountActivity();
    }
    
    /**
     * 
     */
    private void setFields(Account account) {
    	loginName.setText(account.getLoginName());
    	name.setText("Name: " + account.getName());
    	email.setText("Email: " + account.getEmail());
    }
    
    /**
     * 
     */
    private void setButtonDisplay()
    {
    	Button editAccountButton = (Button)findViewById(R.id.editAccountButton);
    	editAccountButton.setVisibility(View.VISIBLE);
    }
}
