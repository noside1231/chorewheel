import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * Created by Edison on 10/10/16.
 */
public class Config extends FlowPane{

    private ChoreWheelRun run;

    private Button saveButton, backButton;

    private TextField t0, t1, t2, t3, t4, t5, t6, t7, t8, t9;
    private TextField n0, n1, n2, n3, n4, n5, n6, n7, n8, n9;

    Config(ChoreWheelRun choreWheelRun) {

        run = choreWheelRun;

        saveButton = new Button("Apply");
        backButton = new Button("Back");

        saveButton.setOnAction(e -> ButtonClicked(e));
        backButton.setOnAction(e -> ButtonClicked(e));

        t0 = new TextField();
        t1 = new TextField();
        t2 = new TextField();
        t3 = new TextField();
        t4 = new TextField();
        t5 = new TextField();
        t6 = new TextField();
        t7 = new TextField();
        t8 = new TextField();
        t9 = new TextField();

        n0 = new TextField();
        n1 = new TextField();
        n2 = new TextField();
        n3 = new TextField();
        n4 = new TextField();
        n5 = new TextField();
        n6 = new TextField();
        n7 = new TextField();
        n8 = new TextField();
        n9 = new TextField();

        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        getChildren().addAll(backButton, t0,t1,t2,t3,t4,t5,t6,t7,t8,t9, n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, saveButton);



    }


    public void ButtonClicked(ActionEvent e) {
        {
            if (e.getSource()==saveButton)
                System.out.println("Save");
            else if(e.getSource()==backButton)
                run.theStage.setScene(run.mainMenuScene);
        }
    }

    public void saveInput() {

    }
    }

}
