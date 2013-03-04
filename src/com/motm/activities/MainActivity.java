<<<<<<< HEAD
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startFindItemActivity()
    {
        Intent intent = new Intent(this, FindItemActivity.class);
        startActivity(intent);
        finish();
    }
    
    private void startFindAccountActivity()
    {
        Intent intent = new Intent(this, FindAccountActivity.class);
        startActivity(intent);
        finish();
    }
    
    public void findAccountButtonPressed(View view)
    {
        startFindAccountActivity();
    }
    
    public void findItemButtonPressed(View view)
    {
        startFindItemActivity();
    }
    
}
=======
package com.motm.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

import com.example.findmystuff.User;
import com.motm.R;
import com.motm.helpers.Logger;

public class MainActivity extends Activity
{
	
	private static HashMap<String,User> userTable;
	private static String filename = "userTable";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        
        
        // if the user is not logged in
        if(true){
            // take them to the login activity
        	userTable = new HashMap<String,User>();
            startLoginActivity();
            // close the main activity, so you can't go back
            finish();
            return;
        }

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
    public static HashMap<String, User> getTable()
    {
    	return userTable;
    }
    public static void serializeTable(){
    	
    	FileOutputStream fos = null;
    	ObjectOutputStream oos = null;
    	
		try {fos = new FileOutputStream(filename);}
		catch (FileNotFoundException e) {e.printStackTrace();	}
		
		try {oos = new ObjectOutputStream( fos );}
		catch (IOException e) {e.printStackTrace();}
		
		try { oos.writeObject(userTable);}
		catch (IOException e) {e.printStackTrace();}
    }
    public static HashMap<String,User> deserializeTable(){
    	
    	FileInputStream fos = null;
    	ObjectInputStream oos = null;
    	
    	if( ! new File(filename).exists() ){
    		return new HashMap<String,User> ();
    	}
    	
		try {fos = new FileInputStream(filename);} 
		catch (FileNotFoundException e) {e.printStackTrace();	}
		
		try {oos = new ObjectInputStream( fos );} 
		catch (IOException e) {e.printStackTrace();}
		
		try { userTable = (HashMap<String,User>)oos.readObject();} 
		catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (OptionalDataException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
		
		return userTable;
    }
}
>>>>>>> 71d6d59186e85a4337f02a39c0091d73a96605be
