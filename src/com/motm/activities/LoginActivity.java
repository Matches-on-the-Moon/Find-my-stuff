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
import com.motm.models.interfaces.AccountManager;

public class LoginActivity extends Activity
{
    private AccountManager accountManager;
    private EditText loginNameInput;
    private EditText passwordInput;
    private TextView loginStatus;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        
        setContentView(R.layout.login);
        
        accountManager = Factory.getAccountManager();
        
        loginNameInput = (EditText)findViewById(R.id.loginNameInput);
        passwordInput  = (EditText)findViewById(R.id.passwordInput);
        loginStatus = (TextView)findViewById(R.id.loginStatus);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();
        clearFields();
        clearLoginStatus();
    }

    /**
     * 
     */
    private void startRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 
     */
    private void startMainActivity()
    {
        // start main
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        
        // finish login
        finish();
    }

    /**
     * @param message
     */
    private void setLoginStatus(String message)
    {
        loginStatus.setText(message);
        loginStatus.setVisibility(View.VISIBLE);
    }
    
    /**
     * 
     */
    private void clearLoginStatus()
    {
        loginStatus.setText("");
        loginStatus.setVisibility(View.INVISIBLE);
    }
    
    /**
     * 
     */
    private void clearFields()
    {
        loginNameInput.setText("");
        passwordInput.setText("");
    }

    /**
     * @param view
     */
    public void loginButtonPressed(View view)
    {
    	String loginName;
        String password;
        loginName = loginNameInput.getText().toString().trim();
    	password = passwordInput.getText().toString().trim();
    	
        if(loginName.isEmpty() || password.isEmpty()) {
            String message = getString(R.string.loginRequiredFields);
            loginStatus.setTextColor(Color.parseColor("#FF0000"));
            setLoginStatus(message);
            return;
        }
        
        // check if the loginName is locked
        if(accountManager.getAccountStateByLoginName(loginName) == Account.State.Locked){
            String message = getString(R.string.accountLocked);
            setLoginStatus(message);
            loginStatus.setTextColor(Color.parseColor("#FF0000"));
            clearFields();
        } else {     
	        Account account = accountManager.attemptLogin(loginName, password);
	        
	        if(account == null) {
	            // failure
	            String message = getString(R.string.loginUnsuccessful);
	            setLoginStatus(message);
	            loginStatus.setTextColor(Color.parseColor("#FF0000"));
	            clearFields();
	            
	    	} else {
	            // success
	            String message = getString(R.string.loginSuccessful);
	            setLoginStatus(message);
	            loginStatus.setTextColor(Color.parseColor("#00FF00"));
	            
	            FMSApplication.getInstance().setCurrentAccount(account);
	
	            startMainActivity();
	    	}
        }
    }

    /**
     * @param view
     */
    public void registerButtonPressed(View view)
    {
        startRegisterActivity();
    }
}
