package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MarathonMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // NOTE: View with capital V, matching the folder name
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/marathon.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Marathon Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
