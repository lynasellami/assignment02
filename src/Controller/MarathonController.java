package Controller;

import Model.Runner;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarathonController {

    @FXML
    private Pane racePane;

    @FXML
    private StackPane runner1Pane;

    @FXML
    private StackPane runner2Pane;

    @FXML
    private StackPane runner3Pane;

    @FXML
    private StackPane runner4Pane;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label messageLabel;

    @FXML
    private TextArea messageArea;

    private ParallelTransition raceAnimation;
    private boolean raceCreated = false;
    private boolean raceRunning = false;
    private boolean winnerDeclared = false;
    private boolean countdownInProgress = false;

    private final List<Runner> runners = new ArrayList<>();

    @FXML
    public void initialize() {
        if (messageArea != null) {
            messageArea.setText("Welcome to the Marathon Simulator!");
        }
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }

        // First set of runner speeds
        createRunnerModels();

        // Layout / animation info for the teacher
        appendMessage("Layout: BorderPane for the main window, Pane for the track, " +
                "StackPane for each runner (body + running legs), and VBox/HBox for controls/messages.");
        appendMessage("Animation: each runner uses a TranslateTransition and they run together in a ParallelTransition.");
    }

    @FXML
    private void handleStart() {
        // If countdown is already running, ignore clicks
        if (countdownInProgress) {
            return;
        }

        // If race was paused and already created -> just resume (no new countdown)
        if (raceAnimation != null && raceCreated && !raceRunning) {
            raceAnimation.play();
            raceRunning = true;
            appendMessage("Race resumed.");
            return;
        }

        // First time starting this race
        if (!raceCreated) {
            createBasicRaceAnimation();
            raceCreated = true;
            appendMessage("Created race animation for this round.");
        }

        // Start 3-2-1-GO countdown then play
        startCountdown();
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
        countdownInProgress = false;

        if (runner1Pane != null) runner1Pane.setTranslateX(0);
        if (runner2Pane != null) runner2Pane.setTranslateX(0);
        if (runner3Pane != null) runner3Pane.setTranslateX(0);
        if (runner4Pane != null) runner4Pane.setTranslateX(0);

        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }

        appendMessage("Runners reset. New speeds will be generated.");

        // New race = new randomized speeds
        createRunnerModels();
    }

    @FXML
    private void handleExit() {
        appendMessage("Closing application...");
        Platform.exit();
    }

    // ---------------------- race animation ----------------------

    private void createBasicRaceAnimation() {

        if (runners.isEmpty()) {
            appendMessage("ERROR: No runner models found.");
            return;
        }

        winnerDeclared = false;

        // distance adjusted so they reach the finish line
        double distance = 730;

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

        t1.setOnFinished(e -> declareWinnerIfFirst(runners.get(0)));
        t2.setOnFinished(e -> declareWinnerIfFirst(runners.get(1)));
        t3.setOnFinished(e -> declareWinnerIfFirst(runners.get(2)));
        t4.setOnFinished(e -> declareWinnerIfFirst(runners.get(3)));

        raceAnimation = new ParallelTransition(t1, t2, t3, t4);

        appendMessage("Animation created: TranslateTransition for each runner combined in a ParallelTransition.");
    }

    // ---------------------- countdown ---------------------------

    private void startCountdown() {
        countdownInProgress = true;

        if (startButton != null) startButton.setDisable(true);
        if (pauseButton != null) pauseButton.setDisable(true);
        if (resetButton != null) resetButton.setDisable(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> setStatusText("3")),
                new KeyFrame(Duration.seconds(1), e -> setStatusText("2")),
                new KeyFrame(Duration.seconds(2), e -> setStatusText("1")),
                new KeyFrame(Duration.seconds(3), e -> setStatusText("GO!")),
                new KeyFrame(Duration.seconds(3.5), e -> {
                    setStatusText("Marathon Status");
                    countdownInProgress = false;

                    if (startButton != null) startButton.setDisable(false);
                    if (pauseButton != null) pauseButton.setDisable(false);
                    if (resetButton != null) resetButton.setDisable(false);

                    if (raceAnimation != null) {
                        raceAnimation.play();
                        raceRunning = true;
                        appendMessage("Race started! Runners are moving toward the finish line.");
                    }
                })
        );

        timeline.play();
    }

    private void setStatusText(String text) {
        if (messageLabel != null) {
            messageLabel.setText(text);
        }
    }

    // ---------------------- winner logic ------------------------

    private void declareWinnerIfFirst(Runner r) {
        if (winnerDeclared) return;

        winnerDeclared = true;
        raceRunning = false;

        appendMessage("🏆 Winner: " + r.getName() + " (#" + r.getNumber() + ")");
        if (messageLabel != null) {
            messageLabel.setText("Winner: " + r.getName());
        }
    }

    // ---------------------- models / speeds ---------------------

    private void createRunnerModels() {
        runners.clear();

        Random rand = new Random();

        double finnSpeed       = 1.3 + (rand.nextDouble() * 0.3 - 0.15);
        double bubblegumSpeed  = 1.0 + (rand.nextDouble() * 0.25 - 0.12);
        double jakeSpeed       = 1.6 + (rand.nextDouble() * 0.35 - 0.17);
        double marcelineSpeed  = 1.1 + (rand.nextDouble() * 0.25 - 0.12);

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

    // ---------------------- logging helper ----------------------

    private void appendMessage(String text) {
        if (messageArea == null) return;

        if (messageArea.getText().isEmpty()) {
            messageArea.setText(text);
        } else {
            messageArea.appendText("\n" + text);
        }
    }
}
