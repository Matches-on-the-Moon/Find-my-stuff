package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.interfaces.AccountManager;


public class EditAccountActivity extends Activity {

	private int targetAccountID;
    private AccountManager accountManager;
	private EditText passwordInput;
	private EditText emailInput;
	private TextView loginName;
	private String message;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_edit);
		
        passwordInput        = (EditText)findViewById(R.id.editPasswordInput);
        emailInput           = (EditText)findViewById(R.id.editEmailInput);
        loginName			 = (TextView)findViewById(R.id.loginName);
        
		// find and set the targeted account
        accountManager = Factory.getAccountManager();
		targetAccountID = getIntent().getExtras().getInt("targetAccount");
		loginName.setText(accountManager.getAccount(targetAccountID).getLoginName());
        if (accountManager.isAdmin(FMSApplication.getInstance().getCurrentAccount().getAccountId()))
        	setButtonDisplay();
	}
	
    /**
     * 
     */
    private void startViewAccountActivity()
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        intent.putExtra("targetAccount", targetAccountID);
        startActivity(intent);
    }
    
    /**
     * 
     */
    private void clearToMainActivity()
    {
        // start main
        Intent intent = new Intent(this, MainActivity.class);
        // clear top will go back the main and clear everything after
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    
    /**
     * @param view
     */
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
    
    /**
     * @param view
     */
    public void promoteButtonPressed(View view)
    {
    	if(accountManager.promoteAccount(targetAccountID)) {
	    	message = "Account promoted.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    	}
    }
    
    /**
     * @param view
     */
    public void lockButtonPressed(View view) 
    {
    	if(accountManager.lockAccount(targetAccountID)) {
	        message = "Account locked.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    	}
    }
    
    /**
     * @param view
     */
    public void unlockButtonPressed(View view)
    {
    	if(accountManager.unlockAccount(targetAccountID)) {
        	message = "Account unlocked.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    	}
    }
    
    /**
     * @param view
     */
    public void deleteButtonPressed(View view)
    {
    	if(accountManager.deleteAccount(targetAccountID)) {
    		message = "Account deleted.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        	if(FMSApplication.getInstance().getCurrentAccount().getAccountId() != targetAccountID) {
        		finish();
        	} else {
                FMSApplication.getInstance().setCurrentAccount(null);
                clearToMainActivity();
                finish();
        	}
    	}
    }
    
    /**
     * @param view
     */
    public void editPasswordButtonPressed(View view) 
    {
    	passwordInput.setVisibility(View.VISIBLE);
    }
    
    /**
     * @param view
     */
    public void editEmailButtonPressed(View view) 
    {
    	emailInput.setVisibility(View.VISIBLE);
    }
    
    /**
     * @param view
     */
    public void editItemPictureButtonPressed(View view) {
    	// do stuff
    }
    
    /**
     * 
     */
    private void setButtonDisplay()
    {
    	Button deleteButton = (Button)findViewById(R.id.deleteButton);
    	Button unlockButton = (Button)findViewById(R.id.unlockButton);
    	Button lockButton = (Button)findViewById(R.id.lockButton);
    	Button promoteButton = (Button)findViewById(R.id.promoteButton);
        lockButton.setVisibility(View.VISIBLE);
        unlockButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    	if(!accountManager.isAdmin(targetAccountID)) 
            promoteButton.setVisibility(View.VISIBLE);
    }

}
