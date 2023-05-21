package pl.controllers;

import be.Account;
import be.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.models.AccountModel;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {
    @FXML
    public Label lblTitle;
    @FXML
    public TextField txfUsername,
            txfPassword;
    @FXML
    public ChoiceBox<be.AccountType> chbAccountType;
    @FXML
    public AnchorPane anpMain;
    private double xOffset;
    private double yOffset;
    private AccountModel accountModel;
    private Account selectedAccount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anpMain.setOnMousePressed(this::handleMousePressed);
        anpMain.setOnMouseDragged(this::handleMouseDragged);
        accountModel = new AccountModel();
        chbAccountType.setItems(FXCollections.observableArrayList(AccountType.values()));
        selectedAccount = AccountVIewController.getSelectedAccount();
        if (selectedAccount != null) {
            txfUsername.setText(selectedAccount.getName());
            chbAccountType.getSelectionModel().select(selectedAccount.getType());
        } else
            chbAccountType.getSelectionModel().select(1);
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

    public void minBtnPressed() {
        Stage stage = (Stage) anpMain.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeBtnPressed() {
        Stage stage = (Stage) anpMain.getScene().getWindow();
        stage.close();
    }

    public void btnSavePressed() {
        if (AccountVIewController.getSelectedAccount() == null){
            if (!txfPassword.getText().equals("") && !txfUsername.getText().equals("")) {
                accountModel.createAccount(new Account(accountModel.getMaxID() + 1, txfUsername.getText(),
                        txfPassword.getText(), chbAccountType.getSelectionModel().getSelectedItem()));

                JOptionPane.showMessageDialog(null, "Successfully created account.");
                closeBtnPressed();
            }
            else{
                if(txfPassword.getText().equals("") && txfUsername.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Username and password field is empty.");
                else if (txfUsername.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Username field is empty.");
                else
                    JOptionPane.showMessageDialog(null, "Password field is empty.");
            }


        }

        else { //no time to optimize
            accountModel.changeAccountName(selectedAccount.getId(), txfUsername.getText());
            if (txfPassword.getText() != null)
                accountModel.changePassword(selectedAccount.getId(), txfPassword.getText());
            accountModel.changeAccountType(selectedAccount.getId(), chbAccountType.getSelectionModel().getSelectedItem());
            JOptionPane.showMessageDialog(null, "Successfully updated account.");
            closeBtnPressed();
        }
        AccountVIewController.refreshTable();


    }


}
