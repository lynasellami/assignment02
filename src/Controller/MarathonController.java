package Controller;

import Model.Runner;
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

    // TRACK UI
    @FXML
    private Pane racePane;

    // RUNNERS (each = PNG body + GIF legs in StackPane)
    @FXML
    private StackPane runner1Pane;

    @FXML
    private StackPane runner2Pane;

    @FXML
    private StackPane runner3Pane;

    @FXML
    private StackPane runner4Pane;

    // BUTTONS
    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button exitButton;

    // MESSAGES
    @FXML
    private Label messageLabel;

    @FXML
    private TextArea messageArea;

    // ANIMATION
    private ParallelTransition raceAnimation;
    private boolean raceCreated = false;
    private boolean raceRunning = false;
    private boolean winnerDeclared = false;

    // MODEL
    private java.util.List<Runner> runners = new java.util.ArrayList<>();

    @FXML
    public void initialize() {
        if (messageArea != null) {
            messageArea.setText("Welcome to the Marathon Simulator! (logic coming soon)");
        }
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }

        // Create runner model objects
        createRunnerModels();
    }

    @FXML
    private void handleStart() {
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

    // ----------------------------------------------------------
    // BASIC RACE ANIMATION (now uses Runner baseSpeed)
    // ----------------------------------------------------------
    private void createBasicRaceAnimation() {

        if (runners.isEmpty()) {
            appendMessage("No runner models found!");
            return;
        }

        winnerDeclared = false;

        double distance = 600; // track distance

        // Build animation transitions using model speeds
        TranslateTransition t1 = new TranslateTransition(
                Duration.seconds(8 / runners.get(0).getBaseSpeed()),
                runners.get(0).getUiPane()
        );
        t1.setToX(distance);

        TranslateTransition t2 = new TranslateTransition(
                Duration.seconds(8 / runners.get(1).getBaseSpeed()),
                runners.get(1).getUiPane()
        );
        t2.setToX(distance);

        TranslateTransition t3 = new TranslateTransition(
                Duration.seconds(8 / runners.get(2).getBaseSpeed()),
                runners.get(2).getUiPane()
        );
        t3.setToX(distance);

        TranslateTransition t4 = new TranslateTransition(
                Duration.seconds(8 / runners.get(3).getBaseSpeed()),
                runners.get(3).getUiPane()
        );
        t4.setToX(distance);

        // Winner detection using model data
        t1.setOnFinished(e -> declareWinnerIfFirst(runners.get(0)));
        t2.setOnFinished(e -> declareWinnerIfFirst(runners.get(1)));
        t3.setOnFinished(e -> declareWinnerIfFirst(runners.get(2)));
        t4.setOnFinished(e -> declareWinnerIfFirst(runners.get(3)));

        raceAnimation = new ParallelTransition(t1, t2, t3, t4);
    }

    // ----------------------------------------------------------
    // DECLARE WINNER (first runner to finish)
    // ----------------------------------------------------------
    private void declareWinnerIfFirst(Runner r) {
        if (winnerDeclared) return;

        winnerDeclared = true;

        appendMessage("Winner: " + r.getName() + " (#" + r.getNumber() + ") 🎉");
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status – Winner Announced");
        }
    }

    // ----------------------------------------------------------
    // CREATE RUNNER MODEL OBJECTS
    // ----------------------------------------------------------
    private void createRunnerModels() {
        runners.clear();

        runners.add(new Runner("Finn",       33, 1.0, runner1Pane));
        runners.add(new Runner("Bubblegum",  44, 1.0, runner2Pane));
        runners.add(new Runner("Jake",       22, 1.0, runner3Pane));
        runners.add(new Runner("Marceline",  11, 1.0, runner4Pane));

        appendMessage("Runner models created (names, numbers, speeds).");
    }

    // ----------------------------------------------------------
    // MESSAGE HELPER
    // ----------------------------------------------------------
    private void appendMessage(String text) {
        if (messageArea == null) return;

        if (messageArea.getText().isEmpty()) {
            messageArea.setText(text);
        } else {
            messageArea.appendText("\n" + text);
        }
    }
}
