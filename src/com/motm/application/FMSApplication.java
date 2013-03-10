package com.motm.application;

import android.app.Application;
import android.content.Context;
import com.motm.models.Account;

public class FMSApplication extends Application 
{
    private static Context context;
    private Account currentAccount;
    private static FMSApplication fmsApplication;

    
    @Override
    public void onCreate()
    {
        super.onCreate();
        FMSApplication.context = getApplicationContext();
        fmsApplication = this;
    }
    
    public static FMSApplication getInstance()
    {
    	return fmsApplication;
    }

    
    public Account getCurrentAccount()
    {
        return currentAccount;
    }
    
    public void setCurrentAccount(Account account)
    {
        currentAccount = account;
    }
    
    public static Context getAppContext()
    {
        return FMSApplication.context;
    }
}
