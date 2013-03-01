/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models;

/**
 *
 * @author William
 */
public class Account
{
    public enum State {
        Locked, Unlocked
    }
    
    private String name;
    private String loginName;
    private String password;
    private State accountState;
    private String email;
    private int loginAttempts;
    
    // creates the Account using the user's login name, password, name, and email.
    public Account(String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountState = accountState;
        this.loginAttempts = loginAttempts;
    }
    
    // convience constructor
    public static Account newAccount(String loginName, String password, String name, String email)
    {
        return new Account(loginName, password, name, email, State.Unlocked, 0);
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
    
    public State getAccountState() {
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
    
    public boolean setAccountState(State accountState) {
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
