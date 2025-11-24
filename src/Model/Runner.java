package Model;

import javafx.scene.layout.StackPane;

public class Runner {

    private String name;
    private int number;
    private double baseSpeed;
    private StackPane uiPane;   // link to the runner's UI node

    public Runner(String name, int number, double baseSpeed, StackPane uiPane) {
        this.name = name;
        this.number = number;
        this.baseSpeed = baseSpeed;
        this.uiPane = uiPane;
    }

    // ----- getters and setters -----

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public StackPane getUiPane() {
        return uiPane;
    }

    public void setUiPane(StackPane uiPane) {
        this.uiPane = uiPane;
    }
}
