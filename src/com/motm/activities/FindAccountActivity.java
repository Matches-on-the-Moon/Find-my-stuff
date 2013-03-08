package com.motm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.motm.R;

public class FindAccountActivity extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.account_find);  
    }
    
    public void viewAccountButtonPressed(View view) 
    {
        startViewAccountActivity();
    }
    
    public void startViewAccountActivity() {
        Intent intent = new Intent(this, ViewAccountActivity.class);
        startActivity(intent);
    }
}
