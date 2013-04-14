package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
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
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();
        clearFields();
    }
    
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * 
     */
    private void startRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterAccountActivity.class);
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
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
            setLoginStatus(message);
            return;
        }
        
        // check if the loginName is locked
        if(accountManager.getAccountStateByLoginName(loginName) == Account.State.LOCKED){
            String message = getString(R.string.accountLocked);
            setLoginStatus(message);
            clearFields();
        } else {     
	        Account account = accountManager.attemptLogin(loginName, password);
	        
	        if(account == null) {
	            // failure
	            String message = getString(R.string.loginUnsuccessful);
	            setLoginStatus(message);
	            clearFields();
	            
	    	} else {
	            // success
	            String message = getString(R.string.loginSuccessful);
	            setLoginStatus(message);
	            
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
