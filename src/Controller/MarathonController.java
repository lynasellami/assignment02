package Controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MarathonController {

    // Center area where the race is drawn
    @FXML
    private Pane racePane;

    // Runner panes (each = PNG body + GIF legs)
    @FXML
    private StackPane runner1Pane;

    @FXML
    private StackPane runner2Pane;

    @FXML
    private StackPane runner3Pane;

    @FXML
    private StackPane runner4Pane;

    // Bottom buttons
    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button exitButton;

    // Status / messages on the bottom right
    @FXML
    private Label messageLabel;

    @FXML
    private TextArea messageArea;

    // Animation objects
    private ParallelTransition raceAnimation;
    private boolean raceCreated = false;
    private boolean raceRunning = false;
    private boolean winnerDeclared = false;

    @FXML
    public void initialize() {
        if (messageArea != null) {
            messageArea.setText("Welcome to the Marathon Simulator! (logic coming soon)");
        }
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }
    }

    @FXML
    private void handleStart() {
        // First time: create the basic animation
        if (!raceCreated) {
            createBasicRaceAnimation();
            raceCreated = true;
            appendMessage("Created basic race animation (test version).");
        }

        if (raceAnimation != null) {
            raceAnimation.play();
            raceRunning = true;
            appendMessage("Race started! Runners are moving toward the finish line.");
        }
    }

    @FXML
    private void handlePause() {
        if (raceAnimation != null && raceRunning) {
            raceAnimation.pause();
            raceRunning = false;
            appendMessage("Race paused.");
        }
    }

    @FXML
    private void handleReset() {
        if (raceAnimation != null) {
            raceAnimation.stop();
        }
        raceRunning = false;
        raceCreated = false;
        winnerDeclared = false;

        if (runner1Pane != null) runner1Pane.setTranslateX(0);
        if (runner2Pane != null) runner2Pane.setTranslateX(0);
        if (runner3Pane != null) runner3Pane.setTranslateX(0);
        if (runner4Pane != null) runner4Pane.setTranslateX(0);

        appendMessage("Runners reset to the start line. Press Start to run again.");
    }

    @FXML
    private void handleExit() {
        appendMessage("Exit button clicked. Closing application.");
        Platform.exit();
    }

    /**
     * Creates a simple TranslateTransition for each runner.
     * This version also declares a winner when the first one finishes.
     */
    private void createBasicRaceAnimation() {
        if (runner1Pane == null || runner2Pane == null ||
                runner3Pane == null || runner4Pane == null) {
            appendMessage("Runners not ready yet – cannot create animation.");
            return;
        }

        winnerDeclared = false;

        // Rough distance from start to finish
        double distance = 600; // we can refine this later using racePane width

        // Different durations so they don't all tie
        TranslateTransition t1 = new TranslateTransition(Duration.seconds(6), runner1Pane);
        t1.setToX(distance);

        TranslateTransition t2 = new TranslateTransition(Duration.seconds(6.5), runner2Pane);
        t2.setToX(distance);

        TranslateTransition t3 = new TranslateTransition(Duration.seconds(7), runner3Pane);
        t3.setToX(distance);

        TranslateTransition t4 = new TranslateTransition(Duration.seconds(7.5), runner4Pane);
        t4.setToX(distance);

        // When each runner finishes, check if we already have a winner
        t1.setOnFinished(e -> declareWinnerIfFirst("Runner 1 (purple)"));
        t2.setOnFinished(e -> declareWinnerIfFirst("Runner 2 (blue)"));
        t3.setOnFinished(e -> declareWinnerIfFirst("Runner 3 (star shirt)"));
        t4.setOnFinished(e -> declareWinnerIfFirst("Runner 4 (pink)"));

        raceAnimation = new ParallelTransition(t1, t2, t3, t4);
    }

    /**
     * Declares the winner only once – the first transition that finishes wins.
     */
    private void declareWinnerIfFirst(String winnerName) {
        if (winnerDeclared) {
            return;
        }
        winnerDeclared = true;

        appendMessage("Winner: " + winnerName + " 🎉");
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status – Winner Announced");
        }
    }

    // Helper to write into the message area
    private void appendMessage(String text) {
        if (messageArea == null) {
            return;
        }
        if (messageArea.getText().isEmpty()) {
            messageArea.setText(text);
        } else {
            messageArea.appendText("\n" + text);
        }
    }
}
