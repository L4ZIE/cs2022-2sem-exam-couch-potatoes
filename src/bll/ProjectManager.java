package bll;

import be.EditLog;
import be.Project;
import bll.interfaces.IProjectManager;
import dal.LogDAO;
import dal.PictureDAO;
import dal.ProjectAccountDAO;
import dal.ProjectDAO;
import dal.interfaces.ILogDAO;
import dal.interfaces.IPictureDAO;
import dal.interfaces.IProjectAccountDAO;
import dal.interfaces.IProjectDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager implements IProjectManager {
    private IProjectDAO projectDAO;
    private ILogDAO logDAO;
    private IPictureDAO pictureDAO;
    private IProjectAccountDAO projectAccountDAO;
    private List<Project> allProjects;
    private List<Project> privateProjects;

    public ProjectManager(){
        projectDAO = new ProjectDAO();
        pictureDAO = new PictureDAO();
        logDAO = LogDAO.getInstance();
        projectAccountDAO = new ProjectAccountDAO();
        fillAllProjects();
    }
    private void fillAllProjects() {
        allProjects = projectDAO.getAllProjects();
    }
    @Override
    public List<Project> getAllProjects() {
        return allProjects;
    }

    @Override
    public List<Project> getPrivateProjects() {
        List<Project> listPrivateProjects = new ArrayList<>();
        for (Project project : allProjects){
            if (project.getPrivateProject()== false){
                listPrivateProjects.add(project);
            }
        }
        return listPrivateProjects;
    }

    public List<Project> getPublicProjects(){
        List<Project> listPublicProjects = new ArrayList<>();
        for (Project project : allProjects){
            if (project.getPrivateProject()==true){
                listPublicProjects.add(project);
            }
        }
        return listPublicProjects;
    }

    @Override
    public void createProject(Project project, int accountID) {
        projectDAO.createProject(project);
        projectAccountDAO.saveProject(project.getRefNumber(), accountID, projectAccountDAO.getMaxID());
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
     * @param accountID The id of the account.
     * @return A list of projects.
     */
    @Override
    public List<Project> getProjectsForAccount(int accountID){
        List<Project> results = new ArrayList<>();
        //TODO develop ProjectAccountDAO
        return results;
    }


    /**
     * Returns all the pictures for a project.
     * @param refNumber The reference number for the project.
     * @return The path of all pictures.
     */
    @Override
    public List<String> getPicturesForProject(String refNumber){
        return pictureDAO.getAllPicturesForProject(refNumber);
    }
    @Override
    public void recordLog(String refNumber, int accountID){
        int id = logDAO.getMaxID() + 1;
        String date = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        logDAO.recordLog(new EditLog(id, accountID, refNumber, date));
    }

    @Override
    public void deleteProject(String refNumber) {
        //projectAccountDAO.deleteConnection(refNumber, 1);//TODO change later
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

        for (Project project: allProjects){
            switch (searchOption){
                case "name":
                    if (project.getCustomerName().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                    break;
                case "location":
                    if (project.getCustomerLocation().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                    break;
                case "start":
                    if (project.getStartDate().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                    break;
                case "end":
                    if (project.getEndDate().toLowerCase().contains(userSearchInput.toLowerCase()))
                        matchingProjects.add(project);
                    break;
            }
        }
        return matchingProjects;
    }


}

