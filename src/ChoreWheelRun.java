import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class ChoreWheelRun extends Application {
    private boolean gameOver = false;
    public static final int pixelScale = 25;
    public static final int height = pixelScale*35;
    public static final int width = (4*pixelScale)*13;
    public static final int MS_PER_UPDATE = 20;
    private boolean[] keys;
    private ChoreWheel wheel;
    private Canvas canvas;
    private GraphicsContext g;
    private Scene theScene;
    private Stage theStage;

    public void start(Stage theStage) throws URISyntaxException, FileNotFoundException {
        theStage.setTitle("Breakout -- Alex Petrusca");

        wheel =  new ChoreWheel(this, width, height);;
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

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        requestFocus();
//        wheel.render(g);
//    }

    public void gameLoop() {
        double previous = System.currentTimeMillis();
        double lag = 0.0;
        while (!gameOver)
        {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            wheel.processInput(keys);

            while (lag >= MS_PER_UPDATE)
            {
                wheel.update();
                lag -= MS_PER_UPDATE;
            }
        }
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
