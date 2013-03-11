package com.motm.helpers;

import com.motm.models.interfaces.AccountManager;
import com.motm.models.interfaces.ItemManager;
import com.motm.models.FileAccountManager;
import com.motm.models.FileItemManager;

public class Factory
{
    /**
     * @return AccountManager
     */
    public static AccountManager getAccountManager()
    {
        return new FileAccountManager();
    }
    
    /**
     * @return ItemManager
     */
    public static ItemManager getItemManager()
    {
        return new FileItemManager();
    }
}
