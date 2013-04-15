package com.motm.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.motm.R;
import com.motm.adapters.AccountViewAdapter;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.Account;
import com.motm.models.interfaces.AccountManager;
import java.util.List;

public class FindAccountActivity extends ListActivity {
	
	private List<Account> rowAccounts;
	private AccountManager accountManager;
	private int targetAccountID;
    private AccountViewAdapter adapter;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_find);  
        accountManager = Factory.getAccountManager();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        // moved the onCreate stuff here until we can figure out how to stop it from duplicating the list every time or not updating at all.
        rowAccounts = accountManager.getAllAccounts();
        adapter = new AccountViewAdapter(this, R.layout.account_find_list_rows, rowAccounts);
        setListAdapter(adapter);
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
    
    /*
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent intent;
        switch (item.getItemId())
        {
        case R.id.viewAccount:
            intent = new Intent(this, ViewAccountActivity.class);
            Integer targetAccountID = FMSApplication.getInstance().getCurrentAccount().getAccountId();
            intent.putExtra("targetAccount", targetAccountID);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "\"Viewing your account.\"", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.logoutButton:
            Toast.makeText(getApplicationContext(), "\"See you next time!\"", Toast.LENGTH_SHORT).show();
            FMSApplication.getInstance().setCurrentAccount(null);
            // start login
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    } 
    
    /**
     * 
     */
    public void startViewAccountActivity() 
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        intent.putExtra("targetAccount", targetAccountID);
        startActivity(intent);
    }
    
    /**
     * @param view
     */
    public void accountButtonClicked(View view) 
    {
    	targetAccountID = accountManager.getAccountIdByLoginName(((TextView)(view.findViewById(R.id.loginName))).getText().toString());
    	startViewAccountActivity();
    }
    
}
