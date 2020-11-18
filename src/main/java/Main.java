import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

        public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/sample.fxml"));
        primaryStage.setTitle("BalTalOchKa v 2.0");
        primaryStage.setScene(new Scene(root, 500.0D, 600.0D));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
