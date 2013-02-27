/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.application;

import android.app.Application;
import com.motm.models.Account;

/**
 *
 * @author michael
 */
public class FMSApplication extends Application 
{
    private Account currentAccount;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
    /*
     *  
     */
    
    public Account getCurrentAccount()
    {
        return currentAccount;
    }
    
    public void setCurrentAccount(Account account)
    {
        currentAccount = account;
    }
}
