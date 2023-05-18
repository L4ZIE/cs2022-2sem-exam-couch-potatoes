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

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
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

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}

