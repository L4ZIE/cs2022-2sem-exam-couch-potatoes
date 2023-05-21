package pl.models;


import be.Devices;
import be.EditLog;
import be.Project;
import bll.ProjectManager;
import bll.interfaces.IProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.List;


public class ProjectModel {
    private final IProjectManager projectManager;

    public ProjectModel() {
        projectManager = new ProjectManager();
    }

    public Project getProjectByRefNumber(String refNumber) {
        return projectManager.getProjectByRefNumber(refNumber);
    }

    public ObservableList<Project> getAllProjects() {
        return FXCollections.observableArrayList(projectManager.getAllProjects());
    }

    public ObservableList<Project> getPrivateProjects() {
        return FXCollections.observableArrayList(projectManager.getPrivateProjects());
    }

    public ObservableList<Project> getPublicProjects() {
        return FXCollections.observableArrayList(projectManager.getPublicProjects());
    }

    public ObservableList<Devices> getAllDevicesForProject(String refNumber) {
        return FXCollections.observableArrayList(projectManager.getAllDevicesForProject(refNumber));
    }

    public ObservableList<Project> getProjectsForAccount(int accountID) {
        return FXCollections.observableArrayList(projectManager.getProjectsForAccount(accountID));
    }

    public ObservableList<String> getPicturesForProject(String refNumber) {
        return FXCollections.observableArrayList(projectManager.getPicturesForProject(refNumber));
    }

    public ObservableList<Project> searchForProjects(String userSearchInput, String searchOption) {
        return FXCollections.observableArrayList(projectManager.searchForProjects(userSearchInput, searchOption));
    }

    public Image getPictureByPath(String path) {
        return new Image(path);
    }

    public void deletePicture(int id) {
        projectManager.deletePicture(id);
    }

    public int getPictureIDByPath(String path) {
        return projectManager.getPictureIDByPath(path);
    }

    public void createProject(Project project, int accountID) {
        projectManager.createProject(project, accountID);
    }

    public void editProject(Project project) {
        projectManager.editProject(project);
    }

    public void deleteProject(Project project) {
        projectManager.deleteProject(project.getRefNumber());
    }

    public void recordLog(String refNumber, int accountID) {
        projectManager.recordLog(refNumber, accountID);
    }

    public void createPicture(String path, String refNumber) {
        projectManager.createPicture(path, refNumber);
    }

    public void createDevice(Devices devices) {
        projectManager.createDevice(devices);
    }

    public int getMaxIdForDevice() {
        return projectManager.getMaxIdForDevice();
    }

    public ObservableList<EditLog> getAllLogsForProject(String refNumber) {
        return FXCollections.observableArrayList(projectManager.getAllLogsForProject(refNumber));
    }

    public List<Integer> getAllAccountIDsForProject(String refNumber) {
        return projectManager.getAllAccountsForProject(refNumber);
    }

    public String getLatestLogForProject(String refNumber) {
        return projectManager.getLastLogForProject(refNumber);
    }

    public Devices getDeviceByName(String name) {
        return projectManager.getDeviceByName(name);
    }
    public void removeDevice(int id){
        projectManager.removeDevice(id);
    }
}
