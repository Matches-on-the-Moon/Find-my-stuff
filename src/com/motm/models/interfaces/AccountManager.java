package com.motm.models.interfaces;

import java.util.List;

import com.motm.helpers.FMSException;
import com.motm.models.Account;

public interface AccountManager
{
     boolean createAccount(String loginName, String password, String name, String email) throws FMSException;

     Account attemptLogin(String loginName, String password);
    
     Account getAccount(Integer accountID);
    
     Account.State getAccountStateByLoginName(String loginName);

     boolean lockAccount(Integer accountID);

     boolean unlockAccount(Integer accountID);

     boolean deleteAccount(Integer accountID);

     boolean editAccountPassword(Integer accountID, String password);

     boolean editAccountEmail(Integer accountID, String email);
    
     boolean isLoginNameUnique(String loginName);

	 int getAccountIdByLoginName(String text);

	 boolean isAdmin(Integer accountID);

	 List<Account> getAllAccounts();

	 boolean promoteAccount(Integer targetAccountID);
}
