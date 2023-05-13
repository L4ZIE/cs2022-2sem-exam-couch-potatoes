package dal.interfaces;

import be.Account;

import java.util.List;

public interface IAccountDAO {
    List<Account> getAllAccounts();

    void createAccount(Account account);

    void changePassword(int id, String newPassword);

    void deleteAccount(int id);
    int getMaxID();
}
