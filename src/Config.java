import datastructures.Entity;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Edison on 10/10/16.
 */

public class Config extends FlowPane{

    private ChoreWheelRun run;

    private Button saveButton, backButton;

    private TextField[] nameField;
    private TextField[] choreField;

    private ArrayList<Entity> names;
    private ArrayList<Entity> chores;

    Config(ChoreWheelRun choreWheelRun) {

        run = choreWheelRun;


        names = new ArrayList<Entity>();
        chores = new ArrayList<Entity>();

        saveButton = new Button("Apply");
        backButton = new Button("Back");

        choreField = new TextField[10];
        nameField = new TextField[10];

        setVgap(10);
        setHgap(10);
        setAlignment(Pos.CENTER);

        getChildren().add(backButton);
        for(int i = 0; i < nameField.length; i++) {
            if(i == 0)
                nameField[i] = new TextField("Name");
            else
                nameField[i] = new TextField();

            getChildren().add(nameField[i]);
        }
        for(int i = 0; i < choreField.length; i++) {
            if(i==0)
                choreField[i] = new TextField("Chore");
            else
                choreField[i] = new TextField();
            getChildren().add(choreField[i]);
        }
        getChildren().add(saveButton);

        saveButton.setOnAction(e -> ButtonClicked(e));
        backButton.setOnAction(e -> ButtonClicked(e));

        saveInput();

    }

    public void ButtonClicked(ActionEvent e) {
        if (e.getSource()==saveButton)
            saveInput();
        else if(e.getSource()==backButton)
            run.theStage.setScene(run.mainMenuScene);
    }

    public void saveInput() {

        names.clear();
        for(int i = 0; i < nameField.length; i++) {
            if(!nameField[i].getText().equals("")) {
                names.add(new Entity(Color.WHITE, String.valueOf(nameField[i].getText())));
            }
        }

        chores.clear();
        for(int i = 0; i < choreField.length; i++) {
            if(!choreField[i].getText().equals("")) {
                chores.add(new Entity(Color.WHITE, String.valueOf(choreField[i].getText())));
            }
        }
    }

    public ArrayList<Entity> getNames() {
        return names;
    }

    public ArrayList<Entity> getChores() {
        return chores;
    }

}
