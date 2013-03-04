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
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.motm.R;
import com.motm.helpers.Factory;
import com.motm.models.AccountManager;

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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
        	if (!accountManager.createAccount(loginName, password, name, email)) {
	            String message = getString(R.string.registrationUnsuccessful);
	            setStatus(message);
        	} else {
	            String message = getString(R.string.registrationSuccessful);
	            setStatus(message);
	            startLoginActivity();
        	}
        }
    }
}
=======
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findmystuff.User;
import com.motm.R;
import com.motm.helpers.Logger;
/*
 * @author michael
 */
public class RegisterActivity extends Activity
{
    EditText usernameInput;
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

        setContentView(R.layout.register);
        usernameInput = (EditText) findViewById(R.id.registrationUsername);
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
        // check valid username
    	String username = usernameInput.getText().toString();
    	String password = passwordInput.getText().toString();
    	String name = nameInput.getText().toString();
    	String email = emailInput.getText().toString();
    	
        if( userTable.containsKey(username) ){
        	
            
        	status.setTextColor( Color.parseColor("#FF0000"));
        	status.setText( R.string.registrationUnsuccessful );
        	status.setVisibility(View.VISIBLE);
        	        	
        } else {
            // success
        	User user = new User( username, password, name, email );
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
>>>>>>> 71d6d59186e85a4337f02a39c0091d73a96605be
