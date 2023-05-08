package dal;

import dal.connector.DBConnector;
import dal.interfaces.IProjectAccountDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectAccountDAO implements IProjectAccountDAO {

    private PreparedStatement preparedStatement;
    private final DBConnector connector = DBConnector.getInstance();

    @Override
    public List<String> getAllProjectsForAccount(int accountID) {
        List<String> results = new ArrayList<>();

        String sql = "SELECT * FROM Project_Account WHERE accountID = ?";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                results.add(rs.getString("refNumber"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    @Override
    public List<Integer> getAllAccountsForProject(String refNumber) {
        List<Integer> results = new ArrayList<>();

        String sql = "SELECT * FROM Project_Account WHERE refNumber = ?";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setString(1, refNumber);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                results.add(rs.getInt("accountID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }
    @Override
    public int getMaxID(){
        int result = 0;

        String sql = "SELECT MAX(id) AS maxID FROM Project_Account";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
                result = rs.getInt("maxID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    @Override
    public void saveProject(String refNumber, int accountID, int id) {
        String sql = "INSERT INTO Project_Account (id, refNumber, accountID) VALUES (?,?,?)";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, refNumber);
            preparedStatement.setInt(3, accountID);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteConnection(String refNumber, int accountID) {
        String sql = "DELETE FROM Project_Account WHERE accountID = ? AND refNumber = ?";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            preparedStatement.setString(2, refNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
