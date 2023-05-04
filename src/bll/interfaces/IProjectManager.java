package bll.interfaces;

import be.Project;

import java.util.List;

public interface IProjectManager {
    List<Project> getAllProjects();

    void createProject(Project project);

    void editProject(Project project);
    List<Project> getProjectsForAccount(int accountID);
    List<String> getPicturesForProject(String refNumber);
    void recordLog(String refNumber, int accountID);
}
