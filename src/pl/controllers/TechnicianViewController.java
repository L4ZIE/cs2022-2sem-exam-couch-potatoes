package pl.controllers;

import be.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
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

    private ProjectModel projectModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
    }


    public void createBtnPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/fxml/ProjectView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stageCreate = new Stage();
        stageCreate.setTitle("New Project");

        stageCreate.setScene(scene);
        stageCreate.show();
        stageCreate.setResizable(false);
    }

    public void updateBtnPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/fxml/ProjectView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stageUpdate = new Stage();
        stageUpdate.setTitle("Update Project");

        stageUpdate.setScene(scene);
        stageUpdate.show();
        stageUpdate.setResizable(false);
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
        }
    }

    public void searchFieldKeyPressed(KeyEvent keyEvent) {
    }

    //searchbar
    public void createProjectPressed(ActionEvent actionEvent) {
    }

    public void allProjectsBtnPressed(ActionEvent actionEvent) {

        projectTableView.setItems(projectModel.getAllProjects());
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
}

