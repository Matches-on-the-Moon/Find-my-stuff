<<<<<<< HEAD
<<<<<<< HEAD
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
=======
>>>>>>> 38e022dc8f84ba5d89cdd2f1cf3c8f2b267a397f
package com.motm.application;

import android.app.Application;
import com.motm.models.Account;

public class FMSApplication extends Application 
{
    private Account currentAccount;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
    public Account getCurrentAccount()
    {
        return currentAccount;
    }
    
    public void setCurrentAccount(Account account)
    {
        currentAccount = account;
    }
}
=======
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.application;

import android.app.Application;
import com.motm.helpers.Logger;

/**
 *
 * @author michael
 */
public class FMSApplication extends Application 
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        
    }
}
>>>>>>> 71d6d59186e85a4337f02a39c0091d73a96605be
