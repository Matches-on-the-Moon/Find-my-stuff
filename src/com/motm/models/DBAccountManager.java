package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.motm.application.FMSApplication;
import com.motm.helpers.FMSException;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.AccountManager;
import java.util.List;

final public class DBAccountManager extends APIManager implements AccountManager
{
    public DBAccountManager()
    {
        // first run
        SharedPreferences preferences = FMSApplication.getAppContext().getSharedPreferences("DBAccount", Context.MODE_PRIVATE);
        if(preferences.getBoolean("firstRun", true)){
            preferences.edit().putBoolean("firstRun", false).commit();
            try {
                createAccount("admin", "admin", "administrator", "FMS_Admin@gatech.edu");
                promoteAccount(getAccountIdByLoginName("admin"));
                createAccount("user", "user", "user", "FMS_User@gatech.edu");
                createAccount("a", "a", "a", "a@a.com");
            }
            catch(Exception e){
                Logger.d("Could not create firstRun accounts, exception: "+e.getMessage());
            }
        }
    }
    
    /**
     *  Creates a new account
     *  For the FileAccountManager, it needs to also choose a unique id for the Account
     *  @param loginName loginName
	 *  @param password password
	 *  @param name name
	 *  @param email email
	 *  @return true if account created
     */
    public boolean createAccount(String loginName, String password, String name, String email) throws FMSException
    {
        return false;
    }
    
    /**
     * Attempt login
     * @param loginName 
	 * @param password 
	 * @return account
     */
    public Account attemptLogin(String loginName, String password)
    {
        return null;
    }
    
    /**
     * Get account information for a user
     * @param loginName 
     * @return account state
     */
    public Account.State getAccountStateByLoginName(String loginName)
    {
        return null;
    }
    
    /**
     * Get the account for loginName
     * @param loginName
     * @return accountId
     */
    public int getAccountIdByLoginName(String loginName)
    {
        return 0;
    }
    
	/**
	 * Retrieve account
	 * @param accountID id of account
	 * @return account
	 */
    public Account getAccount(Integer accountID)
    {
        return null;
    }
    
    /**
     * Get all accounts
     * @return all accounts
     */
    public List<Account> getAllAccounts()
    {
        return null;
    }

    /**
     * Lock a user's account
     * @param accountID id
     * @return true
     */
    public boolean lockAccount(Integer accountID)
    {
    	return true;
    }

    /**
     * Unlock account
     * @param accountID id of account
     * @return true
     */
    public boolean unlockAccount(Integer accountID)
    {
    	return true;
    }
    
    /**
     * Delete account
     * @param accountID id
     * @return true
     */
    public boolean deleteAccount(Integer accountID)
    {
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
    	return true;
    }
    
    /**
     * Promote account to Admin
     * @param targetAccountID
     * @return true
     */
    public boolean promoteAccount(Integer targetAccountID) 
    {
        return true;
    }
    /**
     * Checks if a login name is unique
     * @param loginName
     * return true if unique false otherwise
     */
    public boolean isLoginNameUnique(String loginName)
    {
        return true;
    }
    
    /**
     * Tests if an account is an admin
     * @param accountID 
     * @return true if admin false otherwise
     */
    public boolean isAdmin(Integer accountID)
    {
    	return false;
    }
}
