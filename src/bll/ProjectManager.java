package bll;

import be.Project;
import bll.interfaces.IProjectManager;
import dal.ProjectDAO;
import dal.interfaces.IProjectDAO;

import java.util.List;

public class ProjectManager implements IProjectManager {
    IProjectDAO projectDAO;
    private List<Project> allProjects;

    public ProjectManager(){
        projectDAO = new ProjectDAO();
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
}
