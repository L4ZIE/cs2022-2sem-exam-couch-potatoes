package pl.controllers;

import be.Devices;
import be.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.models.ProjectModel;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static pl.controllers.ProjectViewController.devices;

public class DevicesViewController implements Initializable {
    @FXML
    private Label lblRefNumber;
    @FXML
    private TextField txfDeviceName;
    @FXML
    private TextField txfUserName;
    @FXML
    private TextField txfUserPassword;
    @FXML
    private TextField txfProjectRefNumber;
    @FXML
    private Button btnSaveDevice,
            btnCancelAction, btnClose, btnMinimize;

    private ProjectModel projectModel;
    private ProjectViewController projectViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
        projectViewController = new ProjectViewController();
    }


    public void cancelPressed(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void saveDevice(ActionEvent event) {
        ProjectViewController.saveDeviceToList(new Devices(projectModel.getMaxIdForDevice() + 1,
                txfDeviceName.getText(),
                txfUserName.getText(),
                txfUserPassword.getText(),
                ""
        ));

            JOptionPane.showMessageDialog(null, "Device added successfully");
            closeWindow();
        }




    public void closePressed(ActionEvent event) {
        closeWindow();
    }

    public void minimizePressed(ActionEvent event) {
        Stage stage = (Stage) btnMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
    public void closeWindow() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
