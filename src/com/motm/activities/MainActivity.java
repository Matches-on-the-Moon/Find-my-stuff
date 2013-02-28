package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.AccountHelper;
// import com.motm.helpers.Factory;
import com.motm.models.Account;
// import com.motm.models.AccountManager;

public class MainActivity extends Activity
{
    // models
        
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // get current account
        Account currentAccount = ((FMSApplication)getApplication()).getCurrentAccount();
        
        // if the user is not logged in
        if(!AccountHelper.isCurrentAccountLoggedIn(currentAccount)){
            // take them to the login activity
            startLoginActivity();
            // close the main activity, so you can't go back
            finish();
            return;
        }

        // set view
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void startLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
