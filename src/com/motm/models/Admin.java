package com.motm.models;

public class Admin extends Account
{	
    public Admin(String loginName, String password, String name, String email)
    {
        super(loginName, password, name, email, State.Unlocked, 0);
    }
}