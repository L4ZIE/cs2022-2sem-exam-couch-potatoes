package pl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pl.models.AccountModel;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    public Button btnMin,
            btnClose,
            btnLogin;
    @FXML
    public TextField txfUsername;
    @FXML
    public PasswordField pwfPassword;

    AccountModel accountModel;

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
        if(accountModel.checkCredentials(txfUsername.getText(), pwfPassword.getText())){
            System.out.println("yes");
        }
        else
            System.out.println("no");
    }


}
