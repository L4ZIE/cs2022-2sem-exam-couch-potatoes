package pl.controllers;

import be.Account;
import be.Devices;
import be.Project;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.models.AccountModel;
import pl.models.ProjectModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ProjectViewController implements Initializable {

    @FXML
    public GridPane gdpPictures;
    @FXML
    public AnchorPane anpMain;
    @FXML
    private CheckBox checkBoxPrivateProject,
            chePicturesInclude,
            cheDrawInclude;
    @FXML
    private ComboBox<String> comboBoxFont;
    @FXML
    private ComboBox<Integer> comboBoxFontSize;
    @FXML
    private DatePicker startDate,
            endDate;
    @FXML
    private ImageView imvDrawing;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label lblRefNumber;
    @FXML
    private Button btnMin,
            btnMax,
            btnDraw,
            btnBold,
            btnItalic,
            btnUnderline,
            btnAddDevice,
            btnRemoveDevice;

    @FXML
    private TextArea txaNotes;
    @FXML
    private TextField txfCustomerName;
    @FXML
    private TextField txfCustomerEmail;
    @FXML
    private TextField txfCustomerLocation;
    @FXML
    private ChoiceBox<String> chbSelectAccount;
    @FXML
    private ChoiceBox<String> chbDevices;
    private ProjectModel projectModel;
    private List<Image> images = new ArrayList<>();
    private static String staticRefNumber;
    public static ObservableList<Devices> devices = FXCollections.observableArrayList();
    private static String drawingLocation;
    private Boolean boldToggled, italicToggled, underlineToggled;
    private AccountModel accountModel;
    private Image selectedPicture;
    private static Boolean changed;
    private static int devicesToAdd;
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectModel = new ProjectModel();
        accountModel = new AccountModel();
        selectedPicture = null;
        usernameLbl.setText(LoginController.getUsername());
        boldToggled = false;
        italicToggled = false;
        underlineToggled = false;
        changed = false;
        devicesToAdd = 0;
        if (TechnicianViewController.getSelectedProject() != null)
            fillFields(TechnicianViewController.getSelectedProject());
        else {
            btnAddDevice.setDisable(true);
            btnRemoveDevice.setDisable(true);
            chbSelectAccount.setDisable(true);
            chbDevices.setDisable(true);
        }
        fillComboBox();
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

    private void fillFields(Project selectedProject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txfCustomerName.setText(selectedProject.getCustomerName());
        txfCustomerEmail.setText(selectedProject.getCustomerEmail());
        txfCustomerLocation.setText(selectedProject.getCustomerLocation());
        lblRefNumber.setText(selectedProject.getRefNumber());
        startDate.setValue(LocalDate.parse(selectedProject.getStartDate().substring(0, 10), formatter));
        endDate.setValue(LocalDate.parse(selectedProject.getEndDate().substring(0, 10), formatter));
        txaNotes.setText(selectedProject.getNote());
        drawingLocation = selectedProject.getDrawing();
        displayDrawing(drawingLocation);
        fillDevices(projectModel.getAllDevicesForProject(selectedProject.getRefNumber()));
        fillDeviceChoiceBox();
        fillAccounts();
        List<String> imageLocations = projectModel.getPicturesForProject(selectedProject.getRefNumber());
        for (String s : imageLocations) {
            images.add(new Image(s));
        }
        displayImages();
    }

    private void displayDrawing(String drawingLocation) {
        if (drawingLocation != null)
            imvDrawing.setImage(new Image(drawingLocation));
    }

    private void fillAccounts() {
        List<Integer> connectedAccountIDs = projectModel.getAllAccountIDsForProject(lblRefNumber.getText());
        for (int i : connectedAccountIDs) {
            if (connectedAccountIDs.size() > 1 && accountModel.getAccountByID(i).getName().equals(usernameLbl.getText()))
                connectedAccountIDs.remove(i);
        }
        List<Account> allAccounts = accountModel.getAllAccounts();
        ObservableList<String> accountNames = FXCollections.observableArrayList();
        int connectedAccountLocation = -1;
        int i = 0;
        for (Account a : allAccounts) {
            accountNames.add(a.getName());
            if (connectedAccountIDs.size() > 0 && a.getId() == connectedAccountIDs.get(0))
                connectedAccountLocation = i;
            else
                i++;
        }
        accountNames.removeIf(a -> usernameLbl.getText().equals(a));
        chbSelectAccount.setItems(accountNames);
        if (connectedAccountLocation >= 0)
            chbSelectAccount.getSelectionModel().select(connectedAccountLocation);
    }

    private void fillDevices(ObservableList<Devices> allDevicesForProject) {
        devices.addAll(allDevicesForProject);
    }
    private void fillDeviceChoiceBox(){
        chbDevices.getItems().clear();
        ObservableList<String> deviceNames = FXCollections.observableArrayList();
        for (Devices d : devices) {
            deviceNames.add(d.getDeviceName());
        }
        chbDevices.setItems(deviceNames);
        chbDevices.getSelectionModel().selectFirst();
    }


    public static void saveDeviceToList(Devices device) {//TODO
        device.setRefNumber(staticRefNumber);
        devices.add(device);
        devicesToAdd ++;
        changed = !changed;
    }

    public static int getDevicesToAdd(){
        return devicesToAdd;
    }
    public void btnSavePressed() {
        if (TechnicianViewController.getSelectedProject() == null) {
            int rand = (int) (Math.random() * 10000);//TODO change
            String refNumber = txfCustomerName.getText().substring(0, 3) + txfCustomerEmail.getText().substring(0, 3) + rand;
            projectModel.createProject(new Project(
                            refNumber,
                            txfCustomerName.getText(),
                            txfCustomerEmail.getText(),
                            txfCustomerLocation.getText(),
                            txaNotes.getText(),
                            drawingLocation,
                            java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            startDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            false,
                            checkBoxPrivateProject.isSelected(),
                            chePicturesInclude.isSelected(),
                            cheDrawInclude.isSelected()),
                    accountModel.getAccountByName(LoginController.getUsername()).getId());
            for (Image img : images) {
                projectModel.createPicture(img.getUrl(), refNumber);
            }
            for (Devices d : devices) {
                d.setRefNumber(refNumber);
                projectModel.createDevice(d);
            }
            devices.clear();

            projectModel.recordLog(refNumber, accountModel.getAccountByName(LoginController.getUsername()).getId());
            JOptionPane.showMessageDialog(null, "Successfully saved Project.");
            closeWindow();

        } else {
            projectModel.editProject(new Project(lblRefNumber.getText(),
                    txfCustomerName.getText(),
                    txfCustomerEmail.getText(),
                    txfCustomerLocation.getText(),
                    txaNotes.getText(),
                    drawingLocation,
                    java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    startDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    endDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    projectModel.getProjectByRefNumber(lblRefNumber.getText()).getApproved(),
                    checkBoxPrivateProject.isSelected(),
                    chePicturesInclude.isSelected(),
                    cheDrawInclude.isSelected()));

            List<String> existingImages = projectModel.getPicturesForProject(lblRefNumber.getText());
            for (String s : existingImages) {   //I don't have time for checks, just completely refresh the db
                                                //it's bad but at least it's a solution
                projectModel.deletePicture(projectModel.getPictureIDByPath(s));
            }

            for (Image i : images) {
                projectModel.createPicture(i.getUrl(), lblRefNumber.getText());
            }

            List<Devices> existingDevices = projectModel.getAllDevicesForProject(lblRefNumber.getText());
            for (Devices d : existingDevices) { //this is such a huge code smell, but I don't have time to develop
                projectModel.removeDevice(d.getId());
            }
            for (Devices d : devices) {
                projectModel.createDevice(d);
            }

            projectModel.recordLog(lblRefNumber.getText(),
                    accountModel.getAccountByName(LoginController.getUsername()).getId());
            JOptionPane.showMessageDialog(null, "Successfully updated Project.");
        }
        closeWindow();
    }

    public void btnCancelPressed() {
        closeWindow();
    }


    public void btnAddPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images",
                "*.png", "*.jpg"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        if (files != null) {
            files.forEach((File f) -> images.add(new Image(f.toURI().toString())));
            displayImages();
        }
    }

    public void displayImages() {
        gdpPictures.getChildren().clear();
        int maxRow = 4, maxCol = 4;
        int currentRow = 0, currentCol = 0;
        gdpPictures.setHgap(10);
        gdpPictures.setVgap(10);
        for (Image i : images) {
            ImageView imageView = new ImageView(i);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.getStyleClass().add("added-pictures");

            imageView.setOnMouseClicked(e -> {
                selectedPicture = i;
                if (e.getClickCount() > 1) {
                    Stage imageStage = new Stage();
                    ImageView imageViewPopup = new ImageView(i);
                    imageViewPopup.setPreserveRatio(true);
                    imageViewPopup.setFitWidth(400);

                    StackPane stackPane = new StackPane(imageViewPopup);
                    Scene scene = new Scene(stackPane);
                    imageStage.setScene(scene);
                    imageStage.setTitle("Image Viewer");
                    imageStage.show();
                }

            });

            gdpPictures.add(imageView, currentCol, currentRow);
            currentCol++;
            if (currentCol > maxCol) {
                currentCol = 0;
                currentRow++;
                if (currentRow > maxRow)
                    gdpPictures.addRow(currentRow);
            }
        }

    }


    public void btnRemovePressed() {
        images.remove(selectedPicture);
        displayImages();
    }


    public void btnDrawPressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pl/fxml/DrawingView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Drawing");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnMinPressed() {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }

    public void btnMaxPressed() {
        Stage stage = (Stage) btnMax.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    public void btnClosePressed() {
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
        comboBoxFontSize.setValue(14);
    }


    public void addDevicePressed() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("pl/fxml/DevicesView.fxml"));
            Parent root = loader.load();

            staticRefNumber = lblRefNumber.getText();

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Device");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBackPressed() {
        closeWindow();
    }

    public void btnRemoveDevicePressed() {
        devices.remove(getDeviceFromChbByName(chbDevices.getSelectionModel().getSelectedItem()));
        fillDeviceChoiceBox();
    }

    private Devices getDeviceFromChbByName(String name) {
        Devices result = null;
        for (Devices d : devices) {
            if (d.getDeviceName().equals(name))
                result = d;
        }
        return result;
    }

    public void btnBoldPressed() {
        if (boldToggled) {
            btnBold.setStyle("-fx-background-color: -fx-dirty-white;");
        } else {
            btnBold.setStyle("-fx-background-color: -fx-dark-orange;");
        }
        boldToggled = !boldToggled;
    }

    public void btnItalicPressed() {
        if (italicToggled) {
            btnItalic.setStyle("-fx-background-color: -fx-dirty-white;");
        } else {
            btnItalic.setStyle("-fx-background-color: -fx-dark-orange;");
        }
        italicToggled = !italicToggled;
    }

    public void btnUnderlinePressed() {
        if (underlineToggled) {
            btnUnderline.setStyle("-fx-background-color: -fx-dirty-white;");
        } else {
            btnUnderline.setStyle("-fx-background-color: -fx-dark-orange;");
        }
        underlineToggled = !underlineToggled;
    }

    public static void setDrawingLocation(String location) {
        drawingLocation = location;
    }

    public static String getDrawingLocation() {
        return drawingLocation;
    }
}

