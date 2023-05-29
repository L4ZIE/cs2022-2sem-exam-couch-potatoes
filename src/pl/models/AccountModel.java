package pl.models;

import be.Account;
import be.AccountType;
import bll.AccountManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    public void changeAccountName(int id, String name){
        accountManager.changeAccountName(id, name);
    }
    public void changeAccountType(int id, AccountType type){
        accountManager.changeAccountType(id, type);
    }
    public void changePassword(int id, String password){
        accountManager.changePassword(id, password);
    }
    public Account getAccountByName(String name){
        return accountManager.getAccountByName(name);
    }
    public AccountType getAccountTypeByName(String name){
        return accountManager.getAccountTypeByName(name);
    }
    public Account getAccountByID(int id){
        return accountManager.getAccountByID(id);
    }
    public ObservableList<Account> getAllAccounts(){
        return FXCollections.observableArrayList(accountManager.getAllAccounts());    }
    public ObservableList<Account> searchForAccounts(String query){
        return FXCollections.observableArrayList(accountManager.searchForAccounts(query));
    }
    public int getMaxID(){
        return accountManager.getMaxID();
    }
}
