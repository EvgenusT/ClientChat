import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @FXML
    public TextField login;
    @FXML
    public TextField password;

    String loginClient = "";
    String passwordClient = "";
    private ControllerLogin controller;

    public void setLoginClient() {
        this.loginClient = loginClient;
    }

    public void setPasswordClient() {
        this.passwordClient = passwordClient;
    }

    public ControllerLogin(TextArea login, TextArea password, String loginClient, String passwordClient) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void pressMyButtonOk(ActionEvent actionEvent) throws IOException, InvocationTargetException {

        this.loginClient = this.login.getText();
        this.passwordClient = this.password.getText();

        Map<String, String> authentication = new HashMap<>();
        authentication.put("Login", loginClient);
        authentication.put("Password", passwordClient);
        System.out.println(authentication);

        login.clear();
        password.clear();

    }
}
