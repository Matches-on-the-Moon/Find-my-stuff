package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class FileAccountManager implements AccountManager
{
    private static final String FILENAME = "accountHM";
    
    private static HashMap<Integer, Account> accountHM;// Key = ID, Value = Account

    public FileAccountManager()
    {
        if(accountHM == null){
            accountHM = new HashMap<Integer, Account>();
        }
        
        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)){
            preferences.edit().putBoolean("firstRun", false).commit();
            accountHM.put(0, new Admin(0, "admin", "admin", "administrator", "FMS_Admin@gatech.edu", Account.State.Unlocked, 0));
            accountHM.put(1, new Account(1, "user", "user", "user", "FMS_User@gatech.edu", Account.State.Unlocked, 0));
            accountHM.put(2, new Account(2, "a", "a", "a", "a@a.com", Account.State.Unlocked, 0));
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
        Set<Integer> accountIDs = accountHM.keySet();
        if(accountIDs.size() >= Integer.MAX_VALUE){
            String message = "Can't create a new account, the max amount of accounts have been created";
            Logger.d(message);
            throw new FMSException(message);
        }
        Integer id = 1;
        if(accountIDs.size() > 0){
            id = Collections.max(accountIDs) + 1;
        }
        // keep incrementing the id until the set doesn't contain it
        while(accountIDs.contains(id)){
            id++;
        }
        
        // create a new account
        Account account = new Account(id, loginName, password, name, email, Account.State.Unlocked, 0);
        accountHM.put(id, account);
        
        saveData();
        return true;
    }
    /**
     * Attempt login
     * @param loginName 
	 * @param password 
     */
    public Account attemptLogin(String loginName, String password)
    {
        Account account = null;
        
        // find an account with the loginName
        for(Account _account : accountHM.values()){
            if(_account.getLoginName().equals(loginName)) {
                account = _account;
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
        for(Account account : accountHM.values()) {
            if(account.getLoginName().equals(loginName)){
                return account.getAccountState();
            }
        }
        
        // if the account is not found, say it's unlocked
        return Account.State.Unlocked;
    }
    /**
     * Get the account for loginName
     * @param loginName
     */
    public int getAccountIdByLoginName(String loginName) {
        for(Account account : accountHM.values()) {
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
        return accountHM.get(accountID);
        
    }
    /**
     * Get all accounts
     * @return all accounts
     */
    public ArrayList<Account> getAllAccounts() {
    	ArrayList<Account> accounts = new ArrayList<Account>(accountHM.values());
        
		return accounts;
    }

    /**
     * Lock a user's account
     * @param accountID id
     * @return true
     */
    public boolean lockAccount(Integer accountID)
    {
    	accountHM.get(accountID).setAccountState(Account.State.Locked);
        
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
    	accountHM.get(accountID).setAccountState(Account.State.Unlocked);
        
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
    	accountHM.remove(accountID);
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
    	accountHM.get(accountID).setPassword(password);
        
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
    	accountHM.get(accountID).setEmail(email);
        
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
    	accountHM.put(targetAccountID, admin);
        
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
        for(Account account : accountHM.values()) {
            if(account.getLoginName().equals(loginName))
                return false;
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
            s.writeObject(accountHM);
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
            HashMap<Integer, Account> accounts = (HashMap<Integer, Account>)s.readObject();
            s.close();
            
            accountHM = accounts;
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
