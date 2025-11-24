package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MarathonMain extends Application {

    private static Stage primaryStageRef;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageRef = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/slideshow.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        primaryStage.setTitle("Marathon Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void switchToRaceView() throws Exception {
        FXMLLoader loader = new FXMLLoader(MarathonMain.class.getResource("/View/marathon.fxml"));
        Scene raceScene = new Scene(loader.load(), 1100, 700);
        primaryStageRef.setScene(raceScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
