package dal;

import be.Project;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.connector.DBConnector;
import dal.interfaces.IProjectDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO implements IProjectDAO {
    private PreparedStatement preparedStatement;
    private final DBConnector connector = DBConnector.getInstance();

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM Projects";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                projects.add(new Project(
                        resultSet.getString("refNumber"),
                        resultSet.getString("customerName"),
                        resultSet.getString("customerEmail"),
                        resultSet.getString("customerLocation"),
                        resultSet.getString("note"),
                        resultSet.getString("drawing"),
                        resultSet.getString("creationDate"),
                        resultSet.getString("projectStartDate"),
                        resultSet.getString("projectEndDate"),
                        resultSet.getBoolean("approved"),
                        resultSet.getBoolean("private"),
                        resultSet.getBoolean("includePictures"),
                        resultSet.getBoolean("includeDrawing")
                ));
            }
            return projects;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createProject(Project project) {

        String sql = "INSERT INTO Projects ( refNumber, customerName, customerEmail, customerLocation, note, " +
                "drawing, creationDate, projectStartDate, projectEndDate, approved) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            preparedStatement = connector.createConnection().prepareStatement(sql);

            preparedStatement.setString(1, project.getRefNumber());
            preparedStatement.setString(2, project.getCustomerName());
            preparedStatement.setString(3, project.getCustomerEmail());
            preparedStatement.setString(4, project.getCustomerLocation());
            preparedStatement.setString(5, project.getNote());
            preparedStatement.setString(6, project.getDrawing());
            preparedStatement.setString(7, project.getCreationDate());
            preparedStatement.setString(8, project.getStartDate());
            preparedStatement.setString(9, project.getEndDate());
            preparedStatement.setBoolean(10, project.getApproved());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void editProject(Project selectedProject) {

        String sql = "UPDATE Projects SET customerName = ?,customerEmail = ?, customerLocation = ?, note = ?, drawing = ?, " +
                "creationDate = ?, projectStartDate = ?, projectEndDate = ?, approved = ? WHERE refNumber = ? ";
        try {
            Connection conn = connector.createConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, selectedProject.getCustomerName());
            preparedStatement.setString(2, selectedProject.getCustomerEmail());
            preparedStatement.setString(3, selectedProject.getCustomerLocation());
            preparedStatement.setString(4, selectedProject.getNote());
            preparedStatement.setString(5, selectedProject.getDrawing());
            preparedStatement.setString(6, selectedProject.getCreationDate());
            preparedStatement.setString(7, selectedProject.getStartDate());
            preparedStatement.setString(8, selectedProject.getEndDate());
            preparedStatement.setBoolean(9, selectedProject.getApproved());
            preparedStatement.setString(10, selectedProject.getRefNumber());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProject(String refNumber) {
        String sql = "DELETE FROM Projects WHERE refNumber = ?";
        try {
            Connection connection = connector.createConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, refNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public Project getProjectByRefNumber(String refNumber){
        String sql = "SELECT * FROM Projects WHERE refNumber = ? ";
        Project project = null;
        try {
            PreparedStatement preparedStatement = connector.createConnection().prepareStatement(sql);
            preparedStatement.setString(1,refNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                project = new Project(
                        resultSet.getString("refNumber"),
                        resultSet.getString("customerName"),
                        resultSet.getString("customerEmail"),
                        resultSet.getString("customerLocation"),
                        resultSet.getString("note"),
                        resultSet.getString("drawing"),
                        resultSet.getString("creationDate"),
                        resultSet.getString("projectStartDate"),
                        resultSet.getString("projectEndDate"),
                        resultSet.getBoolean("approved"),
                        resultSet.getBoolean("private"),
                        resultSet.getBoolean("includePictures"),
                        resultSet.getBoolean("includeDrawing")
                );
            }
            resultSet.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return project;
    }
}
