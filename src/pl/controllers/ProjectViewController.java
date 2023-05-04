package pl.controllers;

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
    private DatePicker startDate,
            endDate;
    @FXML
    private ImageView imvPicture,
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();

    }

    public void getDate(ActionEvent event) {
        LocalDate data = startDate.getValue();
        String formattedDate = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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

            closeWindow();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();
    }


    public void btnSavePressed(ActionEvent actionEvent) {
        //TODO
    }


    public void btnCancelPressed(ActionEvent actionEvent) {
        //TODO//is cancel button
    }

    public void btnAddPressed(ActionEvent actionEvent) {
        List<Image> images = new ArrayList<>();
        int currentImageIndex = 0;

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        if (!files.isEmpty()) {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            imvPicture.setImage(images.get(currentImageIndex));

        }
    }


    public void btnRemovePressed(ActionEvent actionEvent) {
        //TODO
    }

    public void btnDrawPressed(ActionEvent actionEvent) {
        //TODO
    }

    public void startDatePressed(ActionEvent actionEvent) {
    }

    public void endDatePressed(ActionEvent actionEvent) {
    }

    public void btnMinPressed(ActionEvent actionEvent) {
    }

    public void btnMaxPressed(ActionEvent actionEvent) {
    }

    public void btnClosePressed(ActionEvent actionEvent) {
    }
}
