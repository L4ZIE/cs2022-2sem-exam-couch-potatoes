package dal;

import be.Account;
import be.AccountType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.connector.DBConnector;
import dal.interfaces.IAccountDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccountDAO {
    private final DBConnector connector = DBConnector.getInstance();
    private PreparedStatement preparedStatement;
    private static AccountDAO instance;

    public static AccountDAO getInstance() {
        if (instance == null)
            instance = new AccountDAO();
        return instance;
    }


    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Accounts";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accounts.add(new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("uName"),
                        resultSet.getString("uPassword"),
                        AccountType.fromValue(resultSet.getInt("uType"))
                ));
            }
            return accounts;

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createAccount(Account account) {
        String sql = "INSERT INTO Accounts (id, uName, uPassword, uType) VALUES (?,?,?,?)";
        try {

            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, account.getName());
            preparedStatement.setString(3, account.getPassword());
            preparedStatement.setInt(4, account.getType().getValue());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changePassword(int id, String newPassword) {
        String sql = "UPDATE Accounts uPassword = ? WHERE id = ?";
        try {

            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void changeType(int id, int type) {
        String sql = "UPDATE Accounts uType = ? WHERE id = ?";
        try {

            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, type);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void changeAccountName(int id, String name) {
        String sql = "UPDATE Accounts uName = ? WHERE id = ?";
        try {

            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(int id) {
        String sql = "DELETE FROM Accounts WHERE id = ?";
        try {
            Connection connection = connector.createConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getMaxID() {
        String sql = "SELECT MAX(id) AS maxID from Accounts";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next())
                return result.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }
}
