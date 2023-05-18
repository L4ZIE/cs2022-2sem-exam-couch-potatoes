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
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.models.AccountModel;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Button btnMin,
            btnClose,
            btnLogin;
    @FXML
    private TextField txfUsername;
    @FXML
    private PasswordField pwfPassword;

    private AccountModel accountModel;

    private static String username;
    private static AccountType accountType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountModel = new AccountModel();
    }
    public void minBtnPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeBtnPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void btnLoginPressed(ActionEvent actionEvent) {
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
    public static void bringUpWindow(){

    }

}
