package pl.models;

import be.Project;
import bll.ProjectManager;
import bll.interfaces.IProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.io.File;

public class ProjectModel {
    private IProjectManager projectManager;
    public ProjectModel () {
        projectManager = new ProjectManager();
    }


    public ObservableList<Project> getAllProjects() {
        return FXCollections.observableArrayList(projectManager.getAllProjects());
    }
    public ObservableList<Project> getProjectsForAccount(int accountID){
        return FXCollections.observableArrayList(projectManager.getProjectsForAccount(accountID));
    }
    public ObservableList<String > getPicturesForProject(String refNumber){
        return FXCollections.observableArrayList(projectManager.getPicturesForProject(refNumber));
    }
    public Image getPictureByPath(String path){
        return new Image(path);
    }


    public void createProject(Project project, int accountID) {
        projectManager.createProject(project, accountID);
    }
    public void editProject(Project project) {
        projectManager.editProject(project);
    }
    public void deleteProject(Project project){
        projectManager.deleteProject(project.getRefNumber());
    }
    public void recordLog(String refNumber, int accountID){
        projectManager.recordLog(refNumber, accountID);
    }
    public void createPicture(String path, String refNumber){
        projectManager.createPicture(path, refNumber);
    }
}
