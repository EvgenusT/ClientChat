package Controller;

import DataUser.Users;
import Utils.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerCheckIn {

    @FXML
    public TextField id_logincheckIn;

    @FXML
    public TextField id_passwordCI;

    @FXML
    private Button id_botton_checkIn;

    @FXML
    void putChekInButton(ActionEvent event) {
               signAddNewUser();

        id_botton_checkIn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerLogin.class.getResource("FXML/login.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    private void signAddNewUser() {
        DBConnect dbConnect = new DBConnect();
        String login = id_logincheckIn.getText();
        String password = id_passwordCI.getText();
        Users user = new Users(login, password);
        dbConnect.signAddUser(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Вас успешно зарегистрировано в чате");
        alert.showAndWait();
    }
}