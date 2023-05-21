package bll.interfaces;

import be.Account;
import be.AccountType;

import java.util.List;

public interface IAccountManager {
    Boolean checkCredentials(String username, String password);
    Account getAccountByName(String name);
    Account getAccountByID(int id);
    AccountType getAccountTypeByName(String name);
    List<Account> getAllAccounts();
    int getMaxID();
    void deleteAccount(int id);
    void createAccount(Account account);
    void changePassword(int id, String password);
    void changeAccountType(int id, AccountType type);
    void changeAccountName(int id, String name);

}
