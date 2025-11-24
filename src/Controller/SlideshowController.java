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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SlideshowController {

    @FXML
    private ImageView slideshowImage;

    @FXML
    private Label captionLabel;

    @FXML
    private StackPane imageContainer;

    @FXML
    private Button skipButton;

    private final List<Image> images = new ArrayList<>();
    private final List<String> captions = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {

        // Load slideshow images from /Images
        images.add(new Image(Objects.requireNonNull(
                getClass().getResource("/Images/runner1.png")).toString()));
        images.add(new Image(Objects.requireNonNull(
                getClass().getResource("/Images/runner2.png")).toString()));
        images.add(new Image(Objects.requireNonNull(
                getClass().getResource("/Images/runner3.png")).toString()));
        images.add(new Image(Objects.requireNonNull(
                getClass().getResource("/Images/runner4.png")).toString()));

        captions.add("Get ready: Finn (#11)");
        captions.add("Get ready: Bubblegum (#22)");
        captions.add("Get ready: Jake (#33)");
        captions.add("Get ready: Marceline (#44)");

        // Set up Skip button
        skipButton.setOnAction(e -> switchToRace());

        // Start slideshow
        playCurrentImage();
    }

    private void playCurrentImage() {
        if (index >= images.size()) {
            switchToRace();
            return;
        }

        slideshowImage.setImage(images.get(index));
        captionLabel.setText(captions.get(index));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), slideshowImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition hold = new PauseTransition(Duration.seconds(1.5));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), slideshowImage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            index++;
            playCurrentImage();
        });

        fadeIn.play();
        fadeIn.setOnFinished(e -> hold.play());
        hold.setOnFinished(e -> fadeOut.play());
    }

    private void switchToRace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/marathon.fxml"));
            Scene raceScene = new Scene(loader.load(), 1100, 700);
            Stage stage = (Stage) skipButton.getScene().getWindow();
            stage.setScene(raceScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
