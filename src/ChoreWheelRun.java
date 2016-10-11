import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class ChoreWheelRun extends Application {
    public static double scale = 3;
    private boolean gameOver = false;
    public static final double width = (int) (160 * scale);
    public static final double height = (int) (175  * scale);
    private ChoreWheel wheel;
    private MainMenu menu;
    private Canvas canvas;
    private GraphicsContext g;
    protected Scene spinScene;
    private Scene mainMenuScene;
    protected Stage theStage;

    Button spinButton, configButton;
    Label lblscene1, lblscene2;
    FlowPane pane1, pane2;
    Scene scene1, scene2;


    public void start(Stage theStage) throws URISyntaxException, FileNotFoundException {
        theStage.setTitle("Chore Wheel #LIT");

        wheel = new ChoreWheel(this);
        menu = new MainMenu(this);

        spinScene = new Scene(wheel, width, height, Color.WHITE);
        mainMenuScene = new Scene(menu, width,height, Color.WHITE);
        canvas = new Canvas(width, height);

        //pane1.getChildren().addAll(lblscene1,spinButton, configButton);
        //pane2.getChildren().addAll(lblscene2, configButton);

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

        theStage.setScene(mainMenuScene);

        gameLoop.start();
        theStage.show();
    }

    public Scene getScene() {
        return spinScene;
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

    public void ButtonClicked(ActionEvent e) {
        {
            if (e.getSource()==spinButton)
                theStage.setScene(spinScene);
            else
                theStage.setScene(scene1);
        }
    }
}
