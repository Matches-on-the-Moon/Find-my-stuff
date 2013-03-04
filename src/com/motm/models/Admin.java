/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models;

/**
 *
 * @author William
 */
public class Admin extends Account
{
    public Admin(String loginName, String password, String name, String email, State accountState, int loginAttempts)
    {
        super(loginName, password, name, email, accountState, loginAttempts);
    }
}
