/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // set the models


        // setup the view elements
        loginNameInput = (EditText) findViewById(R.id.loginNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        // set the view
        setContentView(R.layout.login);
    }

    private void startRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void returnToMainActivity()
    {
        finish();
    }

    /*
     *      Actions
     */
    public void loginButtonPressed(View view)
    {
        // attempt login

        // successful
        returnToMainActivity();

        // failure
        // notify user
        // clear fields
    }

    public void registerButtonPressed(View view)
    {
        startRegisterActivity();
    }
}
