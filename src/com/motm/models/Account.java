/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models;

/**
 *
 * @author William
 */
public class Account {
    private String name;
    private String loginName;
    private String password;
    private boolean accountState;
    private String email;
    private int loginAttempts;
    
    // creates the Account using the user's login name, password, name, and email.
    public Account(String loginName, String password, String name, String email) {
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.email = email;
        accountState = true;
        loginAttempts = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLoginName() {
        return loginName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean accountState() {
        return accountState;
    }
    
    public String getEmail() {
        return email;
    }
    
    public int getLoginAttempts() {
        return loginAttempts;
    }
    
    public boolean setName(String name) {
        this.name = name;
        return true;
    }
    
    public boolean setLoginName(String loginName) {
        this.loginName = loginName;
        return true;
    }
    
    public boolean setPassword(String password) {
        this.password = password;
        return true;
    }
    
    public boolean setAccountState(boolean accountState) {
        this.accountState = accountState;
        return true;
    }
    
    public boolean setEmail(String email) {
        this.email = email;
        return true;
    }
    
    public boolean setLoginAttempts() {
        loginAttempts++;
        return true;
    }
    
        
        
}
