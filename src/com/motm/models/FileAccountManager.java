package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import com.motm.application.FMSApplication;
import com.motm.helpers.FMSException;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.AccountManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileAccountManager implements AccountManager
{
    private static final String FILENAME = "accountsSA";
    
    private static SparseArray<Account> AccountsSA;// Key = ID, Value = Account

    public FileAccountManager()
    {
        if(AccountsSA == null){
            AccountsSA = new SparseArray<Account>();
        }
        
        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)){
            preferences.edit().putBoolean("firstRun", false).commit();
            AccountsSA.put(0, new Admin(0, "admin", "admin", "administrator", "FMS_Admin@gatech.edu", Account.State.UNLOCKED, 0));
            AccountsSA.put(1, new Account(1, "user", "user", "user", "FMS_User@gatech.edu", Account.State.UNLOCKED, 0));
            AccountsSA.put(2, new Account(2, "a", "a", "a", "a@a.com", Account.State.UNLOCKED, 0));
            saveData();
            return;
        }
        
        loadData();
    }
    
    /**
     *  Creates a new account
     *  For the FileAccountManager, it needs to also choose a unique id for the Account
     *  @param loginName loginName
	 *  @param password password
	 *  @param name name
	 *  @param email email
     */
    public boolean createAccount(String loginName, String password, String name, String email) throws FMSException
    {
        if(!isLoginNameUnique(loginName)){
            return false;
        }
        
        // create a new, unique key
        if(AccountsSA.size() >= Integer.MAX_VALUE){
            Logger.d("Can't create a new account, the max amount of accounts have been created");
            throw new FMSException("Can't create a new account, the max amount of accounts have been created");
        }
        Integer id = 1;
        // keep incrementing the id until the set doesn't contain it
        while(AccountsSA.get(id) != null){
            id++;
        }
        
        // create a new account
        Account account = new Account(id, loginName, password, name, email, Account.State.UNLOCKED, 0);
        AccountsSA.put(id, account);
        
        saveData();
        return true;
    }
    /**
     * Attempt login
     * @param loginName 
	 * @param password
	 * @return account 
     */
    public Account attemptLogin(String loginName, String password)
    {
        Account account = null;
        
        // find an account with the loginName
        int key = 0;
        Account accountTmp;
        for(int i = 0; i < AccountsSA.size(); i++) {
           key = AccountsSA.keyAt(i);
           // get the object by the key.
           accountTmp = AccountsSA.get(key);
           if(accountTmp.getLoginName().equals(loginName)) {
                account = accountTmp;
                break;
            }
        }
        
        // if we didn't find the account, return null
        if(account == null){
            return null;
        }
        
        // check to make sure the password matches
        if(!account.getPassword().equals(password)){
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
    /**
     * Get account information for a user
     * @param loginName 
     * @return account state
     */
    public Account.State getAccountStateByLoginName(String loginName)
    {
        int key = 0;
        Account account;
        for(int i = 0; i < AccountsSA.size(); i++) {
            key = AccountsSA.keyAt(i);
            // get the object by the key.
            account = AccountsSA.get(key);
            if(account.getLoginName().equals(loginName)){
                return account.getAccountState();
            }
        }
        
        // if the account is not found, say it's unlocked
        return Account.State.UNLOCKED;
    }
    /**
     * Get the account for loginName
     * @param loginName
     */
    public int getAccountIdByLoginName(String loginName)
    {
        int key = 0;
        Account account;
        for(int i = 0; i < AccountsSA.size(); i++) {
            key = AccountsSA.keyAt(i);
            // get the object by the key.
            account = AccountsSA.get(key);
            if(account.getLoginName().equals(loginName)){
                return account.getAccountId();
            }
        }
        
        return 0; // just a place holder.
    }
	/**
	 * Retrieve account
	 * @param accountID id of account
	 */
    public Account getAccount(Integer accountID)
    {
        return AccountsSA.get(accountID);
        
    }
    /**
     * Get all accounts
     * @return all accounts
     */
    public List<Account> getAllAccounts()
    {
        List<Account> accounts = new ArrayList<Account>();
        
        int key = 0;   
        Account accountTmp;
        for(int i = 0; i < AccountsSA.size(); i++) {
            key = AccountsSA.keyAt(i);
            // get the object by the key.
            accountTmp = AccountsSA.get(key);
            accounts.add(accountTmp);
        }
        
		return accounts;
    }

    /**
     * Lock a user's account
     * @param accountID id
     * @return true
     */
    public boolean lockAccount(Integer accountID)
    {
    	AccountsSA.get(accountID).setAccountState(Account.State.LOCKED);
        
        saveData();
        
        return true;
    }

    /**
     * Unlock account
     * @param accountID id of account
     * @return true
     */
    public boolean unlockAccount(Integer accountID)
    {
    	AccountsSA.get(accountID).setAccountState(Account.State.UNLOCKED);
        
        saveData();
        
        return true;
    }
    /**
     * Delete account
     * @param accountID id
     * @return true
     */
    public boolean deleteAccount(Integer accountID)
    {
    	AccountsSA.remove(accountID);
        saveData();
        
        return true;
    }
    /**
     * Edit password
     * @param accountID
     * @param password
     * @return true
     */
    public boolean editAccountPassword(Integer accountID, String password)
    {
    	AccountsSA.get(accountID).setPassword(password);
        
        saveData();
        
        return true;
    }
    /**
     * Edit email
     * @param accountID
     * @param email
     * @return true
     */
    public boolean editAccountEmail(Integer accountID, String email)
    {
    	AccountsSA.get(accountID).setEmail(email);
        
        saveData();
        
    	return true;
    }
    /**
     * Promote account to Admin
     * @param targetAccountID
     * @return true
     */
    public boolean promoteAccount(Integer targetAccountID) 
    {
        Account account = getAccount(targetAccountID);
        
    	Admin admin = new Admin(account.getAccountId(), account.getLoginName(), account.getPassword(), 
    			account.getName(), account.getEmail(), account.getAccountState(), account.getLoginAttempts());
    	
        deleteAccount(targetAccountID);
    	AccountsSA.put(targetAccountID, admin);
        
        saveData();
        
    	return true;
    }
    /**
     * Checks if a login name is unique
     * @param loginName
     * return true if unique false otherwise
     */
    public boolean isLoginNameUnique(String loginName)
    {
        int key = 0;
        Account account;
        for(int i = 0; i < AccountsSA.size(); i++) {
            key = AccountsSA.keyAt(i);
            // get the object by the key.
            account = AccountsSA.get(key);
            if(account.getLoginName().equals(loginName)){
                return false;
            }
        }
        
        return true;
    }
    /**
     * Tests if an account is an admin
     * @param accountID 
     * return true if admin false otherwise
     */
    public boolean isAdmin(Integer accountID)
    {
    	if (getAccount(accountID) instanceof Admin){
    		return true;
        }
        
    	return false;
    }
    
    /**
     * Save accounts
     */
    private void saveData()
    {
        try {
            FileOutputStream fs = FMSApplication.getAppContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream s = new ObjectOutputStream(fs);
            s.writeObject(AccountsSA);
            s.close();
        }
        catch (Exception e) {
            Logger.d(e.getMessage());
        }
    }
    /**
     * Load accounts
     */
    private void loadData()
    {
        try {
            FileInputStream fs = FMSApplication.getAppContext().openFileInput(FILENAME);
            ObjectInputStream s = new ObjectInputStream(fs);
            SparseArray<Account> accounts = (SparseArray<Account>)s.readObject();
            s.close();
            
            AccountsSA = accounts;
        }
        catch (FileNotFoundException e){
            // ignore
        }
        catch (InvalidClassException e){
            // ignore
        }
        catch (Exception e) {
            Logger.d("Could not load Accout Manager data: "+e.getMessage());
        }
    }
}
