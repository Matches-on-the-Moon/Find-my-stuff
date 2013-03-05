package com.motm.helpers;

import com.motm.models.AccountManager;
// import com.motm.models.DataManager;
import com.motm.models.ItemManager;

public class Factory
{
    public static AccountManager getAccountManager()
    {
        return new AccountManager();
    }
    
    public static ItemManager getItemManager()
    {
        return new ItemManager();
    }
}
