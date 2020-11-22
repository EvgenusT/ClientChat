import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/sample.fxml"));
        primaryStage.setTitle("BalTalOchKa v 1.0");
        primaryStage.setScene(new Scene(root));
        runStage(primaryStage);
    }
    public static void runStage(Stage stage) throws IOException {
        primaryStage  = stage;
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
