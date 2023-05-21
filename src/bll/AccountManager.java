package bll;

import be.Account;
import be.AccountType;
import bll.interfaces.IAccountManager;
import dal.AccountDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

public class AccountManager implements IAccountManager {
    List<Account> accounts;
    AccountDAO accountDAO = AccountDAO.getInstance();

    public AccountManager() {
        fillAccounts();
    }
    private void fillAccounts(){
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
            return checkPassword(password, Objects.requireNonNull(checkForAccount(username)).getPassword());
        else
            return false;
    }

    @Override
    public Account getAccountByName(String name) {
        for (Account a : accounts) {
            if(a.getName().equals(name))
                return a;
        }
        return null;
    }

    @Override
    public Account getAccountByID(int id) {
        for (Account a : accounts) {
            if(a.getId() == id)
                return a;
        }
        return null;
    }

    @Override
    public AccountType getAccountTypeByName(String name) {
        for (Account a : accounts) {
            if(a.getName().equals(name))
                return a.getType();
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public int getMaxID() {
        return accountDAO.getMaxID();
    }

    @Override
    public void deleteAccount(int id) {
        accounts.remove(getAccountByID(id));
        accountDAO.deleteAccount(id);
    }

    @Override
    public void createAccount(Account account) {
        account.setPassword(hashPassword(account.getPassword()));
        accountDAO.createAccount(account);
        accounts.add(account);
    }

    @Override
    public void changePassword(int id, String password) {
        accountDAO.changePassword(id, password);

        Account changedAccount = getAccountByID(id);
        accounts.remove(changedAccount);
        changedAccount.setPassword(hashPassword(password));
        accounts.add(changedAccount);
    }

    @Override
    public void changeAccountType(int id, AccountType type) {
        accountDAO.changeType(id, type.getValue());

        Account changedAccount = getAccountByID(id);
        accounts.remove(changedAccount);
        changedAccount.setType(type);
        accounts.add(changedAccount);
    }

    @Override
    public void changeAccountName(int id, String name) {
        accountDAO.changeAccountName(id, name);

        Account changedAccount = getAccountByID(id);
        accounts.remove(changedAccount);
        changedAccount.setName(name);
        accounts.add(changedAccount);
    }
}
