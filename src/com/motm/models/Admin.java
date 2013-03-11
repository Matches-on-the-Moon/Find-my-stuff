package com.motm.models;

public class Admin extends Account
{
    public Admin(int id, String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        super(id, loginName, password, name, email, accountState, loginAttempts);
    }
    
    public Admin(Account account) {
    	super(account.getAccountId(), account.getLoginName(), account.getPassword(), 
    			account.getName(), account.getEmail(), account.getAccountState(), account.getLoginAttempts());
    }
}
