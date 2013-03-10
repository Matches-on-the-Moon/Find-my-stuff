package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.models.Account;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Account currentAccount = ((FMSApplication)getApplication()).getCurrentAccount();

        if(currentAccount == null) {
            startLoginActivity();
        }
        
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void startLoginActivity()
    {
        // start login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        
        // finish main, to prevent back
        finish();
    }

    private void startFindItemActivity()
    {
        Intent intent = new Intent(this, FindItemActivity.class);
        startActivity(intent);
    }
    
    private void startFindAccountActivity()
    {
        Intent intent = new Intent(this, FindAccountActivity.class);
        startActivity(intent);
    }
    
    /*
     *  Actions
     */
    
    public void findAccountButtonPressed(View view)
    {
        startFindAccountActivity();
    }
    
    public void findItemButtonPressed(View view)
    {
        startFindItemActivity();
    }
    
    public void logoutButtonPressed(View view)
    {
    	// log user out
        ((FMSApplication)getApplication()).setCurrentAccount(null);
        
        // start the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    	
        // quit the main activity
        finish();
    }
    
}
