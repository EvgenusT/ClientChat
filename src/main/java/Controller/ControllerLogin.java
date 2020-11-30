package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ControllerLogin {

    @FXML
    private Button button_check_in;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button button_entry;

    public static Stage primaryStage;

    public ControllerLogin() throws UnsupportedEncodingException {
    }

    @FXML
    void putButtonCheckIn(ActionEvent event) {

    }
    Controller controller = new Controller();

    @FXML
    void putBottonEntry(ActionEvent event) {
        button_entry.setOnAction(event1 -> {
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
            primaryStage.showAndWait();


        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                controller.stop();
                System.exit(0);
            }
        });

    }



}

