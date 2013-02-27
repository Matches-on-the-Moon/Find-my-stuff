/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models;

import java.util.HashMap;

/**
 *
 * @author michael
 */
public class AccountManager
{
    private static HashMap<String, Account> accounts;

    public AccountManager()
    {
        if(accounts == null){
            accounts = new HashMap<String, Account>();
        }
    }
    
    public void createAccount(String loginName, String password, String name, String email)
    {
        Account account = new Account(loginName, password, name, email);
        accounts.put(loginName, account);
    }

    public Account getAccount(String loginName, String password)
    {
        Account account = accounts.get(loginName);
        
        if(account == null || !account.getPassword().equals(password)){
            return null;
        }
        
        return account;
    }

    public boolean lockAccount(String loginName)
    {

        return true;
    }

    public boolean unlockAccount(String loginName)
    {
        return true;
    }

    public boolean deleteAccount(String loginName)
    {
        return true;
    }

    public boolean editAccount(String loginName)
    {
        return true;
    }
    
    public boolean isLoginNameUnique(String loginName)
    {
        // check if there is a user with that login name
        // the hashmap's key is the login name
        
        if(accounts.containsKey(loginName)){
            // contains, so not unique
            return false;
        }
        
        return true;
    }
}
