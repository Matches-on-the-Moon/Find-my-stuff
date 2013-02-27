/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.helpers;

import com.motm.application.FMSApplication;
import com.motm.models.AccountManager;
import com.motm.models.DataManager;
import com.motm.models.ItemManager;

/**
 *
 * @author michael
 */
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
