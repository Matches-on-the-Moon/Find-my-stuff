/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.helpers;

import com.motm.models.Account;

/**
 *
 * @author michael
 */
public class AccountHelper
{
    public static boolean isCurrentAccountLoggedIn(Account account)
    {
        if(account == null){
            return false;
        }
        
        return true;
    }
}
