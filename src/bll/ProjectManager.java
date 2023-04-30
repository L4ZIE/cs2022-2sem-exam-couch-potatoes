package bll;

import be.Project;
import bll.interfaces.IProjectManager;
import dal.ProjectDAO;
import dal.interfaces.IProjectDAO;

import java.util.ArrayList;
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
    public void editProject(Project selectedProject) {
        projectDAO.editProject(selectedProject);
        allProjects.remove(selectedProject);

        List<Project> editedList = new ArrayList<>();
        for(Project p : allProjects) {
            if(p.equals(selectedProject)) {
                editedList.add(p);
            }
        }
    }
}
