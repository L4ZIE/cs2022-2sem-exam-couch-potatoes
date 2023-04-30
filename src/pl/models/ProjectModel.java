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
    public void editProject(Project selectedProject) {
        projectManager.editProject(selectedProject);
    }
}
