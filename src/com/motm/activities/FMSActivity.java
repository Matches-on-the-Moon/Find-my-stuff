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
import android.widget.TextView;
import java.util.HashMap;

import com.example.findmystuff.User;
import com.motm.R;

/**
 *
 * @author michael
 */
public class FMSActivity extends Activity
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
        setContentView(R.layout.main);

    }
    public void writeTable(View view){
    	
    	MainActivity.serializeTable();
    	finish();
    }
}
