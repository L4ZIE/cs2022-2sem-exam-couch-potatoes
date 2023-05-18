package pl.controllers;

import be.Project;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.models.ProjectModel;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class TechnicianViewController implements Initializable {

    @FXML
    private Button createBtn,
            updateBtn,
            previewBtn,
            saveBtn,
            sendBtn,
            deleteBtn,
            allProjectsBtn,
            privateProjectsBtn,
            publicProjectsBtn;
    @FXML
    private TableView<Project> projectTableView;

    @FXML
    private TextField searchField;
    @FXML
    private TableColumn colCustomerName,
            colCustomerLocation,
            colStartDate,
            colEndDate,
            colApproved;

    private ProjectModel projectModel;
    private boolean needsRefresh = false;
    private static Project selectedProject;

    //private String refNumber;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
        fillProjectsTable(projectTableView, "all");
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
        //TODO fix parameters
        previewPressed(selectedProject.getRefNumber());
    }

    //TODO fix connection
    public void previewPressed(String refNumber) {
        Project project =projectModel.getProjectByRefNumber(refNumber);

        if (project != null){
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("project_"+ refNumber + ".pdf"));
                document.open();

                document.add(new Paragraph("Reference Number: " + project.getRefNumber()));
                document.add(new Paragraph("Customer Name: " + project.getCustomerName()));
                document.add(new Paragraph("Customer Email: " + project.getCustomerEmail()));
                document.add(new Paragraph("Customer Location: " + project.getCustomerLocation()));
                document.add(new Paragraph("Note: " + project.getNote()));
                document.add(new Paragraph("Drawing: " + project.getDrawing()));
                document.add(new Paragraph("Creation Date: " + project.getCreationDate()));
                document.add(new Paragraph("Project Start Date: " + project.getStartDate()));
                document.add(new Paragraph("Project End Date: " + project.getEndDate()));
                document.add(new Paragraph("Approved: " + project.getApproved()));
                document.add(new Paragraph("Private: " + project.getPrivateProject()));
                document.add(new Paragraph(""));

                document.close();
                System.out.println("PDF file generated successfully");
                }catch (Exception e){
                throw new RuntimeException(e);
            }
            }else {
            System.out.println("Project with reference number"+ refNumber+"not found.");
            }
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
            fillProjectsTable(projectTableView, "all");
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

    public void updateProjectTable(ObservableList<Project> selectedProjects) {
        projectTableView.setItems(selectedProjects);
    }

    public void searchForName() {
        updateProjectTable(projectModel.searchForProjects(searchField.getText(), "name"));
    }

    public void searchForLocation() {
        updateProjectTable(projectModel.searchForProjects(searchField.getText(), "location"));
    }

    public void searchForStart() {
        updateProjectTable(projectModel.searchForProjects(searchField.getText(), "start"));
    }

    public void searchForEnd() {
        updateProjectTable(projectModel.searchForProjects(searchField.getText(), "end"));
    }

    //TODO change the fill for Approved to be "Approved or pending"
    public void fillProjectsTable(TableView projectTableView, String projectType) {
        colCustomerName.setCellValueFactory(new PropertyValueFactory<Project, String>("customerName"));
        colCustomerLocation.setCellValueFactory(new PropertyValueFactory<Project, String>("customerLocation"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Project, String>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<Project, String>("endDate"));
        colApproved.setCellValueFactory(new PropertyValueFactory<Project, String>("approved"));

        try {
            if (projectType.equals("all")) {
                projectTableView.setItems(projectModel.getAllProjects());
            } else if (projectType.equals("private")) {
                projectTableView.setItems(projectModel.getPrivateProjects());
            } else if (projectType.equals("public")) {
                projectTableView.setItems(projectModel.getPublicProjects());
            }
            projectTableView.getSelectionModel().select(0);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void allProjectsBtnPressed(ActionEvent actionEvent) {
        fillProjectsTable(projectTableView, "all");
    }

    public void publicProjectsBtnPressed(ActionEvent actionEvent) {
        fillProjectsTable(projectTableView, "public");
    }

    public void privateProjectsBtnPressed(ActionEvent actionEvent) {
        fillProjectsTable(projectTableView, "private");
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
            fillProjectsTable(projectTableView, "all");
            needsRefresh = false;
        }
    }

    public static Project getSelectedProject() {
        return selectedProject;
    }


}

