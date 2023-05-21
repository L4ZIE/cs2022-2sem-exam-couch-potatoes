package pl.controllers;

import be.AccountType;
import be.Project;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.models.ProjectModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TechnicianViewController implements Initializable {

    @FXML
    public Label lblUsername;
    @FXML
    public Button btnMin,
            btnMax,
            btnClose,
            btnNameSearch,
            btnLocSearch,
            btnStartSearch,
            btnEndSearch,
            btnApprovedSearch,
            createBtn,
            updateBtn,
            previewBtn,
            saveBtn,
            sendBtn,
            deleteBtn,
            allProjectsBtn,
            privateProjectsBtn,
            publicProjectsBtn,
            btnAccounts;
    @FXML
    public AnchorPane anpMain;
    @FXML
    private TableView<Project> projectTableView;

    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Project, String> colCustomerName;
    @FXML
    private TableColumn<Project, String> colCustomerLocation;
    @FXML
    private TableColumn<Project, String> colStartDate;
    @FXML
    private TableColumn<Project, String> colEndDate;
    @FXML
    private TableColumn<Project, String> colApproved;

    private ProjectModel projectModel;
    private boolean needsRefresh = false;
    private static Project selectedProject;
    private String activeSearchOption;
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
        activeSearchOption = "name";
        activateButton("name");
        fillProjectsTable(projectTableView);
        lblUsername.setText(LoginController.getUsername());

        if (LoginController.getAccountType().equals(AccountType.PROJECTMANAGER) ||
                LoginController.getAccountType().equals(AccountType.CEO)) {
            btnAccounts.setVisible(true);
        }
        removeInactiveProjects();
        anpMain.setOnMousePressed(this::handleMousePressed);
        anpMain.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void createBtnPressed() {
        selectedProject = null;
        openWindow();
    }

    public void updateBtnPressed() {
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
            stageCreate.initStyle(StageStyle.UNDECORATED);
            stageCreate.show();

            stageCreate.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void openWindow() {
        openWindow(null);
    }

    public void previewBtnPressed() {
        selectedProject = projectTableView.getSelectionModel().getSelectedItem();
        saveBtnPressed("temp/pdf/project_" + selectedProject.getRefNumber() + ".pdf");
    }


    public void saveBtnPressed(String location) {
        Project selectedProject = projectTableView.getSelectionModel().getSelectedItem();
        FileChooser chooser = new FileChooser();
        File file = null;

        if (location == null) {
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("pdf files (*.pdf)", "*.pdf"));
            chooser.setInitialFileName(selectedProject.getRefNumber());
            file = chooser.showSaveDialog(null);
        }

        if (location != null || file != null) {
            try {
                Document document = new Document();
                if (location == null)
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                else
                    PdfWriter.getInstance(document, new FileOutputStream(location));

                document.open();

                document.add(new Paragraph("Reference Number: " + selectedProject.getRefNumber()));
                document.add(new Paragraph("Customer Name: " + selectedProject.getCustomerName()));
                document.add(new Paragraph("Customer Email: " + selectedProject.getCustomerEmail()));
                document.add(new Paragraph("Customer Location: " + selectedProject.getCustomerLocation()));
                document.add(new Paragraph("Drawing: " + selectedProject.getDrawing()));//TODO
                document.add(new Paragraph("Note: " + selectedProject.getNote()));
                document.add(new Paragraph("Pictures: "));//TODO
                document.add(new Paragraph("Project Start Date: " + selectedProject.getStartDate()));
                document.add(new Paragraph("Project End Date: " + selectedProject.getEndDate()));
                document.add(new Paragraph(""));

                document.close();
                if (location == null)
                    JOptionPane.showMessageDialog(null, "File successfully saved.");
                else
                    Desktop.getDesktop().open(new File(location));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Couldn't save pdf file.");
            }
        }
    }

    public void saveBtnPressed() {
        saveBtnPressed(null);
    }

    public void sendBtnPressed() {
    }

    public void deleteBtnPressed() {
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
            searchForQuery();
        }
    }

    private void searchForQuery() {
        updateProjectTable(projectModel.searchForProjects(searchField.getText(), activeSearchOption));
    }

    public void updateProjectTable(ObservableList<Project> selectedProjects) {
        projectTableView.setItems(selectedProjects);
    }

    public void searchForName() {
        activeSearchOption = "name";
        activateButton("name");
    }

    public void searchForLocation() {
        activeSearchOption = "location";
        activateButton("location");
    }

    public void searchForStart() {
        activeSearchOption = "start";
        activateButton("start");
    }

    public void searchForEnd() {
        activeSearchOption ="end";
        activateButton("end");
    }
    public void fillProjectsTable(TableView<Project> projectTableView, String projectType) {
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustomerLocation.setCellValueFactory(new PropertyValueFactory<>("customerLocation"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colApproved.setCellValueFactory(new PropertyValueFactory<>("approved"));

        colApproved.setCellValueFactory(cellData -> {
            Boolean approved = cellData.getValue().getApproved();
            String approvedText = approved ? "Approved" : "Pending";
            return new SimpleStringProperty(approvedText);
        });

        try {
            switch (projectType) {
                case "all" -> projectTableView.setItems(projectModel.getAllProjects());
                case "private" -> projectTableView.setItems(projectModel.getPrivateProjects());
                case "public" -> projectTableView.setItems(projectModel.getPublicProjects());
            }
            projectTableView.getSelectionModel().select(0);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fillProjectsTable(TableView<Project> projectTableView) {
        fillProjectsTable(projectTableView, "all");
    }

    public void allProjectsBtnPressed() {
        fillProjectsTable(projectTableView, "all");
    }

    public void publicProjectsBtnPressed() {
        fillProjectsTable(projectTableView, "public");
    }

    public void privateProjectsBtnPressed() {
        fillProjectsTable(projectTableView, "private");
    }


    public void minBtnPressed(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    public void maxBtnBtn(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    public void closeBtnPressed(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void refresh() {
        if (needsRefresh) {
            fillProjectsTable(projectTableView);
            needsRefresh = false;
        }
    }

    public void removeInactiveProjects() {

        try {
            List<Project> allProjects = projectModel.getAllProjects();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int thisYear = Integer.parseInt(sdf.format(new Date()));
            for (Project p : allProjects) {
                if (projectModel.getLatestLogForProject(p.getRefNumber()) != null)
                    if (thisYear - Integer.parseInt(projectModel.getLatestLogForProject(p.getRefNumber()).substring(6, 10)) >= 4)
                        projectModel.deleteProject(p);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static Project getSelectedProject() {
        return selectedProject;
    }

    public void btnAccountsPressed() {
    }

    public void btnSearchPressed() {
        searchForQuery();

    }

    public void searchForApproved() {
        activateButton("approved");
        activateButton("approved");
        activeSearchOption = "approved";
    }
    private void activateButton(String buttonName) {
        btnNameSearch.setDisable(false);
        btnLocSearch.setDisable(false);
        btnStartSearch.setDisable(false);
        btnEndSearch.setDisable(false);
        btnApprovedSearch.setDisable(false);

        switch (buttonName) {
            case "name" -> btnNameSearch.setDisable(true);
            case "location" -> btnLocSearch.setDisable(true);
            case "start" -> btnStartSearch.setDisable(true);
            case "end" -> btnEndSearch.setDisable(true);
            case "approved" -> btnApprovedSearch.setDisable(true);
        }
    }
}

