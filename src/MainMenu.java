import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by Edison on 10/10/16.
 */

public class MainMenu extends GridPane {

    private ChoreWheelRun run;

    private Button spinButton, configButton;
    private Label warningLabel;


    MainMenu(ChoreWheelRun choreWheelRun) {
        run = choreWheelRun;

        HBox hbox1 = new HBox();



        spinButton = new Button("Spin!");
        configButton = new Button("Config");
        warningLabel = new Label("");

        HBox.setHgrow(spinButton, Priority.NEVER);
        HBox.setHgrow(configButton, Priority.NEVER);
        spinButton.setMaxWidth(Double.MAX_VALUE);
        configButton.setMaxWidth(Double.MAX_VALUE);

        hbox1.getChildren().addAll(spinButton, configButton);

        hbox1.setSpacing(10);
        spinButton.setOnAction(e -> ButtonClicked(e));
        configButton.setOnAction(e -> ButtonClicked(e));

        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        warningLabel.setMaxWidth(100);
        warningLabel.setWrapText(true);

        add(hbox1,0,0);
        add(warningLabel,0,2);


    }


    private void ButtonClicked(ActionEvent e) {
        {
            if (e.getSource()==spinButton) {
                run.wheel.setNames();
                run.wheel.setChores();

                if(run.wheel.getNames().isEmpty() || run.wheel.getChores().isEmpty()) {
                    warningLabel.setText("Please add at least one name and one chore");
                }
                else {
                    warningLabel.setText("");
                    run.wheel.populateChores();
                    run.theStage.setScene(run.spinScene);
                }
            }
            else if(e.getSource()==configButton)
                run.theStage.setScene(run.configScene);
        }
    }

}
