package com.motm.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.motm.R;
import com.motm.adapters.AccountViewAdapter;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.Account;
import com.motm.models.interfaces.AccountManager;
import java.util.ArrayList;

public class FindAccountActivity extends ListActivity {
	
	private ArrayList<Account> rowAccounts;
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
    
    
    /**
     * @param view
     */
    public void viewAccountButtonPressed(View view) 
    {
    	targetAccountID = FMSApplication.getInstance().getCurrentAccount().getAccountId();
        startViewAccountActivity();
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
