package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SlideshowController {

    @FXML
    private ImageView slideshowImage;

    @FXML
    private Label captionLabel;

    @FXML
    private StackPane imageContainer;

    @FXML
    private Button skipButton;

    private List<Image> images = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {

        // Load slideshow images
        images.add(new Image(getClass().getResource("/Images/runner1.png").toString()));
        images.add(new Image(getClass().getResource("/Images/runner2.png").toString()));
        images.add(new Image(getClass().getResource("/Images/runner3.png").toString()));
        images.add(new Image(getClass().getResource("/Images/runner4.png").toString()));

        skipButton.setOnAction(e -> goToRace());

        startSlideshow();
    }

    private void startSlideshow() {
        if (index >= images.size()) {
            goToRace();
            return;
        }

        slideshowImage.setImage(images.get(index));
        captionLabel.setText("Runner " + (index + 1));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), slideshowImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition hold = new PauseTransition(Duration.seconds(2));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), slideshowImage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            index++;
            startSlideshow();
        });

        fadeIn.play();
        fadeIn.setOnFinished(ev -> hold.play());
        hold.setOnFinished(ev -> fadeOut.play());
    }

    private void goToRace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/marathon.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) skipButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
