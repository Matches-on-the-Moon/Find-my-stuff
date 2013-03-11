package com.motm.models;

import java.io.Serializable;

public class Admin extends Account implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public Admin(int id, String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        super(id, loginName, password, name, email, accountState, loginAttempts);
    }
}
