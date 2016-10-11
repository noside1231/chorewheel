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


    MainMenu(ChoreWheelRun choreWheelRun) {
        run = choreWheelRun;

        spinButton = new Button("Spin!");
        configButton = new Button("Config");
        spinButton.setOnAction(e -> ButtonClicked(e));
        configButton.setOnAction(e -> ButtonClicked(e));

        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        getChildren().addAll(spinButton, configButton);
//        setAutoScale(AutoScale.FIT);

    }


    public void ButtonClicked(ActionEvent e) {
        {
            if (e.getSource()==spinButton) {
                run.wheel.populateChores();
                run.theStage.setScene(run.spinScene);
            }
            else if(e.getSource()==configButton)
                run.theStage.setScene(run.configScene);
        }
    }

}
