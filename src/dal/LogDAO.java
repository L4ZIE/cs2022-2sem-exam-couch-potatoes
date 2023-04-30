package dal;

import be.EditLog;
import dal.connector.dbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogDAO {

    private final dbConnector connector = dbConnector.getInstance();
    private PreparedStatement preparedStatement;
    private static LogDAO instance;

    public static LogDAO getInstance() {
        if (instance == null)
            instance = new LogDAO();
        return instance;
    }

    /**
     * This method records a log in the DB.
     *
     * @param log
     */
    public void recordLog(EditLog log) {
        String sql = "INSERT INTO Log (id, refNumber, accountID, logDate) VALUES (?,?,?,?)";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, log.getId());
            preparedStatement.setString(2, log.getRefNumber());
            preparedStatement.setInt(3, log.getAccountID());
            preparedStatement.setString(4, log.getDate());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO for testing purposes only, remove when project is done (logs must not be removed by hand)
    public void deleteLog(int id) {
        String sql = "DELETE FROM Log WHERE id = ?";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method returns the max ID that's inside the log table.
     *
     * @return Max id inside the Log table.
     */
    public int getMaxID() {
        String sql = "SELECT MAX(id) AS maxID from Log";
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

    public ArrayList<EditLog> getAllLogs() {
        String sql = "SELECT * FROM Log";
        ArrayList<EditLog> logs = new ArrayList<>();

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                logs.add(new EditLog(
                        results.getInt("id"),
                        results.getInt("accountID"),
                        results.getString("refNumber"),
                        results.getString("logDate")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logs;
    }
}
