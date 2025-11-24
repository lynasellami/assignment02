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

    // RUNNERS (PNG body + GIF legs inside StackPane)
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
            messageArea.setText("Welcome to the Marathon Simulator!");
        }
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }

        // Create randomized runner models for the first race
        createRunnerModels();
    }

    @FXML
    private void handleStart() {
        if (!raceCreated) {
            createBasicRaceAnimation();
            raceCreated = true;
            appendMessage("Created race animation for this round.");
        }

        if (raceAnimation != null) {
            raceAnimation.play();
            raceRunning = true;
            appendMessage("Race started!");
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

        // Reset runners to start line
        if (runner1Pane != null) runner1Pane.setTranslateX(0);
        if (runner2Pane != null) runner2Pane.setTranslateX(0);
        if (runner3Pane != null) runner3Pane.setTranslateX(0);
        if (runner4Pane != null) runner4Pane.setTranslateX(0);

        appendMessage("Runners reset. New speeds will be generated.");

        // New race = new speeds
        createRunnerModels();
    }

    @FXML
    private void handleExit() {
        appendMessage("Closing application...");
        Platform.exit();
    }

    // ----------------------------------------------------------
    // CREATE BASIC RACE ANIMATION USING MODEL SPEEDS
    // ----------------------------------------------------------
    private void createBasicRaceAnimation() {

        if (runners.isEmpty()) {
            appendMessage("ERROR: No runner models found.");
            return;
        }

        winnerDeclared = false;

        double distance = 600; // from start to finish

        // Build TranslateTransitions using model speeds
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
    // WINNER LOGIC
    // ----------------------------------------------------------
    private void declareWinnerIfFirst(Runner r) {
        if (winnerDeclared) return;

        winnerDeclared = true;

        appendMessage("🏆 Winner: " + r.getName() + " (#" + r.getNumber() + ")");
        if (messageLabel != null) {
            messageLabel.setText("Winner Announced!");
        }
    }

    // ----------------------------------------------------------
    // RANDOMIZE RUNNER MODELS FOR EACH RACE
    // ----------------------------------------------------------
    private void createRunnerModels() {
        runners.clear();

        java.util.Random rand = new java.util.Random();

        // Randomized base speeds — small variations
        double finnSpeed       = 1.3 + (rand.nextDouble() * 0.3 - 0.15);  // 1.15–1.45
        double bubblegumSpeed  = 1.0 + (rand.nextDouble() * 0.25 - 0.12); // 0.88–1.12
        double jakeSpeed       = 1.6 + (rand.nextDouble() * 0.35 - 0.17); // 1.43–1.77
        double marcelineSpeed  = 1.1 + (rand.nextDouble() * 0.25 - 0.12); // 0.98–1.22

        runners.add(new Runner("Finn",       33, finnSpeed, runner1Pane));
        runners.add(new Runner("Bubblegum",  44, bubblegumSpeed, runner2Pane));
        runners.add(new Runner("Jake",       22, jakeSpeed, runner3Pane));
        runners.add(new Runner("Marceline",  11, marcelineSpeed, runner4Pane));

        appendMessage("New speeds generated for this race:");
        appendMessage("Finn: " + String.format("%.2f", finnSpeed));
        appendMessage("Bubblegum: " + String.format("%.2f", bubblegumSpeed));
        appendMessage("Jake: " + String.format("%.2f", jakeSpeed));
        appendMessage("Marceline: " + String.format("%.2f", marcelineSpeed));
    }

    // ----------------------------------------------------------
    // LOGGING / MESSAGE HELPER
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
