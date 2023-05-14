package bll.interfaces;

import be.Account;
import be.AccountType;

public interface IAccountManager {
    Boolean checkCredentials(String username, String password);
    Account getAccountByName(String name);
    AccountType getAccountTypeByName(String name);
    int getMaxID();
    void deleteAccount(int id);
    void createAccount(Account account);

}
