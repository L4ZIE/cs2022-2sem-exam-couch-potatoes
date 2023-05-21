package dal.interfaces;

import be.EditLog;

import java.util.ArrayList;

public interface ILogDAO {
    void recordLog(EditLog log);
    void deleteLog(int id);
    int getMaxID();
    ArrayList<EditLog> getAllLogs();
    String getMaxDateForProject(String refNumber);
}
