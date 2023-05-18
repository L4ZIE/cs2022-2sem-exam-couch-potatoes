package bll.interfaces;

import be.Account;
import be.AccountType;

public interface IAccountManager {
    Boolean checkCredentials(String username, String password);
    Account getAccountByName(String name);
    Account getAccountByID(int id);
    AccountType getAccountTypeByName(String name);
    int getMaxID();
    void deleteAccount(int id);
    void createAccount(Account account);
    void changePassword(int id, String password);
    void changeAccountType(int id, AccountType type);
    void changeAccountName(int id, String name);

}
