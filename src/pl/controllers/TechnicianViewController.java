package pl.controllers;

import be.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.models.ProjectModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TechnicianViewController implements Initializable {

    @FXML
    private Button createBtn, updateBtn, previewBtn, saveBtn, sendBtn, deleteBtn;
    @FXML
    private TableView<Project> projectTableView;

    @FXML
    private TextField txfSearchField;
    @FXML
    private TableColumn colCustomerName,
            colCustomerLocation,
            colStartDate,
            colEndDate,
            colApproved;

    private ProjectModel projectModel;
    private boolean needsRefresh = false;
    private static Project selectedProject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
        fillProjectsTable(projectTableView);
        //TODO display public projects
    }


    public void createBtnPressed(ActionEvent actionEvent) {
        openWindow();
    }

    public void updateBtnPressed(ActionEvent actionEvent) {
        if (projectTableView.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a project.");
        } else {
            selectedProject = projectTableView.getSelectionModel().getSelectedItem();
            openWindow(projectTableView.getSelectionModel().getSelectedItem());
        }
    }

    private void openWindow(Project project) {
        needsRefresh = true;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/fxml/ProjectView.fxml"));

        try {
            Scene scene = new Scene(loader.load());
            Stage stageCreate = new Stage();
            if (project == null) {
                stageCreate.setTitle("New Project");
            } else {
                stageCreate.setTitle(selectedProject.getRefNumber());
            }
            stageCreate.initModality(Modality.APPLICATION_MODAL);
            stageCreate.setScene(scene);
            stageCreate.show();
            stageCreate.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void openWindow() {
        openWindow(null);
    }

    public void previewBtnPressed(ActionEvent actionEvent) {
    }

    public void saveBtnPressed(ActionEvent actionEvent) {
    }

    public void sendBtnPressed(ActionEvent actionEvent) {
    }

    public void deleteBtnPressed(ActionEvent actionEvent) {
        if (projectTableView.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please select a project.");
        } else {
            Project selectedProject = projectTableView.getSelectionModel().getSelectedItem();
            projectModel.deleteProject(selectedProject);
            fillProjectsTable(projectTableView);
        }
    }

    public void searchFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            //projectModel.searchForProject(searchField.getText());
        }
    }


    public void createProjectPressed(ActionEvent actionEvent) {
        //projectModel.searchForProject(searchField.getText());

    }


    public void fillProjectsTable(TableView projectTableView) {
        colCustomerName.setCellValueFactory(new PropertyValueFactory<Project, String>("customerName"));
        colCustomerLocation.setCellValueFactory(new PropertyValueFactory<Project, String>("customerLocation"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Project, String>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<Project, String>("endDate"));
        colApproved.setCellValueFactory(new PropertyValueFactory<Project, String>("approved"));

        try {
            projectTableView.setItems(projectModel.getAllProjects());
            projectTableView.getSelectionModel().select(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void allProjectsBtnPressed(ActionEvent actionEvent) {
        fillProjectsTable(projectTableView);
    }

    public void publicProjectsBtnPressed(ActionEvent actionEvent) {
    }

    public void privateProjectsBtnPressed(ActionEvent actionEvent) {
    }

    public void logoutBtnPressed(ActionEvent actionEvent) {
    }

    public void minBtnPressed(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    public void maxBtnBtn(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    public void closeBtnPressed(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void refresh(MouseEvent mouseEvent) {
        if (needsRefresh) {
            fillProjectsTable(projectTableView);
            needsRefresh = false;
        }
    }
    public static Project getSelectedProject(){
        return selectedProject;
    }
}

