package bll.interfaces;

import be.Account;
import be.Devices;
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
    void recordLog(String refNumber, int accountID);
    Project getProjectByRefNumber(String refNumber);
    void deleteProject(String refNumber);
    void createPicture(String path, String refNumber);
    void deletePicture(int id);
    int getPictureIDByPath(String path);
    List<Project> searchForProjects(String userSearchInput, String searchOption);

    void createDevice(Devices devices);
    int  getMaxIdForDevice();

}
