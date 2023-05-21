package bll;

import be.Devices;
import be.EditLog;
import be.Project;
import bll.interfaces.IProjectManager;
import dal.*;
import dal.interfaces.*;
import javafx.collections.FXCollections;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager implements IProjectManager {
    private final IProjectDAO projectDAO;
    private final ILogDAO logDAO;
    private final IPictureDAO pictureDAO;
    private final IProjectAccountDAO projectAccountDAO;
    private final IDevicesDAO devicesDAO;
    private List<Project> allProjects;
    private List<EditLog> allLogs;
    private List<Devices> allDevices;


    public ProjectManager() {
        projectDAO = new ProjectDAO();
        pictureDAO = new PictureDAO();
        logDAO = LogDAO.getInstance();
        projectAccountDAO = new ProjectAccountDAO();
        devicesDAO = new DevicesDAO();
        fillAllProjects();
        fillAllLogs();
        fillAllDevices();
    }

    private void fillAllDevices() {
        allDevices = devicesDAO.getAllDevices();
    }

    private void fillAllLogs() {
        allLogs = logDAO.getAllLogs();
    }

    private void fillAllProjects() {
        allProjects = projectDAO.getAllProjects();
    }

    @Override
    public List<Project> getAllProjects() {
        fillAllProjects();
        return allProjects;
    }

    @Override
    public List<Project> getPrivateProjects() {
        List<Project> listPrivateProjects = new ArrayList<>();
        for (Project project : allProjects) {
            if (!project.getPrivateProject()) {
                listPrivateProjects.add(project);
            }
        }
        return listPrivateProjects;
    }

    public List<Project> getPublicProjects() {
        List<Project> listPublicProjects = new ArrayList<>();
        for (Project project : allProjects) {
            if (project.getPrivateProject()) {
                listPublicProjects.add(project);
            }
        }
        return listPublicProjects;
    }

    @Override
    public void createProject(Project project, int accountID) {
        projectDAO.createProject(project);
        projectAccountDAO.saveProject(project.getRefNumber(), accountID, projectAccountDAO.getMaxID() + 1);
        allProjects.add(project);
    }

    @Override
    public void editProject(Project project) {
        projectDAO.editProject(project);
        allProjects.removeIf(obj -> obj.getRefNumber().equals(project.getRefNumber()));
        allProjects.add(project);
    }

    /**
     * Returns all the projects for an account.
     *
     * @param accountID The id of the account.
     * @return A list of projects.
     */
    @Override
    public List<Project> getProjectsForAccount(int accountID) {
        List<Project> results = new ArrayList<>();
        //TODO develop ProjectAccountDAO
        return results;
    }


    /**
     * Returns all the pictures for a project.
     *
     * @param refNumber The reference number for the project.
     * @return The path of all pictures.
     */
    @Override
    public List<String> getPicturesForProject(String refNumber) {
        return pictureDAO.getAllPicturesForProject(refNumber);
    }

    @Override
    public List<Devices> getAllDevicesForProject(String refNumber) {
        List<Devices> result = new ArrayList<>();
        for (Devices d : allDevices) {
            if(d.getRefNumber().equals(refNumber))
                result.add(d);
        }
        return result;
    }

    @Override
    public List<Devices> getAllDevices() {
        return allDevices;
    }

    @Override
    public List<Integer> getAllAccountsForProject(String refNumber) {
        return projectAccountDAO.getAllAccountsForProject(refNumber);
    }

    @Override
    public void recordLog(String refNumber, int accountID) {
        int id = logDAO.getMaxID() + 1;
        String date = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        logDAO.recordLog(new EditLog(id, accountID, refNumber, date));
    }

    @Override
    public void deleteProject(String refNumber) {
        projectDAO.deleteProject(refNumber);
        fillAllProjects();
    }

    @Override
    public void createPicture(String path, String refNumber) {
        int id = pictureDAO.getMaxID() + 1;
        pictureDAO.createPicture(path, refNumber, id);
    }

    @Override
    public void deletePicture(int id) {
        pictureDAO.deletePicture(id);
    }

    @Override
    public int getPictureIDByPath(String path) {
        return pictureDAO.getPictureIDByPath(path);
    }


    public List<Project> searchForProjects(String userSearchInput, String searchOption) {
        List<Project> matchingProjects = FXCollections.observableArrayList();

        for (Project project : allProjects) {
            switch (searchOption) {
                case "name" -> {
                    if (project.getCustomerName().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                }
                case "location" -> {
                    if (project.getCustomerLocation().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                }
                case "start" -> {
                    if (project.getStartDate().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                }
                case "end" -> {
                    if (project.getEndDate().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                }
                case "approved" -> {
                    if ("approved".contains(userSearchInput.toLowerCase())){
                        if(project.getApproved())
                            matchingProjects.add(project);
                    }
                    else if ("pending".contains(userSearchInput.toLowerCase()))
                        if(!project.getApproved())
                            matchingProjects.add(project);
                }

            }
        }
        return matchingProjects;
    }

    @Override
    public void createDevice(Devices devices) {
        devicesDAO.createDevice(devices);
    }

    @Override
    public void removeDevice(int id) {
        devicesDAO.removeDevice(id);
    }

    @Override
    public int getMaxIdForDevice() {
        return devicesDAO.getMaxIdForDevice();
    }

    @Override
    public List<EditLog> getAllLogsForProject(String refNumber) {
        List<EditLog> result = new ArrayList<>();
        for (EditLog l : allLogs) {
            if(l.getRefNumber().equals(refNumber))
                result.add(l);
        }
        return result;
    }

    @Override
    public String getLastLogForProject(String refNumber) {
        return logDAO.getMaxDateForProject(refNumber);
    }

    @Override
    public Devices getDeviceByName(String name) {
        for (Devices d : allDevices) {
            if (d.getDeviceName().equals(name))
                return d;
        }

        return null;
    }

    @Override
    public Project getProjectByRefNumber(String refNumber) {
        return projectDAO.getProjectByRefNumber(refNumber);
    }


}

