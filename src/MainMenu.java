import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * Created by Edison on 10/10/16.
 */
public class MainMenu extends FlowPane {

    private ChoreWheelRun run;

    private Button spinButton, configButton;
    private Label lblscene1, lblscene2;


    MainMenu(ChoreWheelRun choreWheelRun) {
        run = choreWheelRun;

        spinButton = new Button("Spin!");
        configButton = new Button("Config");
        spinButton.setOnAction(e -> ButtonClicked(e));
        configButton.setOnAction(e -> ButtonClicked(e));
        lblscene1 = new Label("Main Menu");

        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        getChildren().addAll(spinButton, configButton);
//        setAutoScale(AutoScale.FIT);

    }


    public void ButtonClicked(ActionEvent e) {
        {
            if (e.getSource()==spinButton)
                run.theStage.setScene(run.spinScene);
        }
    }

}
