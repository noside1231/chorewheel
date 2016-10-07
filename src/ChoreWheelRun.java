import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class ChoreWheelRun extends Application {
    public static double scale = 3;
    private boolean gameOver = false;
    public static final double width = (int) (277.5 * scale);
    public static final double height = (int) (185  * scale);
    private ChoreWheel wheel;
    private Canvas canvas;
    private GraphicsContext g;
    private Scene theScene;
    private Stage theStage;

    public void start(Stage theStage) throws URISyntaxException, FileNotFoundException {
        theStage.setTitle("Chore Wheel #LIT");

        wheel = new ChoreWheel(this);

        theScene = new Scene(wheel, width, height, Color.WHITE);
        theStage.setScene(theScene);
        canvas = new Canvas(width, height);

        canvas.setCacheHint(CacheHint.SPEED);
        wheel.setCacheHint(CacheHint.SPEED);

        wheel.getChildren().add(canvas);
        g = canvas.getGraphicsContext2D();
        this.theStage = theStage;
        wheel.addEventHandlers();

        final long startNanoTime = System.nanoTime();
        AnimationTimer gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1e9;
                wheel.runGame(t);
            }
        };
        gameLoop.start();
        theStage.show();
    }

    public Scene getScene() {
        return theScene;
    }

    public Stage getStage() {
        return theStage;
    }

    public GraphicsContext getGlassG() {
        return g;
    }

    public void endLoop() {
        gameOver = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
