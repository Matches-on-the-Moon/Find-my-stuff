package com.motm.models;

import com.motm.models.interfaces.AccountManager;
import java.util.HashMap;

public class FileAccountManager implements AccountManager
{	
    private static HashMap<String, Account> accountHM;
    private Account account;

    public FileAccountManager()
    {
        if(accountHM == null){
            accountHM = new HashMap<String, Account>();
            accountHM.put("admin", new Admin("admin", "admin", "administrator", "FMS@gatech.edu"));
        }
    }
    
    public boolean createAccount(String loginName, String password, String name, String email)
    {
        if(accountHM.containsKey(loginName)) {
            return false;
        }
        account = new Account(loginName, password, name, email);
        accountHM.put(loginName, account);
        return true;
    }

    public Account getAccount(String loginName, String password)
    {
        account = accountHM.get(loginName);

        if(account == null || account.getAccountState() == Account.State.Locked || !account.getPassword().equals(password)) {
        	if(account != null) {
	        	account.setLoginAttempts(account.getLoginAttempts() + 1);
	        	return account;
        	}
            return null;
        }
        
        return account;
    }

    public boolean lockAccount(String loginName)
    {
    	account = accountHM.get(loginName);
    	account.setAccountState(Account.State.Locked);
        return true;
    }

    public boolean unlockAccount(String loginName)
    {
    	account = accountHM.get(loginName);
    	account.setAccountState(Account.State.Unlocked);
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
    
    public boolean editAccountEmail(String loginName, String email)
    {
    	account = accountHM.get(loginName);
    	account.setEmail(email);
    	return true;
    }
}
