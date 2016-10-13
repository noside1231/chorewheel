import datastructures.Entity;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Edison on 10/10/16.
 */

public class Config extends GridPane{

    private ChoreWheelRun run;

    private Button saveButton, backButton;

    private Label nameL, choreL;

    private TextField[] nameField;
    private TextField[] choreField;

    private ArrayList<Entity> names;
    private ArrayList<Entity> chores;

    private ColorPicker[] nameColor;
    private ColorPicker[] choreColor;

    Config(ChoreWheelRun choreWheelRun) {

        run = choreWheelRun;

        names = new ArrayList<>();
        chores = new ArrayList<>();

        saveButton = new Button("Apply");
        backButton = new Button("Back");

        nameL = new Label("Name");
        choreL = new Label("Chore");

        choreField = new TextField[10];
        nameField = new TextField[10];

        nameColor = new ColorPicker[10];
        choreColor = new ColorPicker[10];


        setVgap(10);
        setHgap(10);
        setPadding(new Insets(20));

        add(nameL, 0,1);
        add(choreL,2,1);

        add(backButton, 0,12);
        for(int i = 0; i < nameField.length; i++) {
            nameField[i] = new TextField(i+"");
            nameField[i].setPrefWidth(150);
            add(nameField[i], 0,i+2);

            nameColor[i] = new ColorPicker();
            nameColor[i].setPrefWidth(30);
            add(nameColor[i], 1, i+2);
        }
        for(int i = 0; i < choreField.length; i++) {
            choreField[i] = new TextField(i+"");
            choreField[i].setPrefWidth(150);
            add(choreField[i],2,i+2);

            choreColor[i] = new ColorPicker();
            choreColor[i].setPrefWidth(30);
            add(choreColor[i], 3, i+2);
        }
        add(saveButton,1,12);

        saveButton.setOnAction(this::ButtonClicked);
        backButton.setOnAction(this::ButtonClicked);


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
                names.add(new Entity(nameColor[i].getValue(), String.valueOf(nameField[i].getText())));

            }
        }

        chores.clear();
        for(int i = 0; i < choreField.length; i++) {
            if(!choreField[i].getText().equals("")) {
                chores.add(new Entity(choreColor[i].getValue(), String.valueOf(choreField[i].getText())));
            }
        }




    }


    public ArrayList<Entity> getNames() {
        return names;
    }

    public ArrayList<Entity> getChores() {
        return chores;
    }

    public void setNames() {
        names = run.getWheel().getNames();
    }

}
