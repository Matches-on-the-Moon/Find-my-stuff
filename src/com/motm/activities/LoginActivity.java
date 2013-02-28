/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.Account;
import com.motm.models.AccountManager;

/**
 *
 * @author michael
 */
public class LoginActivity extends Activity
{
    // models
    private AccountManager accountManager;
    
    // view variables
    private EditText loginNameInput;
    private EditText passwordInput;
    private TextView passwordStatus;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // set the models
        accountManager = Factory.getAccountManager();

        // set the view
        setContentView(R.layout.login);
        
        // setup the view elements
        loginNameInput = (EditText)findViewById(R.id.loginNameInput);
        passwordInput  = (EditText)findViewById(R.id.passwordInput);
        passwordStatus = (TextView)findViewById(R.id.passwordStatus);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        // clear fields
        clearFields();
        clearStatus();
    }

    private void startRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void returnToMainActivity()
    {
        // start main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        
        // finish
        finish();
    }

    private void setStatus(String message)
    {
        // set text
        passwordStatus.setText(message);
        
        // make visible
        passwordStatus.setVisibility(View.VISIBLE);
    }
    
    private void clearStatus()
    {
        // set text
        passwordStatus.setText("");
        
        // hide
        passwordStatus.setVisibility(View.INVISIBLE);
    }
    
    private void clearFields()
    {
        loginNameInput.setText("");
        passwordInput.setText("");
    }
    
    /*
     *      Actions
     */
    
    public void loginButtonPressed(View view)
    {
    	String loginName;
        String password;
        
        // get fields
        loginName = loginNameInput.getText().toString().trim();
    	password = passwordInput.getText().toString().trim();

        // validate
        if(loginName.isEmpty() || password.isEmpty()){
            String message = getString(R.string.loginRequiredFields);
            passwordStatus.setTextColor(Color.parseColor("#FF0000"));
            setStatus(message);
            return;
        }
        
        // try to get an account
        Account account = accountManager.getAccount(loginName, password);
        
        // successful
        if(account == null){
            // failure
            String message = getString(R.string.passwordUnsuccessful);
            setStatus(message);
            passwordStatus.setTextColor(Color.parseColor("#FF0000"));
            // clear fields
            clearFields();
            
    	} else {
            // successful
            String message = getString(R.string.passwordSuccessful);
            setStatus(message);
            passwordStatus.setTextColor(Color.parseColor("#00FF00"));
            
            // set the current account
            ((FMSApplication)getApplication()).setCurrentAccount(account);
            
            // return to main
            returnToMainActivity();
    	}
    }

    public void registerButtonPressed(View view)
    {
        startRegisterActivity();
    }
}
