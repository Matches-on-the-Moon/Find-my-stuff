package com.motm.models;

public class Admin extends Account
<<<<<<< HEAD
{
    public Admin(String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        super(loginName, password, name, email, accountState, loginAttempts);
=======
{	
    public Admin(String loginName, String password, String name, String email)
    {
        super(loginName, password, name, email, State.Unlocked, 0);
>>>>>>> 38e022dc8f84ba5d89cdd2f1cf3c8f2b267a397f
    }
}
