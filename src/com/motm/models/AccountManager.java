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
    private static HashMap<String, Account> accountHM;
    private Account account;

    public AccountManager()
    {
        if(accountHM == null){
            accountHM = new HashMap<String, Account>();
        }
    }
    
    public boolean createAccount(String loginName, String password, String name, String email)
    {
        // check if there is a user with that login name
        // the hashmap's key is the login name
        if(accountHM.containsKey(loginName)){
            // contains, so not unique
            return false;
        }
        account = new Account(loginName, password, name, email);
        accountHM.put(loginName, account);
        return true;
    }

    public Account getAccount(String loginName, String password)
    {
        account = accountHM.get(loginName);

        if(account == null || !account.getAccountState() || !account.getPassword().equals(password)) {
        	if(account != null) {
	        	account.setLoginAttempts();
	        	if(account.getLoginAttempts() >= 3)
	        		account.setAccountState(false);
        	}
            return null;
        }
        
        return account;
    }

    public boolean lockAccount(String loginName)
    {
    	account = accountHM.get(loginName);
    	account.setAccountState(false);
        return true;
    }

    public boolean unlockAccount(String loginName)
    {
    	account = accountHM.get(loginName);
    	account.setAccountState(true);
        return true;
    }

    public boolean deleteAccount(String loginName)
    {
    	account = accountHM.remove(loginName);
        return true;
    }

    public boolean editAccountPassword(String loginName, String password)
    {
    	account = accountHM.get(loginName);
    	account.setPassword(password);
        return true;
    }
    
    public boolean editAccountEmail(String loginName, String email) {
    	account = accountHM.get(loginName);
    	account.setEmail(email);
    	return true;
    }
}
