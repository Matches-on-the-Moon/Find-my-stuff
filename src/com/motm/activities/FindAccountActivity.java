package com.motm.activities;

import java.util.ArrayList;
import java.util.List;

import com.motm.adapters.AccountViewAdapter;
import com.motm.adapters.RowAccount;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.helpers.Factory;
import com.motm.models.Account;
import com.motm.models.interfaces.AccountManager;

public class FindAccountActivity extends Activity {
	
	private ListView listView;
	private List<RowAccount> rowAccounts;
	private AccountManager accountManager;
	private int targetAccountID;
    private AccountViewAdapter adapter;
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        // moved the onCreate stuff here until we can figure out how to stop it from duplicating the list every time or not updating at all.
        setContentView(R.layout.account_find);  
        rowAccounts = new ArrayList<RowAccount>();
        accountManager = (AccountManager)Factory.getAccountManager();
        adapter = new AccountViewAdapter(this, R.layout.account_find_list_rows, rowAccounts);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        updateRows();
    }
    
    /**
     * 
     */
    public void updateRows() {
    	// quick and dirty, fix later
        Account[] accounts = accountManager.getAllAccounts();
        int Id = 0;
    	for(Account account : accounts) {
    		RowAccount rowAccount = new RowAccount(Id, account.getName(), account.getEmail());
    		rowAccounts.add(rowAccount);
    	}
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
    
    /**
     * 
     */
    public void listOnScroll () 
    {
    	//soon to be added
    }

    /**
     * @param view
     */
    public void addRow(View view) 
    {
    	// soon to be added
    }
}
