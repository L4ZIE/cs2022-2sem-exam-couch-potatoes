package pl.models;

import be.Project;
import bll.ProjectManager;
import bll.interfaces.IProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectModel {
    private IProjectManager projectManager;
    public ProjectModel () {
        projectManager = new ProjectManager();
    }
    public ObservableList<Project> getAllProjects() {
        return FXCollections.observableArrayList(projectManager.getAllProjects());
    }
    public void createProject(Project project) {
        projectManager.createProject(project);
    }
    public void editProject(Project project) {
        projectManager.editProject(project);
    }
    public ObservableList<Project> getProjectsForAccount(int accountID){
        return FXCollections.observableArrayList(projectManager.getProjectsForAccount(accountID));
    }
    public ObservableList<String > getPicturesForProject(String refNumber){
        return FXCollections.observableArrayList(projectManager.getPicturesForProject(refNumber));
    }
    public void recordLog(String refNumber, int accountID){
        projectManager.recordLog(refNumber, accountID);
    }
}
