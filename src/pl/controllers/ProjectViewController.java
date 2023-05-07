package pl.controllers;

import be.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.models.ProjectModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {
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
            btnClose;  //// need to add 2 button: btnNextPicture, btnPreviousPicture(methods are ready)

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

    }

    public void goBackPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pl/fxml/TechnicianView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Projects");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.show();

            closeWindows();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSavePressed(ActionEvent actionEvent) {
        int rand = (int) (Math.random() * 10000);
        String refNumber = txfCustomerName.getText().substring(0,3) + txfCustomerEmail.getText().substring(0,3) + rand;
        projectModel.createProject(new Project(refNumber,
                txfCustomerName.getText(),
                txfCustomerEmail.getText(),
                txfCustomerLocation.getText(),
                txaNotes.getText(),
                "drawing location placeholder",
                java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                startDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                true
        ));
        projectModel.recordLog(refNumber,1);
        //TODO create a picture,create a draw
    }


    public void btnCancelPressed(ActionEvent actionEvent) {
       closeWindows();
    }

    public void btnAddPressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        if (!files.isEmpty()) {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
        else {
            JOptionPane.showMessageDialog(null,"No image has been selected");
        }
    }

    public void btnNextPicturePressed(ActionEvent actionEvent) {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    public void btnPreviousPicturePressed(ActionEvent actionEvent) {
        if (!images.isEmpty()) {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    private void displayImage() {
        if (!images.isEmpty()) {
            imvPictures.setImage(images.get(currentImageIndex));
        }
    }


    public void btnRemovePressed(ActionEvent actionEvent) {
        imvPictures.setImage(null);
        // images.remove(projectModel.getPictureByID(id));
        // projectModel.deletePicture(id);
    }

    public Image getPictureByPath(String path) {
        return new Image(path);
    }

    public void btnDrawPressed(ActionEvent actionEvent) {
        //TODO ADD  FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ADD FXML"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Drawing");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.show();

            closeWindows();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startDatePressed(ActionEvent actionEvent) { // TODO DELETE LATER
        //LocalDate data = startDate.getValue();
        //data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void endDatePressed(ActionEvent actionEvent) {// TODO DELETE LATER
        // LocalDate data = endDate.getValue();
        // data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
        closeWindows();
    }

    private void closeWindows() {
        Stage stage = (Stage) btnDraw.getScene().getWindow();
        stage.close();
    }
}
