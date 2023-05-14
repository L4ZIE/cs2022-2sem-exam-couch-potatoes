package pl.controllers;

import be.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.models.ProjectModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ProjectViewController implements Initializable {
    @FXML
    private FlowPane flowPane;
    @FXML
    private CheckBox checkBoxBold,
            checkBoxItalic;
    @FXML
    private ComboBox<String> comboBoxFont;
    @FXML
    private ComboBox<Integer> comboBoxFontSize;
    @FXML
    private DatePicker startDate,
            endDate;
    @FXML
    private ImageView imvPictures,
            imvDrawing;
    @FXML
    private Button goBack,
            logoutBtn;
    @FXML
    private Label usernameLbl,
            lblRefNumber;
    @FXML
    private Button btnMin,
            btnMax,
            btnSave,
            btnCancel,
            btnAdd,
            btnRemove,
            btnDraw,
            btnClose;


    @FXML
    private TextArea txaNotes;
    @FXML
    private TextField txfCustomerName,
            txfCustomerEmail,
            txfCustomerLocation;
    @FXML
    private ChoiceBox chbSelectAccount;
    private ProjectModel projectModel;
    private List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
        if (TechnicianViewController.getSelectedProject() != null)
            fillFields(TechnicianViewController.getSelectedProject());
        fillComboBox();
    }

    private void fillFields(Project selectedProject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txfCustomerName.setText(selectedProject.getCustomerName());
        txfCustomerEmail.setText(selectedProject.getCustomerEmail());
        txfCustomerLocation.setText(selectedProject.getCustomerLocation());
        lblRefNumber.setText(selectedProject.getRefNumber());
        startDate.setValue(LocalDate.parse(selectedProject.getStartDate().substring(0, 10), formatter));
        endDate.setValue(LocalDate.parse(selectedProject.getEndDate().substring(0, 10), formatter));
        txaNotes.setText(selectedProject.getNote());
        List<String> imageLocations = projectModel.getPicturesForProject(selectedProject.getRefNumber());
        for (String s : imageLocations) {
            images.add(new Image(s));
        }
        displayImages();
        //TODO fill drawing
    }

    public void goBackPressed(ActionEvent actionEvent) {
        closeWindow();
    }

    /*public void btnSavePressed(ActionEvent actionEvent) {
        int rand = (int) (Math.random() * 10000); //TODO placeholder, change later
        String refNumber = txfCustomerName.getText().substring(0, 3) + txfCustomerEmail.getText().substring(0, 3) + rand;
        if (TechnicianViewController.getSelectedProject() == null) {

            projectModel.createProject(new Project(refNumber,
                    txfCustomerName.getText(),
                    txfCustomerEmail.getText(),
                    txfCustomerLocation.getText(),
                    txaNotes.getText(),
                    "drawing location placeholder", //TODO placeholder, change later
                    java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    startDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    true
            ), 1);
            for (Image img : images) {
                projectModel.createPicture(img.getUrl(), refNumber);
            }
            //projectModel.recordLog(refNumber, 1);
            JOptionPane.showMessageDialog(null, "Successfully saved Project.");
        } else {
            projectModel.editProject(new Project(lblRefNumber.getText(),
                    txfCustomerName.getText(),
                    txfCustomerEmail.getText(),
                    txfCustomerLocation.getText(),
                    txaNotes.getText(),
                    "drawing location placeholder", //TODO placeholder, change later
                    java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    startDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    true));
            for (Image img : images) {
                projectModel.createPicture(img.getUrl(), lblRefNumber.getText());
            }
            JOptionPane.showMessageDialog(null, "Successfully updated Project.");
        }

        closeWindow();
        //TODO create a picture,create a draw
    }*/


    public void btnCancelPressed(ActionEvent actionEvent) {
        closeWindow();
    }

    public void btnAddPressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        if (files != null) {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImages();
        }
    }


    public void displayImages() {
        if (!images.isEmpty()) {
            imvPictures.setImage(images.get(currentImageIndex));
        }
    }


    public void btnRemovePressed(ActionEvent actionEvent) {
        projectModel.deletePicture(projectModel.getPictureIDByPath(imvPictures.getImage().getUrl()));
        images.remove(imvPictures.getImage());
        imvPictures.setImage(null);
    }

    public Image getPictureByPath(String path) {
        return new Image(path);
    }

    public void btnDrawPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pl/fxml/DrawingView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Drawing");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.show();

            closeWindow();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnMinPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }

    public void btnMaxPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) btnMax.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    public void btnClosePressed(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnDraw.getScene().getWindow();
        stage.close();
    }

    public void fillComboBox() {
        List<String> fontName = Font.getFamilies();
        comboBoxFont.getItems().addAll(fontName);
        comboBoxFont.setValue(fontName.get(0));

        for (int i = 1; i <= 100; i++) {
            comboBoxFontSize.getItems().add(i);
        }
        comboBoxFontSize.setValue(1);

        comboBoxFont.setOnAction(e -> setFont());
        comboBoxFontSize.setOnAction(e -> setFont());
        checkBoxBold.setOnAction(e -> setFont());
        checkBoxItalic.setOnAction(e -> setFont());
    }

    private void setFont() {
        FontWeight weight;
        if (checkBoxBold.isSelected()) {
            weight = FontWeight.BOLD;
        } else {
            weight = FontWeight.NORMAL;
        }
        FontPosture posture;
        if (checkBoxItalic.isSelected()) {
            posture = FontPosture.ITALIC;
        } else {
            posture = FontPosture.REGULAR;
        }
        txaNotes.setFont(Font.font(comboBoxFont.getValue(), weight, posture, comboBoxFontSize.getValue()));
    }
}
