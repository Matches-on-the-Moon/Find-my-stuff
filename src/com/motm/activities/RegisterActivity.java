/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.activities;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.motm.models.User;
import com.motm.R;
import com.motm.models.AccountManager;

/**
 *
 * @author michael
 */
public class RegisterActivity extends Activity
{
    // models
    AccountManager account;
    
    // view elements
    EditText loginNameInput;
    EditText passwordInput;
    EditText nameInput;
    EditText emailInput;
    TextView status;
    String registrationSuccessful;
    String registrationUnsuccessful;
    HashMap<String,User> userTable;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // models
        account = new AccountManager();
        
        // view elements
        
        
        setContentView(R.layout.register);
        loginNameInput = (EditText) findViewById(R.id.registrationUsername);
        passwordInput = (EditText) findViewById(R.id.registrationPassword);
        nameInput = (EditText) findViewById(R.id.registrationName);
        emailInput = (EditText) findViewById(R.id.registrationEmail);
        status = (TextView) findViewById(R.id.registrationStatus);
        registrationSuccessful = getString(R.string.registrationSuccessful);
        registrationUnsuccessful = getString(R.string.registrationUnsuccessful);
        userTable = MainActivity.getTable();
    }

    private void returnToLoginActivity()
    {
        // login is previous, don't go forward go back
        finish();
    }
    
    /*
     *      Actions
     */
    public void registerButtonPressed(View view)
    {
        String login = loginNameInput.getText().toString();
    	String password = passwordInput.getText().toString();
    	String name = nameInput.getText().toString();
    	String email = emailInput.getText().toString();
        
        boolean result = account.createAccount(name, login, password, email);
        
        // check valid username
        if( userTable.containsKey(login) ){
        	status.setTextColor( Color.parseColor("#FF0000"));
        	status.setText( R.string.registrationUnsuccessful );
        	status.setVisibility(View.VISIBLE);
        	        	
        } else {
            // success
        	User user = new User( login, password, name, email );
        	userTable.put( user.getName(), user );
        	status.setTextColor( Color.parseColor("#00FF00"));
        	status.setText( R.string.registrationSuccessful );
        	status.setVisibility(View.VISIBLE);
        	startFMSAppActivity();
        }
        
    }
    
    private void startFMSAppActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
