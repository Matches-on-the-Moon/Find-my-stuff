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
import com.motm.models.interfaces.AccountManager;

public class ViewAccountActivity extends Activity {
	
	private int targetAccountId;
	private AccountManager accountManager;
	private TextView name;
	private TextView email;
	private TextView loginName;
	
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
        accountManager = Factory.getAccountManager();
        targetAccountId = getIntent().getExtras().getInt("targetAccount");
        setFields();

        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();
        if (currentAccount.getAccountId() == targetAccountId || accountManager.isAdmin(currentAccount.getAccountId())){
        	setButtonDisplay();
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
        finish();
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
    private void setFields() {
    	Account account = accountManager.getAccount(targetAccountId);
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
