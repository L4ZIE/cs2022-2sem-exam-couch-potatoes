package bll.interfaces;

import be.Account;
import be.Devices;
import be.EditLog;
import be.Project;

import java.util.List;

public interface IProjectManager {
    List<Project> getAllProjects();
    List<Project> getPrivateProjects();
    List<Project> getPublicProjects();

    void createProject(Project project, int accountID);

    void editProject(Project project);
    List<Project> getProjectsForAccount(int accountID);
    List<String> getPicturesForProject(String refNumber);
    List<Devices> getAllDevicesForProject(String refNumber);
    List<Devices> getAllDevices();
    List<Integer> getAllAccountsForProject(String refNumber);
    void recordLog(String refNumber, int accountID);
    Project getProjectByRefNumber(String refNumber);
    void deleteProject(String refNumber);
    void createPicture(String path, String refNumber);
    void deletePicture(int id);
    int getPictureIDByPath(String path);
    List<Project> searchForProjects(String userSearchInput, String searchOption);

    void createDevice(Devices devices);
    void removeDevice(int id);
    int  getMaxIdForDevice();
    List<EditLog> getAllLogsForProject(String refNumber);
    String  getLastLogForProject(String refNumber);
    Devices getDeviceByName(String name);

}
