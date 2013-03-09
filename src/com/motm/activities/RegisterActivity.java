package com.motm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.motm.R;
import com.motm.helpers.Factory;
import com.motm.models.interfaces.AccountManager;

public class RegisterActivity extends Activity
{
    private AccountManager accountManager;
    private EditText loginNameInput;
    private EditText passwordInput;
    private EditText nameInput;
    private EditText emailInput;
    private TextView registrationStatus;

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
        registrationStatus   = (TextView)findViewById(R.id.registrationStatus);
   }
    
    @Override
    public void onResume()
    {
        super.onResume();
        clearFields();
        clearStatus();
    }

    private void startLoginActivity()
    {
        finish();
    }
    
    private void setStatus(String message)
    {
    	registrationStatus.setText(message);
    	registrationStatus.setVisibility(View.VISIBLE);
    }
    
    private void clearStatus()
    {
    	registrationStatus.setText("");
    	registrationStatus.setVisibility(View.INVISIBLE);
    }
    
    private void clearFields()
    {
        loginNameInput.setText("");
        passwordInput.setText("");
        nameInput.setText("");
        emailInput.setText("");
    }
    
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
				    String message = getString(R.string.registrationUnsuccessful);
				    setStatus(message);
				} else {
				    String message = getString(R.string.registrationSuccessful);
				    setStatus(message);
				    startLoginActivity();
				}
			} catch (Exception e) {
			    String message = getString(R.string.registrationException);
			    setStatus(message);
			}
        }
    }
}
