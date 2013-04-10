package com.motm.models;

import java.io.Serializable;

public class Account implements Serializable
{	
    protected static final long serialVersionUID = 1L;
    
    public enum State {
        Locked, Unlocked
    }
    
    protected Integer id;
    protected String name;
    protected String loginName;
    protected String password;
    protected State accountState;
    protected String email;
    protected int loginAttempts;
    
    /**
     * New account
     * @param id
     * @param loginName
     * @param password
     * @param name
     * @param email
     * @param accountState
     * @param loginAttempts
     */
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
    
    /**
     * Get account id
     * @return id
     */
    public int getAccountId()
    {
        return id;
    }
    /**
     * Get account name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Get login name
     * @return login name
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     * Get password
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Get account state
     * @return state
     */
    public State getAccountState() {
        return accountState;
    }
    /**
     * Get email
     * @return email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Get number of login attempts
     * @return loginAttempts
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }
    /**
     * Set account id
     * @param id new id
     * @return true
     */
    public boolean setAccountID(Integer id)
    {
        this.id = id;
        
        return true;
    }
    /**
     * Set name
     * @param name new name
     * @return true
     */
    public boolean setName(String name) {
        this.name = name;
        return true;
    }
    /**
     * Set login name
     * @param loginName
     * @return true
     */
    public boolean setLoginName(String loginName) {
        this.loginName = loginName;
        return true;
    }
    /**
     * Set password
     * @param password
     * @return true
     */
    public boolean setPassword(String password) {
        this.password = password;
        return true;
    }
    /**
     * Set state of account
     * @param accountState
     * @return true
     */
    public boolean setAccountState(State accountState) {
        this.accountState = accountState;
        return true;
    }
    /**
     * Set email for account
     * @param email
     * @return true
     */
    public boolean setEmail(String email) {
        this.email = email;
        return true;
    }
    /**
     * Set login attempts
     * @param loginAttempts
     */
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }   
}
