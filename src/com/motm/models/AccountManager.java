/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models;

/**
 *
 * @author michael
 */
public class AccountManager
{
    private Account currentAccount;
    //  Logger logger;
    // DataManager dataManager;
            
    public boolean createAccount(String loginName, String password, String name, String email)
    {
        //checks if loginName is already in use, then if it is valid
        //check if password is valid
        //checks if email is valid
        //creates new account, sets currentAccount to said account (unless Admin can create them - then no!)
        return true;
    }
    
    public Account getCurrentAccount() {
        return currentAccount;
    }
    
    public boolean setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
        return true;
    }
    
    public boolean checkLogin (String loginName, String password) {
        return true;
    }
    
    public boolean lockAccount (String loginName) {
        
        return true;
    }
    
    public boolean unlockAccount (String loginName) {
        return true;
    }
    
    public boolean deleteAccount (String loginName) {
        return true;
    }
    
    public boolean editAccount (String loginName) {
        return true;
    }
    
    
}
