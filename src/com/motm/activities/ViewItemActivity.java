package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.motm.R;
import com.motm.application.FMSApplication;
import com.motm.models.Account;

public class ViewItemActivity extends Activity {
	
	private Button lostFoundButton;
	private Button editItemButton;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.item_view);
        lostFoundButton = (Button)findViewById(R.id.lostFoundButton);
        editItemButton = (Button)findViewById(R.id.editItemButton);
        setButtonDisplay();
        
    }
    
    private void startViewAccountActivity()
    {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        startActivity(intent);
    }
    
    private void startEditItemActivity()
    {
        Intent intent = new Intent(this, EditItemActivity.class);
        startActivity(intent);
    }
    
    public void editItemButtonPressed(View view)
    {
    	startEditItemActivity();
    }
    
    public void lostFoundButtonPressed(View view)
    {
        startViewAccountActivity();
    }
    
    private void setFields() {
    	//sets the currently blank fields to ' + ": " + itemInformation; '
    }
    
    private void setButtonDisplay()
    {
        Account currentAccount = ((FMSApplication)getApplication()).getCurrentAccount();
        // if currentAccount != ownersAccount
    	lostFoundButton.setVisibility(View.VISIBLE);
    	// else editItemButton.setVisibility(View.VISIBLE);
    }
}
