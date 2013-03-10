package com.motm.models.interfaces;

import com.motm.models.Account;

public interface AccountManager
{
    public boolean createAccount(String loginName, String password, String name, String email) throws Exception;

    public Account attemptLogin(String loginName, String password);
    
    public Account getAccount(Integer accountID);
    
    public Account.State getAccountStateByLoginName(String loginName);

    public boolean lockAccount(Integer accountID);

    public boolean unlockAccount(Integer accountID);

    public boolean deleteAccount(Integer accountID);

    public boolean editAccountPassword(Integer accountID, String password);

    public boolean editAccountEmail(Integer accountID, String email);
    
    public boolean isLoginNameUnique(String loginName);

	public int getAccountIdByLoginName(String text);

	public boolean isAdmin(Integer accountID);

	public Account[] getAllAccounts();
}
