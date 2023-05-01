package bll.interfaces;

import be.Project;

import java.util.List;

public interface IProjectManager {
    List<Project> getAllProjects();

    void createProject(Project project);

    void editProject(Project project);
}
