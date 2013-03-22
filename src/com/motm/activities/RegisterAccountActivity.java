package com.motm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.motm.R;
import com.motm.helpers.FMSException;
import com.motm.helpers.Factory;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.AccountManager;

public class RegisterActivity extends Activity
{
    private AccountManager accountManager;
    private EditText loginNameInput;
    private EditText passwordInput;
    private EditText nameInput;
    private EditText emailInput;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        
        accountManager = Factory.getAccountManager();
        
        setContentView(R.layout.account_add);
        
        loginNameInput       = (EditText)findViewById(R.id.registrationUsername);
        passwordInput        = (EditText)findViewById(R.id.registrationPassword);
        nameInput            = (EditText)findViewById(R.id.registrationName);
        emailInput           = (EditText)findViewById(R.id.registrationEmail);
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

    /**
     * 
     */
    private void startLoginActivity()
    {
        finish();
    }
    
    /**
     * @param message
     */
    private void setStatus(String message)
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
        nameInput.setText("");
        emailInput.setText("");
    }
    
    /**
     * @param view
     */
    public void submitButtonPressed(View view)
    {
        String loginName = loginNameInput.getText().toString().trim();
    	String password = passwordInput.getText().toString().trim();
    	String name = nameInput.getText().toString().trim();
    	String email = emailInput.getText().toString().trim();
        
        if(loginName.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            String message = getString(R.string.registrationRequiredFields);
            setStatus(message);
        } else {
        	try {
				if (!accountManager.createAccount(loginName, password, name, email)) {
                    // failure
				    String message = getString(R.string.registrationUnsuccessful);
				    setStatus(message);
				} else {
                    // success
				    String message = getString(R.string.registrationSuccessful);
				    setStatus(message);
				    startLoginActivity();
				}
			}
            catch (FMSException e) {
                Logger.d(e.getLocalizedMessage()+", "+e.getMessage()+", "+e.getClass()+", "+e.getCause());
			    String message = getString(R.string.registrationException);
			    setStatus(message);
			}
        }
    }
    
    public void cancelButtonPressed(View view)
    {
    	finish();
    }
}
