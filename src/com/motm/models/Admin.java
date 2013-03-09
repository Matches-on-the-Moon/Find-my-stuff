package com.motm.models;

public class Admin extends Account
{
    public Admin(int id, String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        super(id, loginName, password, name, email, accountState, loginAttempts);
    }
    
    public Admin(String loginName, String password, String name, String email)
    {
    	super(loginName, password, name, email);
    }
}