package Model;

import javafx.scene.layout.StackPane;

public class Runner {

    private String name;
    private int number;
    private double baseSpeed;
    private StackPane uiPane;

    public Runner(String name, int number, double baseSpeed, StackPane uiPane) {
        this.name = name;
        this.number = number;
        this.baseSpeed = baseSpeed;
        this.uiPane = uiPane;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public StackPane getUiPane() {
        return uiPane;
    }
}
