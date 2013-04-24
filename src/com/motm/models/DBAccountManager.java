package com.motm.models;

import android.content.Context;
import android.content.SharedPreferences;
import com.motm.application.FMSApplication;
import com.motm.helpers.FMSException;
import com.motm.helpers.Logger;
import com.motm.models.interfaces.AccountManager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

final public class DBAccountManager extends APIManager implements AccountManager
{
    Account nullAccount;
    
    public DBAccountManager()
    {
        //setBaseURL("http://gtpkt.org/cs2340api/item/");
        setBaseURL("http://10.0.2.2/cs2340api/index.php/account/");
        
        nullAccount = new Account(0, "", "", "", "", Account.State.LOCKED, 0);
        
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
     *  @param loginName loginName
	 *  @param password password
	 *  @param name name
	 *  @param email email
	 *  @return true if account created
     */
    public boolean createAccount(String loginName, String password, String name, String email) throws FMSException
    {
        JSONObject result = put("", createParams(
                "loginName", loginName,
                "password", password,
                "name", name,
                "email", email
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("createAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
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
        JSONObject result = get("attemptLogin", createParams(
                "loginName", loginName,
                "password", password
                ));
        
        try {
            if(!result.getBoolean("success")){
                return nullAccount;
            }
            
            // get the result array
            JSONObject jsonObject = result.getJSONObject("result");
            
            if(jsonObject.getString("isAdmin").equals("1")){
                // admin
                Admin admin = new Admin(
                        jsonObject.getInt("id"),
                        jsonObject.getString("loginName"),
                        jsonObject.getString("password"),
                        jsonObject.getString("name"),
                        jsonObject.getString("email"),
                        Account.State.valueOf(jsonObject.getString("accountState")),
                        jsonObject.getInt("id"));
                
                return admin;

            } else {
                // account, non-admin
                Account account = new Account(
                        jsonObject.getInt("id"),
                        jsonObject.getString("loginName"),
                        jsonObject.getString("password"),
                        jsonObject.getString("name"),
                        jsonObject.getString("email"),
                        Account.State.valueOf(jsonObject.getString("accountState")),
                        jsonObject.getInt("id"));
                
                return account;
            }
            
        }
        catch(Exception e){
            Logger.d("attemptLogin unexpected result from JSON, "+e.getMessage());
            return nullAccount;
        }
    }
    
    /**
     * Get account information for a user
     * @param loginName 
     * @return account state
     */
    public Account.State getAccountStateByLoginName(String loginName)
    {
        JSONObject result = get("", createParams(
                "loginName", loginName
                ));
        
        try {
            if(!result.getBoolean("success")){
                return Account.State.UNLOCKED;
            }
            
            // get the result array
            JSONObject jsonObject = result.getJSONObject("result");
            
            return Account.State.valueOf(jsonObject.getString("state"));
        }
        catch(Exception e){
            Logger.d("getAccountStateByLoginName unexpected result from JSON, "+e.getMessage());
            return Account.State.UNLOCKED;
        }
    }
    
    /**
     * Get the account for loginName
     * @param loginName
     * @return accountId
     */
    public int getAccountIdByLoginName(String loginName)
    {
        JSONObject result = get("idByLoginName", createParams(
                "loginName", loginName
                ));
        
        try {
            if(!result.getBoolean("success")){
                return 0;
            }
            
            // get the result array
            JSONObject jsonObject = result.getJSONObject("result");
            
            return jsonObject.getInt("id");
        }
        catch(Exception e){
            Logger.d("getAccountIdByLoginName unexpected result from JSON, "+e.getMessage());
            return 0;
        }
    }
    
	/**
	 * Retrieve account
	 * @param accountID id of account
	 * @return account
	 */
    public Account getAccount(Integer accountID)
    {
        JSONObject result = get("", createParams(
                "id", accountID.toString()
                ));
        
        try {
            if(!result.getBoolean("success")){
                return nullAccount;
            }
            
            // get the result array
            JSONObject jsonObject = result.getJSONObject("result");
            
            if(jsonObject.getString("isAdmin").equals("1")){
                // admin
                Admin admin = new Admin(
                        jsonObject.getInt("id"),
                        jsonObject.getString("loginName"),
                        jsonObject.getString("password"),
                        jsonObject.getString("name"),
                        jsonObject.getString("email"),
                        Account.State.valueOf(jsonObject.getString("accountState")),
                        jsonObject.getInt("id"));
                
                return admin;

            } else {
                // account, non-admin
                Account account = new Account(
                        jsonObject.getInt("id"),
                        jsonObject.getString("loginName"),
                        jsonObject.getString("password"),
                        jsonObject.getString("name"),
                        jsonObject.getString("email"),
                        Account.State.valueOf(jsonObject.getString("accountState")),
                        jsonObject.getInt("id"));
                
                return account;
            }
            
        }
        catch(Exception e){
            Logger.d("getAccount unexpected result from JSON, "+e.getMessage());
            return nullAccount;
        }
    }
    
    /**
     * Get all accounts
     * @return all accounts
     */
    public List<Account> getAllAccounts()
    {
        JSONObject result = get("all", null);
        
        ArrayList nullList = new ArrayList<Account>();
        nullList.add(nullAccount);
        
        try {
            if(!result.getBoolean("success")){
                return nullList;
            }
            
            // get the result array
            JSONArray accountsJSONArray = result.getJSONArray("result");
            
            ArrayList<Account> accounts = new ArrayList<Account>();
            
            // make items from the results
            for(int i = 0; i < accountsJSONArray.length(); i++){
                // catch for each item, so I can ignore the ones with errors
                try {
                    JSONObject jsonObject = accountsJSONArray.getJSONObject(i);
                    
                    if(jsonObject.getString("isAdmin").equals("1")){
                        // admin
                        Admin admin = new Admin(
                                jsonObject.getInt("id"),
                                jsonObject.getString("loginName"),
                                jsonObject.getString("password"),
                                jsonObject.getString("name"),
                                jsonObject.getString("email"),
                                Account.State.valueOf(jsonObject.getString("accountState")),
                                jsonObject.getInt("id"));

                        accounts.add(admin);

                    } else {
                        // account, non-admin
                        Account account = new Account(
                                jsonObject.getInt("id"),
                                jsonObject.getString("loginName"),
                                jsonObject.getString("password"),
                                jsonObject.getString("name"),
                                jsonObject.getString("email"),
                                Account.State.valueOf(jsonObject.getString("accountState")),
                                jsonObject.getInt("id"));

                        accounts.add(account);
                    }
                }
                catch(Exception e){
                    // I don't care, continue
                }
            
            }
            
            return accounts;
            
        }
        catch(Exception e){
            Logger.d("getAllAccounts unexpected result from JSON, "+e.getMessage());
            return nullList;
        }
    }

    /**
     * Lock a user's account
     * @param accountID id
     * @return true
     */
    public boolean lockAccount(Integer accountID)
    {
    	JSONObject result = post("lock", createParams(
                "id", accountID.toString()
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("lockAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
        return true;
    }

    /**
     * Unlock account
     * @param accountID id of account
     * @return true
     */
    public boolean unlockAccount(Integer accountID)
    {
    	JSONObject result = post("unlock", createParams(
                "id", accountID.toString()
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("unlockAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
        return true;
    }
    
    /**
     * Delete account
     * @param accountID id
     * @return true
     */
    public boolean deleteAccount(Integer accountID)
    {
    	JSONObject result = post("delete", createParams(
                "id", accountID.toString()
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("deleteAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
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
    	JSONObject result = post("unlock", createParams(
                "id", accountID.toString(),
                "password", password
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("editAccountPassword unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
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
    	JSONObject result = post("unlock", createParams(
                "id", accountID.toString(),
                "email", email
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("editAccountEmail unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
        return true;
    }
    
    /**
     * Promote account to Admin
     * @param targetAccountID
     * @return true
     */
    public boolean promoteAccount(Integer targetAccountID) 
    {
        JSONObject result = post("promote", createParams(
                "id", targetAccountID.toString()
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
        }
        catch(Exception e){
            Logger.d("promoteAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
        
        return true;
    }
    /**
     * Checks if a login name is unique
     * @param loginName
     * return true if unique false otherwise
     */
    public boolean isLoginNameUnique(String loginName)
    {
        JSONObject result = get("isLoginNameUnique", createParams(
                "loginName", loginName
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
            
            JSONObject jsonObject = result.getJSONObject("result");
            
            if(jsonObject.getString("unique").equals("yes")){
                return true;
            }
            
            return false;
        }
        catch(Exception e){
            Logger.d("promoteAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
    }
    
    /**
     * Tests if an account is an admin
     * @param accountID 
     * @return true if admin false otherwise
     */
    public boolean isAdmin(Integer accountID)
    {
    	JSONObject result = get("isAdmin", createParams(
                "id", accountID.toString()
                ));
        
        try {
            if(!result.getBoolean("success")){
                return false;
            }
            
            JSONObject jsonObject = result.getJSONObject("result");
            
            if(jsonObject.getString("isAdmin").equals("yes")){
                return true;
            }
            
            return false;
        }
        catch(Exception e){
            Logger.d("promoteAccount unexpected result from JSON, "+e.getMessage());
            return false;
        }
    }
}
