package dal.interfaces;

import be.Project;

import java.util.List;

public interface IProjectDAO {
    List<Project> getAllProjects();

    void createProject(Project project);

    void editProject(Project selectedProject);

    void deleteProject(String refNumber);
    Project getProjectByRefNumber(String refNumber);
}
