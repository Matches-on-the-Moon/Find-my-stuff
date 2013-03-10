package com.motm.activities;

import com.motm.models.Account;
import com.motm.models.interfaces.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;


public class EditAccountActivity extends Activity {

	private int targetAccountID;
    private AccountManager accountManager;
	private EditText passwordInput;
	private EditText emailInput;
	private TextView editStatus;
	private TextView loginName;
	private String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_edit);
		
        passwordInput        = (EditText)findViewById(R.id.editPasswordInput);
        emailInput           = (EditText)findViewById(R.id.editEmailInput);
        editStatus			 = (TextView)findViewById(R.id.editStatus);
        loginName			 = (TextView)findViewById(R.id.loginName);
        
		// find and set the targeted account
        accountManager = Factory.getAccountManager();
		targetAccountID = getIntent().getExtras().getInt("targetAccount");
		loginName.setText(accountManager.getAccount(targetAccountID).getLoginName());
		Account currentAccount = FMSApplication.getInstance().getCurrentAccount();
        if (accountManager.isAdmin(currentAccount.getAccountId()))
        	setButtonDisplay();
	}
	
    private void startViewAccountActivity()
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        intent.putExtra("targetAccount", targetAccountID);
        startActivity(intent);
    }
    
    public void submitButtonPressed(View view)
    {
    	String password = passwordInput.getText().toString().trim();
    	String email = emailInput.getText().toString().trim();
    	if(!password.isEmpty()){
    		accountManager.editAccountPassword(targetAccountID, password);
        }
    	if (!email.isEmpty()){
    		accountManager.editAccountEmail(targetAccountID, email);
        }
    	startViewAccountActivity();
    	finish();
    }
    
    public void promoteButtonPressed(View view)
    {
    	// finds the account, promotes it to Admin
    	message = "Account promoted.";
    	editStatus.setText(message);
    	editStatus.setVisibility(View.VISIBLE);
    }
    
    public void lockButtonPressed(View view) 
    {

    	if(accountManager.lockAccount(targetAccountID)) {
	        message = "Account locked.";
	    	editStatus.setText(message);
	    	editStatus.setVisibility(View.VISIBLE);
    	}
    }
    
    public void unlockButtonPressed(View view)
    {
    	if(accountManager.unlockAccount(targetAccountID)) {
        	message = "Account unlocked.";
        	editStatus.setText(message);
        	editStatus.setVisibility(View.VISIBLE);
    	}
    }
    
    public void deleteButtonPressed(View view)
    {
    	if(accountManager.deleteAccount(targetAccountID)) {
    		message = "Account deleted.";
        	editStatus.setText(message);
        	editStatus.setVisibility(View.VISIBLE);
    	}

    }
    
    public void editPasswordButtonPressed(View view) 
    {
    	passwordInput.setVisibility(View.VISIBLE);
    }
    
    public void editEmailButtonPressed(View view) 
    {
    	emailInput.setVisibility(View.VISIBLE);
    }
    
    public void editItemPictureButtonPressed(View view) {
    	// do stuff
    }
    
    private void setButtonDisplay()
    {
    	Button deleteButton = (Button)findViewById(R.id.deleteButton);
    	Button promoteButton = (Button)findViewById(R.id.promoteButton);
    	Button unlockButton = (Button)findViewById(R.id.unlockButton);
    	Button lockButton = (Button)findViewById(R.id.lockButton);
    	
        deleteButton.setVisibility(View.VISIBLE);
        lockButton.setVisibility(View.VISIBLE);
        promoteButton.setVisibility(View.VISIBLE);
        unlockButton.setVisibility(View.VISIBLE);
    }

}
