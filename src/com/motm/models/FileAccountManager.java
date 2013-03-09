package com.motm.models;

import com.motm.models.interfaces.AccountManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class FileAccountManager implements AccountManager
{	
    private static HashMap<Integer, Account> accountHM;// Key = ID, Value = Account

    public FileAccountManager()
    {
        if(accountHM == null){
            accountHM = new HashMap<Integer, Account>();
            accountHM.put(0, new Admin(0, "admin", "admin", "administrator", "FMS@gatech.edu", Account.State.Unlocked, 0));
        }
    }
    
    /*
     *  Creates a new account
     *  For the FileAccountManager, it needs to also choose a unique id for the Account
     */
    public boolean createAccount(String loginName, String password, String name, String email) throws Exception
    {
        if(!isLoginNameUnique(loginName)){
            return false;
        }
        
        // create a new, unique key
        Set<Integer> AccountIDs = accountHM.keySet();
        if(AccountIDs.size() >= Integer.MAX_VALUE){
            throw new Exception("Can't create a new account, the max amount of accounts have been created");
        }
        Integer id = Collections.max(AccountIDs) + 1;
        // keep incrementing the id until the set doesn't contain it
        while(AccountIDs.contains(id)){
            id++;
        }
        
        // create a new account
        Account account = new Account(id, loginName, password, name, email, Account.State.Unlocked, 0);
        accountHM.put(id, account);
        
        return true;
    }
    
    public Account attemptLogin(String loginName, String password)
    {
        Account account = null;
        
        // find an account with the loginName
        for(Account _account : accountHM.values()){
            if(_account.getLoginName().equals(loginName)){
                account = _account;
                break;
            }
        }
        
        // if we didn't find the account, return null
        if(account == null){
            return null;
        }
        
        // check to make sure the password matches
        if(!account.getPassword().endsWith(password)){
            // password doesn't match, increment the login attempts
            account.setLoginAttempts(account.getLoginAttempts() + 1);
            
            // if the attempts are >= 3 then lock the account
            if(account.getLoginAttempts() >= 3){
                lockAccount(account.getAccountId());
            }
            
            // return null
            return null;
        }
        
        return account;
    }
    
    public Account.State getAccountStateByLoginName(String loginName)
    {
        for(Account account : accountHM.values()){
            if(account.getLoginName().equals(loginName)){
                return account.getAccountState();
            }
        }
        
        // if the account is not found, say it's unlocked
        return Account.State.Unlocked;
    }

    public Account getAccount(Integer accountID)
    {
        Account account = accountHM.get(accountID);
        
        return account;
    }

    public boolean lockAccount(Integer accountID)
    {
    	Account account = accountHM.get(accountID);
    	account.setAccountState(Account.State.Locked);
        return true;
    }

    public boolean unlockAccount(Integer accountID)
    {
    	Account account = accountHM.get(accountID);
    	account.setAccountState(Account.State.Unlocked);
        return true;
    }

    public boolean deleteAccount(Integer accountID)
    {
    	Account account = accountHM.remove(accountID);
        return true;
    }

    public boolean editAccountPassword(Integer accountID, String password)
    {
    	Account account = accountHM.get(accountID);
    	account.setPassword(password);
        return true;
    }
    
    public boolean editAccountEmail(Integer accountID, String email)
    {
    	Account account = accountHM.get(accountID);
    	account.setEmail(email);
    	return true;
    }
    
    public boolean isLoginNameUnique(String loginName)
    {
        for(Account account : accountHM.values()){
            if(account.getLoginName().equals(loginName)){
                return false;
            }
        }
        
        return true;
    }
}
