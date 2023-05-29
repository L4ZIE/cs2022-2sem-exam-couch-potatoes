package pl.controllers;

import be.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class AccountVIewController implements Initializable {
    @FXML
    public AnchorPane anpMain;
    @FXML
    public TextField txfSearch;
    @FXML
    public TableColumn<Object, Object> colUsername;
    @FXML
    public TableColumn<Object, Object> colAccountType;
    @FXML
    public TableView<Account> tbvAccounts;
    private AccountModel accountModel;
    private double xOffset;
    private double yOffset;
    private static Account selectedAccount;
    public static Boolean needsRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountModel = new AccountModel();
        anpMain.setOnMousePressed(this::handleMousePressed);
        anpMain.setOnMouseDragged(this::handleMouseDragged);
        fillAccountsTable(tbvAccounts);
        needsRefresh = true;
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

    private void fillAccountsTable(TableView<Account> accountTableView) {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAccountType.setCellValueFactory(new PropertyValueFactory<>("type"));

        accountTableView.setItems(accountModel.getAllAccounts());

        accountTableView.getSelectionModel().select(0);
        needsRefresh = false;
    }

    public void searchFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            searchForAccounts();
    }

    public void btnSearchPressed() {
        searchForAccounts();
    }

    private void searchForAccounts() {
        if (accountModel.searchForAccounts(txfSearch.getText()) != null)
            tbvAccounts.setItems(accountModel.searchForAccounts(txfSearch.getText()));
        else
            fillAccountsTable(tbvAccounts);
    }

    public void minBtnPressed() {
        Stage stage = (Stage) anpMain.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeBtnPressed() {
        Stage stage = (Stage) anpMain.getScene().getWindow();
        stage.close();
    }

    public void btnCreateAccountPressed() {
        selectedAccount = null;
        openCreateWindow();
    }

    public void btnEditAccountPressed() {
        if (tbvAccounts.getSelectionModel().getSelectedItem() != null)
            selectedAccount = tbvAccounts.getSelectionModel().getSelectedItem();
        else
            JOptionPane.showMessageDialog(null, "Please select an account to edit.");
        openCreateWindow();
    }

    private void openCreateWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/fxml/CreateAccountView.fxml"));

        try {
            Scene scene = new Scene(loader.load());
            Stage stageCreate = new Stage();

            stageCreate.setTitle("Create Account");
            stageCreate.initModality(Modality.APPLICATION_MODAL);
            stageCreate.setScene(scene);
            stageCreate.initStyle(StageStyle.UNDECORATED);
            stageCreate.show();

            stageCreate.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnRemoveAccount() {
        accountModel.deleteAccount(tbvAccounts.getSelectionModel().getSelectedItem().getId());
        fillAccountsTable(tbvAccounts);
    }
    public static Account getSelectedAccount(){
        return selectedAccount;
    }

    public static void refreshTable(){
        needsRefresh = true;
    }

    public void checkRefreshTable() {
        if (needsRefresh){
            fillAccountsTable(tbvAccounts);
            needsRefresh = false;
        }

    }
}
