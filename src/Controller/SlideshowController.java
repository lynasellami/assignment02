package Controller;

import Main.MarathonMain;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SlideshowController {

    @FXML
    private ImageView slideshowImage;

    @FXML
    private Label captionLabel;

    @FXML
    private Button skipButton;

    @FXML
    private StackPane imageContainer;

    private int index = 0;

    private final String[] images = {
            "/Images/runner1.png",
            "/Images/runner2.png",
            "/Images/runner3.png",
            "/Images/runner4.png"
    };

    private final String[] captions = {
            "Get ready: Finn (#11)",
            "Get ready: Bubblegum (#22)",
            "Get ready: Jake (#33)",
            "Get ready: Marceline (#44)"
    };

    @FXML
    public void initialize() {
        runSlideshow();

        skipButton.setOnAction(e -> {
            try {
                MarathonMain.switchToRaceView();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void runSlideshow() {
        showImage(images[index], captions[index]);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), slideshowImage);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setOnFinished(e -> nextImage());
        fade.play();
    }

    private void nextImage() {
        index++;
        if (index >= images.length) {
            try {
                MarathonMain.switchToRaceView();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        showImage(images[index], captions[index]);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), slideshowImage);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setOnFinished(e -> nextImage());
        fade.play();
    }

    private void showImage(String path, String caption) {
        Image img = new Image(path);
        slideshowImage.setImage(img);
        captionLabel.setText(caption);
    }
}
