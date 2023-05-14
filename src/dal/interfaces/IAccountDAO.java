package dal.interfaces;

import be.Account;

import java.util.List;

public interface IAccountDAO {
    List<Account> getAllAccounts();

    void createAccount(Account account);

    void changePassword(int id, String newPassword);

    void deleteAccount(int id);
    void changeType(int id, int type);
    void changeAccountName(int id, String name);
    int getMaxID();
}
