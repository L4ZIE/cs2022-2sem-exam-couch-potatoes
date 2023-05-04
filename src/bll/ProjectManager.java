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
import dal.interfaces.IProjectDAO;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectManager implements IProjectManager {
    private IProjectDAO projectDAO;
    private ILogDAO logDAO;
    private IPictureDAO pictureDAO;
    private ProjectAccountDAO projectAccountDAO;
    private List<Project> allProjects;

    public ProjectManager(){
        projectDAO = new ProjectDAO();
        pictureDAO = new PictureDAO();
        logDAO = LogDAO.getInstance();
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
    public void createProject(Project project) {
        projectDAO.createProject(project);
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
        projectDAO.deleteProject(refNumber);
    }
}

