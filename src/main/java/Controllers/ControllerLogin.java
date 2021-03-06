package Controllers;

import DataUser.Users;
import Utils.DBConnect;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerLogin {

    String localAddress = "Local connect"; // это IP-адрес компьютера, где исполняется наша серверная программа.
    String address = "Network connected"; // это IP-адрес компьютера, где исполняется наша серверная программа.

    ObservableList<String> localizationList = FXCollections.observableArrayList(localAddress, address);

    @FXML
    public ChoiceBox switchLocalization;

    @FXML
    public void initialize() {
        switchLocalization.setValue("Local connect");
        switchLocalization.setItems(localizationList);
    }

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button button_entry;

    public ControllerLogin() throws UnsupportedEncodingException {
    }
    Users user = new Users();

    //нажатие кнопки регистрация
    @FXML
    void putButtonCheckIn(ActionEvent event) {
        button_entry.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerLogin.class.getResource("/FXML//checkIn.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.showAndWait();
    }

    //нажатие кнопки входа в чат
    @FXML
    void putBottonEntry(ActionEvent event) {

        String loginText = login.getText().trim();
        String loginPassword = password.getText().trim();


        if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
            checkOk(loginText, loginPassword);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не введен логин или пароль!");
            alert.showAndWait();
        }
        if (checkOk(loginText, loginPassword)) {
            button_entry.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ControllerLogin.class.getResource("/FXML/sample.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    Controller.stop();
                    Platform.exit();
                    System.exit(0);
                }
            })
            ;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не правильно введен логин или пароль!\n Сосредоточься !");
            alert.showAndWait();
        }
    }

    private boolean checkOk(String loginText, String loginPassword) {
        String adresRes = switchLocalization.getValue().toString();
        sourceSelection(adresRes);
        boolean check = false;
        DBConnect dbConnect = new DBConnect();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultSet = dbConnect.getUseData(user);

        int counterUser = 0;

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            counterUser++;
        }
        if (counterUser >= 1) {
            check = true;
        }
        return check;
    }

    private String sourceSelection (String adresRes){

        return adresRes;
    }
}

