package bll;

import be.Account;
import dal.AccountDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class AccountManager {
    List<Account> accounts;
    AccountDAO accountDAO = AccountDAO.getInstance();

    public AccountManager() {
        accounts = accountDAO.getAllAccounts();
    }

    private Account checkForAccount(String username) {
        for (Account a : accounts) {
            if (a.getName().equals(username))
                return a;
        }
        return null;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private Boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

    public Boolean checkCredentials(String username, String password) {
        if (checkForAccount(username) != null)
            return checkPassword(password, checkForAccount(username).getPassword());
        else
            return false;
    }

}
