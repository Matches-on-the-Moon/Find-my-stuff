/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
        String name = "";
        String login = "";
        String password = "";
        String email = "";
        
        boolean result = account.createAccount(name, login, password, email);
        
        // check valid username
        if(result){
            // successful
            returnToLoginActivity();
            
        } else {
            // failure
            
        }
    }
}
