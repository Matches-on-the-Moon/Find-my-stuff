/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.motm.models.interfaces;

import com.motm.models.Account;

/**
 *
 * @author michael
 */
public interface AccountManager
{
    public boolean createAccount(String loginName, String password, String name, String email);

    public Account getAccount(String loginName, String password);

    public boolean lockAccount(String loginName);

    public boolean unlockAccount(String loginName);

    public boolean deleteAccount(String loginName);

    public boolean editAccountPassword(String loginName, String password);

    public boolean editAccountEmail(String loginName, String email);
}
