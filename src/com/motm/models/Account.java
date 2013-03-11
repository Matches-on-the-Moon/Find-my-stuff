package com.motm.models;

import java.io.Serializable;

public class Account implements Serializable
{	
    private static final long serialVersionUID = 1L;
    
    public enum State {
        Locked, Unlocked
    }
    
    private Integer id;
    private String name;
    private String loginName;
    private String password;
    private State accountState;
    private String email;
    private int loginAttempts;
    
    public Account(Integer id, String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountState = accountState;
        this.loginAttempts = loginAttempts;
    }
    
    public int getAccountId()
    {
        return id;
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
    
    public boolean setAccountID(Integer id)
    {
        this.id = id;
        
        return true;
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
    
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }   
}
