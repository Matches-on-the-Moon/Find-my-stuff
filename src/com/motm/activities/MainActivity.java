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
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Account currentAccount = FMSApplication.getInstance().getCurrentAccount();

        if(currentAccount == null) {
            startLoginActivity();
        }
        
        setContentView(R.layout.main);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 
     */
    private void startLoginActivity()
    {
        // start login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        
        // finish main, to prevent back
        finish();
    }

    /**
     * 
     */
    private void startFindItemActivity()
    {
        Intent intent = new Intent(this, FindItemActivity.class);
        startActivity(intent);
    }
    
    /**
     * 
     */
    private void startFindAccountActivity()
    {
        Intent intent = new Intent(this, FindAccountActivity.class);
        startActivity(intent);
    }
    
    /*
     *  Actions
     */
    
    /**
     * @param view
     */
    public void findAccountButtonPressed(View view)
    {
        startFindAccountActivity();
    }
    
    /**
     * @param view
     */
    public void findItemButtonPressed(View view)
    {
        startFindItemActivity();
    }
    
    /**
     * @param view
     */
    public void logoutButtonPressed(View view)
    {
        // logout user
    	FMSApplication.getInstance().setCurrentAccount(null);

        // start login
        startLoginActivity();
    }
    
}
