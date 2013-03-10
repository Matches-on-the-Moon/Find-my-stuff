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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
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
    
    public void updateRows() {
    	// quick and dirty, fix later
        Account[] accounts = accountManager.getAllAccounts();
        int Id = 0;
    	for(Account account : accounts) {
    		RowAccount rowAccount = new RowAccount(Id, account.getName(), account.getEmail());
    		rowAccounts.add(rowAccount);
    	}
    }
    
    public void viewAccountButtonPressed(View view) 
    {
    	String currentLoginName = FMSApplication.getInstance().getCurrentAccount().getLoginName();
    	targetAccountID = accountManager.getAccountIdByLoginName(currentLoginName);
        startViewAccountActivity();
    }
    
    public void startViewAccountActivity() 
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        intent.putExtra("targetAccount", targetAccountID);
        startActivity(intent);
    }
    
    public void accountButtonClicked(View view) 
    {
    	targetAccountID = accountManager.getAccountIdByLoginName(((TextView)(view.findViewById(R.id.loginName))).getText().toString());
    	startViewAccountActivity();
    }
    
    public void listOnScroll () 
    {
    	//soon to be added
    }

    public void addRow(View view) 
    {
    	// soon to be added
    }
}
