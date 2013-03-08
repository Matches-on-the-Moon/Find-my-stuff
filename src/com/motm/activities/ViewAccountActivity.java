package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.models.Account;

public class ViewAccountActivity extends Activity {
	
	private Button editAccountButton;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.account_view);  
        editAccountButton = (Button)findViewById(R.id.editAccountButton);
        setButtonDisplay();
    }
    
    private void startEditAccountActivity()
    {
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivity(intent);
    }
    
    public void editAccountButtonPressed(View view)
    {
    	startEditAccountActivity();
    }
    
    private void setFields() {
    	//sets the currently blank fields to ' + ": " + accountInformation; '
    }
    
    private void setButtonDisplay()
    {
        Account currentAccount = ((FMSApplication)getApplication()).getCurrentAccount();
        // if currentAccount == loginName
    	editAccountButton.setVisibility(View.VISIBLE);
    }
}
