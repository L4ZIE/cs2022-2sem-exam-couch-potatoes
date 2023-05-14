package pl.models;

import be.Account;
import be.AccountType;
import bll.AccountManager;

public class AccountModel {
    AccountManager accountManager = new AccountManager();

    public Boolean checkCredentials(String username, String password) {
        return accountManager.checkCredentials(username, password);
    }
    public void createAccount(Account account){
        accountManager.createAccount(account);
    }
    public void deleteAccount(int id){
        accountManager.deleteAccount(id);
    }
    public Account getAccountByName(String name){
        return accountManager.getAccountByName(name);
    }
    public AccountType getAccountTypeByName(String name){
        return accountManager.getAccountTypeByName(name);
    }

}
