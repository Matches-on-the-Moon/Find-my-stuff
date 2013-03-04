<<<<<<< HEAD
<<<<<<< HEAD
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
=======
>>>>>>> 38e022dc8f84ba5d89cdd2f1cf3c8f2b267a397f
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
        finish();
    }

    private void startMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
=======
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
import java.util.HashMap;

import com.example.findmystuff.User;
import com.motm.R;

/**
 *
 * @author michael
 */
public class LoginActivity extends Activity
{
    // models
    
    // view variables
    EditText loginNameInput;
    EditText passwordInput;
    static TextView passwordStatus;
    String passwordSuccessful;
    String passwordUnsuccessful;
    HashMap<String,User> userTable;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // set the models


        // setup the view elements

        // set the view
        setContentView(R.layout.login);
        
        loginNameInput = (EditText) findViewById(R.id.loginNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        passwordStatus = (TextView) findViewById(R.id.passwordStatus);
        passwordSuccessful = getString(R.string.passwordSuccessful);
        passwordUnsuccessful = getString(R.string.passwordUnsuccessful);
        userTable = MainActivity.getTable();
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

    /*
     *      Actions
     */
    public void loginButtonPressed(View view)
    {
        // attempt login
    	String name = loginNameInput.getText().toString();
    	String password = passwordInput.getText().toString();
        // successful
    	
    	if( userTable.containsKey( name ) && userTable.get( name ).getPassword().equals( password ) )
    	{
    		passwordStatus.setText( passwordSuccessful );
    		passwordStatus.setTextColor( Color.parseColor("#00FF00"));
    		loginNameInput.setText("");
    		passwordInput.setText("");
    	}
    	else
    	{
    		passwordStatus.setText( passwordUnsuccessful );
    		passwordStatus.setTextColor( Color.parseColor("#FF0000"));
    	}
		
    	
    	passwordStatus.setVisibility(View.VISIBLE);
        

        // failure
        // notify user
        // clear fields
    }

    public void registerButtonPressed(View view)
    {
        startRegisterActivity();
    }

	public static void setStatus( String str) {
		// TODO Auto-generated method stub
		passwordStatus.setText( str );
		
	}
}
>>>>>>> 71d6d59186e85a4337f02a39c0091d73a96605be
