import Controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class Main extends Application {

    public static Stage primaryStage;

//    Controller controller = new Controller();

    public Main() throws UnsupportedEncodingException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
        primaryStage.setTitle("BalTalOchKa v 1.1");
        primaryStage.setScene(new Scene(root));
        runStage(primaryStage);

        /*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                controller.stop();
                System.exit(0);
            }
        })*/;

           }

    public void runStage(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }


}
