package pl.controllers;

import be.AccountType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.models.AccountModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    public AnchorPane anpMain;
    @FXML
    private Button btnMin,
            btnClose;
    @FXML
    private TextField txfUsername;
    @FXML
    private PasswordField pwfPassword;

    private AccountModel accountModel;

    private static String username;
    private static AccountType accountType;
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountModel = new AccountModel();
        anpMain.setOnMousePressed(this::handleMousePressed);
        anpMain.setOnMouseDragged(this::handleMouseDragged);
    }
    public void minBtnPressed() {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeBtnPressed() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void btnLoginPressed() {
        login();
    }

    public void pwfPasswordKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER))
            login();
    }

    private void login() {
        //Bob, 123
        //Bob CEO, 567
        if(accountModel.checkCredentials(txfUsername.getText(), pwfPassword.getText())){
            Stage stage = (Stage) btnMin.getScene().getWindow();
            stage.setIconified(true);

            username = txfUsername.getText();
            accountType = accountModel.getAccountTypeByName(username);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/fxml/TechnicianView.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                stage = new Stage();

                stage.setTitle(username);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else
            JOptionPane.showMessageDialog(null, "Wrong username or password.");
    }


    public static String getUsername(){
        return username;
    }
    public static AccountType getAccountType(){
        return accountType;
    }
    //TODO later if have time
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

}
