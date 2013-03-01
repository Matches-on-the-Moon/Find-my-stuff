/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.activities;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.motm.R;
// import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.AccountManager;

/**
 *
 * @author michael
 */
public class RegisterActivity extends Activity
{
    // models
    private AccountManager accountManager;
    
    // view elements
    private EditText loginNameInput;
    private EditText passwordInput;
    private EditText nameInput;
    private EditText emailInput;
    private TextView registrationStatus;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // models
        accountManager = Factory.getAccountManager();
        
        // set view
        setContentView(R.layout.account_add);
        
        // view elements
        loginNameInput       = (EditText)findViewById(R.id.registrationUsername);
        passwordInput        = (EditText)findViewById(R.id.registrationPassword);
        nameInput            = (EditText)findViewById(R.id.registrationName);
        emailInput           = (EditText)findViewById(R.id.registrationEmail);
        registrationStatus   = (TextView)findViewById(R.id.registrationStatus);
   }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        // clear fields
        clearFields();
        clearStatus();
    }

    private void returnToLoginActivity()
    {
        // login is previous, don't go forward go back
        finish();
    }
    
    private void setStatus(String message)
    {
        // set text
    	registrationStatus.setText(message);
        
        // make visible
    	registrationStatus.setVisibility(View.VISIBLE);
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
        loginNameInput.setText("");
        passwordInput.setText("");
        nameInput.setText("");
        emailInput.setText("");
    }
    
    /*
     *      Actions
     */
    public void submitButtonPressed(View view)
    {
        String loginName = loginNameInput.getText().toString().trim();
    	String password = passwordInput.getText().toString().trim();
    	String name = nameInput.getText().toString().trim();
    	String email = emailInput.getText().toString().trim();
        
        
        // check valid login name
        if(loginName.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            // not all fields
            String message = getString(R.string.registrationRequiredFields);
            setStatus(message);
            registrationStatus.setTextColor(Color.parseColor("#FF0000"));
            
        } else {
            // success
            // create the account
        	if (!accountManager.createAccount(loginName, password, name, email)) {
	            String message = getString(R.string.registrationUnsuccessful);
	            setStatus(message);
	            registrationStatus.setTextColor(Color.parseColor("#FF0000"));
        	} else {
	            // display the message
	            String message = getString(R.string.registrationSuccessful);
	            setStatus(message);
	            registrationStatus.setTextColor(Color.parseColor("#00FF00"));
        	}
            // return to the login activity
            returnToLoginActivity();
        }
    }
}
