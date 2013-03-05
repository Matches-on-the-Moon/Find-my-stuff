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

public class LoginActivity extends Activity
{
    private AccountManager accountManager;
    private EditText loginNameInput;
    private EditText passwordInput;
    private TextView loginStatus;

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
    
    @Override
    public void onResume()
    {
        super.onResume();
        clearFields();
        clearLoginStatus();
    }

    private void startRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void startMainActivity()
    {
        finish();
    }

    private void setLoginStatus(String message)
    {
        loginStatus.setText(message);
        loginStatus.setVisibility(View.VISIBLE);
    }
    
    private void clearLoginStatus()
    {
        loginStatus.setText("");
        loginStatus.setVisibility(View.INVISIBLE);
    }
    
    private void clearFields()
    {
        loginNameInput.setText("");
        passwordInput.setText("");
    }

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
        
        Account account = accountManager.getAccount(loginName, password);
        
        if(account == null) {
            String message = getString(R.string.passwordUnsuccessful);
            setLoginStatus(message);
            loginStatus.setTextColor(Color.parseColor("#FF0000"));
            clearFields();
    	} else {
            String message = getString(R.string.passwordSuccessful);
            setLoginStatus(message);
            loginStatus.setTextColor(Color.parseColor("#00FF00"));
            
            ((FMSApplication)getApplication()).setCurrentAccount(account);

            startMainActivity();
    	}
    }

    public void registerButtonPressed(View view)
    {
        startRegisterActivity();
    }
}
