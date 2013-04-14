package com.motm.application;

import android.app.Application;
import android.content.Context;
import com.motm.models.Account;

public class FMSApplication extends Application 
{
    private static Context Context;
    private Account currentAccount;
    private static FMSApplication FMSApplication;

    
    /* (non-Javadoc)
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        Context = getApplicationContext();
        FMSApplication = this;
    }
    
    /**
     * @return
     */
    public static FMSApplication getInstance()
    {
    	return FMSApplication;
    }

    
    /**
     * @return
     */
    public Account getCurrentAccount()
    {
        return currentAccount;
    }
    
    /**
     * @param account
     */
    public void setCurrentAccount(Account account)
    {
        currentAccount = account;
    }
    
    /**
     * @return
     */
    public static Context getAppContext()
    {
        return FMSApplication.Context;
    }
}
