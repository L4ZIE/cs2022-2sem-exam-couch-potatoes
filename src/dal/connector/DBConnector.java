package dal.connector;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector {
    private static final String file = "resources/sqllogin.properties";

    private SQLServerDataSource dataSource = null;

    private static DBConnector instance;

    public static DBConnector getInstance(){
        if(instance==null){
            instance = new DBConnector();
        }
        return instance;
    }

    private DBConnector(){
        Properties properties = getConnectionDetails();
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(properties.getProperty("name"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setServerName(properties.getProperty("server"));
        dataSource.setPortNumber(Integer.parseInt(properties.getProperty("port")));
        dataSource.setTrustServerCertificate(true);
    }
    public Connection createConnection()throws SQLServerException {
        return dataSource.getConnection();
    }

    private Properties getConnectionDetails() {
        Properties properties = new Properties();

        try {
            FileInputStream sr = new FileInputStream(file);
            properties.load(sr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
