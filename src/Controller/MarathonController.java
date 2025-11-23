package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class MarathonController {

    // Center area where the race will be drawn later
    @FXML
    private Pane racePane;

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

    @FXML
    public void initialize() {
        // TODO: Initialize model, set default UI state
        if (messageArea != null) {
            messageArea.setText("Welcome to the Marathon Simulator! (logic coming soon)");
        }
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }
    }

    @FXML
    private void handleStart() {
        // TODO: Start or resume the marathon simulation
        appendMessage("Start button clicked (race logic not implemented yet).");
    }

    @FXML
    private void handlePause() {
        // TODO: Pause the simulation
        appendMessage("Pause button clicked (pause logic not implemented yet).");
    }

    @FXML
    private void handleReset() {
        // TODO: Reset the race state and UI
        appendMessage("Reset button clicked (reset logic not implemented yet).");
    }

    @FXML
    private void handleExit() {
        // TODO: Close the application
        appendMessage("Exit button clicked (application will close when implemented).");
    }

    // Small helper to write into the message area
    private void appendMessage(String text) {
        if (messageArea == null) {
            return;
        }
        if (messageArea.getText().isEmpty()) {
            messageArea.setText(text);
        } else {
            messageArea.appendText("\n" + text);
        }
        if (messageLabel != null) {
            messageLabel.setText("Marathon Status");
        }
    }
}
