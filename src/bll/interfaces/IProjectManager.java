package bll.interfaces;

import be.Project;
import javafx.collections.ObservableList;

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

    void deleteProject(String refNumber);
    void createPicture(String path, String refNumber);
    void deletePicture(int id);
    int getPictureIDByPath(String path);
    List<Project> searchForProjects(String userSearchInput, String searchOption);
}
