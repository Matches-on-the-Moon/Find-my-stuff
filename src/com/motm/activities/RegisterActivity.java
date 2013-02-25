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

/**
 *
 * @author michael
 */
public class RegisterActivity extends Activity
{
    EditText loginNameInput;
    EditText passwordInput;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

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
        // check valid username
        if(true){
            // successful
            returnToLoginActivity();
        } else {
            // failure
            
        }
    }
}
