package dal;

import dal.connector.DBConnector;
import dal.interfaces.IPictureDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PictureDAO implements IPictureDAO {
    private final DBConnector connector = DBConnector.getInstance();
    private PreparedStatement preparedStatement;
    private static PictureDAO instance;

    public static PictureDAO getInstance() {
        if (instance == null)
            instance = new PictureDAO();
        return instance;
    }


    @Override
    public void addPicture(int id, String location, String refNumber) {
        String sql = "INSERT INTO Pictures (id, pLocation, refNumber) VALUES (?,?,?)";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, location);
            preparedStatement.setString(3, refNumber);


            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePicture(int id) {
        String sql = "DELETE FROM Pictures WHERE id = ?";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPicture(String path, String refNumber, int id) {
        String sql = "INSERT INTO Pictures (id, pLocation, refNumber) VALUES (?,?,?)";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, path);
            preparedStatement.setString(3, refNumber);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method returns the max ID that's inside the Pictures table.
     *
     * @return Max id inside the Pictures table.
     */
    @Override
    public int getMaxID() {
        String sql = "SELECT MAX(id) AS maxID from Pictures";
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

    @Override
    public int getPictureIDByPath(String path) {
        String sql = "SELECT id FROM Pictures WHERE pLocation = ?";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setString(1, path);

            ResultSet result = preparedStatement.executeQuery();
            if (result.next())
                return result.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public ArrayList<String> getAllPicturesForProject(String refNumber) {
        ArrayList<String> pictures = new ArrayList<>();
        String sql = "SELECT * FROM Pictures WHERE refNumber = ?";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setString(1, refNumber);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                pictures.add(results.getString("pLocation"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pictures;
    }

}
