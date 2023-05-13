package pl.models;

import bll.AccountManager;

public class AccountModel {
    AccountManager accountManager = new AccountManager();

    public Boolean checkCredentials(String username, String password) {
        return accountManager.checkCredentials(username, password);
    }

}
