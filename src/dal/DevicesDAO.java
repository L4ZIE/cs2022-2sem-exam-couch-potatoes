package dal;

import be.Devices;
import be.Project;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.connector.DBConnector;
import dal.interfaces.IDevicesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevicesDAO implements IDevicesDAO {
    private PreparedStatement preparedStatement;
    private final DBConnector connector = DBConnector.getInstance();

    @Override
    public void createDevice(Devices devices) {
        String sql = "INSERT INTO Devices (id, deviceName, uName, uPassword, refNumber ) VALUES (?,?,?,?,?)";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);

            preparedStatement.setInt(1, devices.getId());
            preparedStatement.setString(2, devices.getDeviceName());
            preparedStatement.setString(3, devices.getUName());
            preparedStatement.setString(4, devices.getUPassword());
            preparedStatement.setString(5, devices.getRefNumber());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeDevice(int id) {
        String sql = "DELETE FROM Devices WHERE id = ?";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getMaxIdForDevice() {
        String sql = "SELECT MAX(id) AS id FROM Devices";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<Devices> getAllDevices() {
        List<Devices> result = new ArrayList<>();
        String sql = "SELECT * FROM Devices";
        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new Devices(
                        rs.getInt("id"),
                        rs.getString("deviceName"),
                        rs.getString("uName"),
                        rs.getString("uPassword"),
                        rs.getString("refNumber")
                        ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

