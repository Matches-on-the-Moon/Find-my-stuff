package com.motm.helpers;

import com.motm.models.DBAccountManager;
import com.motm.models.interfaces.AccountManager;
import com.motm.models.interfaces.ItemManager;
import com.motm.models.DBItemManager;

public class Factory
{
    /**
     * @return AccountManager
     */
    public static AccountManager getAccountManager()
    {
        return new DBAccountManager();
    }
    
    /**
     * @return ItemManager
     */
    public static ItemManager getItemManager()
    {
        return new DBItemManager();
    }
}
