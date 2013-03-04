package com.motm.models;

public class Admin extends Account
{
    public Admin(String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        super(loginName, password, name, email, accountState, loginAttempts);
    }
}
