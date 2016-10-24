import datastructures.Entity;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

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

    Config(ChoreWheelRun choreWheelRun) throws FileNotFoundException {
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

        add(nameL, 0, 1);
        add(choreL, 2, 1);
        add(backButton, 0, 12);

        File file = new File(getClass().getResource("config.txt").toString().substring(5));
        Scanner scan = new Scanner(file);

        for(int i = 0; i < nameField.length; i++) {
            if(scan.hasNext()) {
                String next = scan.next();
                if(!next.equals(",")) {
                    nameField[i] = new TextField(next);
                } else {
                    nameField[i] = new TextField();
                }
            } else {
                nameField[i] = new TextField();
            }
            nameField[i].setPrefWidth(150);
            add(nameField[i], 0, i+2);

            if(scan.hasNext()) {
                double red = scan.nextDouble();
                double green = scan.nextDouble();
                double blue = scan.nextDouble();
                Color color = getColorFromText(red, green, blue);
                nameColor[i] = new ColorPicker(color);
                nameColor[i].getCustomColors().add(color);
            } else {
                nameColor[i] = new ColorPicker();
            }
            nameColor[i].setPrefWidth(30);
            add(nameColor[i], 1, i+2);
        }

        for(int i = 0; i < choreField.length; i++) {
            if(scan.hasNext()) {
                String next = scan.next();
                if(!next.equals(",")) {
                    choreField[i] = new TextField(next);
                } else {
                    choreField[i] = new TextField();
                }
            } else {
                choreField[i] = new TextField();
            }
            choreField[i].setPrefWidth(150);
            add(choreField[i],2,i+2);

            if(scan.hasNext()) {
                double red = scan.nextDouble();
                double green = scan.nextDouble();
                double blue = scan.nextDouble();
                Color color = getColorFromText(red, green, blue);
                choreColor[i] = new ColorPicker(color);
                choreColor[i].getCustomColors().add(color);
            } else {
                choreColor[i] = new ColorPicker();
            }
            choreColor[i].setPrefWidth(30);
            add(choreColor[i], 3, i+2);
        }
        add(saveButton, 1, 12);

        saveButton.setOnAction(this::ButtonClicked);
        backButton.setOnAction(this::ButtonClicked);

        saveInput();
    }

    private Color getColorFromText(double red, double green, double blue) {
        return Color.color(red, green, blue);
    }

    public void ButtonClicked(ActionEvent e) {
        if (e.getSource()==saveButton)
            saveInput();
        else if(e.getSource()==backButton)
            run.theStage.setScene(run.mainMenuScene);
    }

    public void saveInput() {
        File file = new File(getClass().getResource("config.txt").toString().substring(5));
        PrintWriter write = null;
        try {
            write = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        names.clear();
        for(int i = 0; i < nameField.length; i++) {
            if(nameField[i].getText().isEmpty()) {
                write.println(",");
            } else {
                write.println(nameField[i].getText());
            }
            if(!nameColor[i].getCustomColors().isEmpty()) {
                Color color = nameColor[i].getCustomColors().get(nameColor[i].getCustomColors().size() - 1);
                write.println(color.getRed());
                write.println(color.getGreen());
                write.println(color.getBlue());
            } else {
                write.println(1);
                write.println(1);
                write.println(1);
            }
            if(!nameField[i].getText().equals("")) {
                names.add(new Entity(nameColor[i].getValue(), String.valueOf(nameField[i].getText())));
            }
        }

        chores.clear();
        for(int i = 0; i < choreField.length; i++) {
            if(choreField[i].getText().isEmpty()) {
                write.println(",");
            } else {
                write.println(choreField[i].getText());
            }
            if(!nameColor[i].getCustomColors().isEmpty()) {
                Color color = choreColor[i].getCustomColors().get(choreColor[i].getCustomColors().size() - 1);
                write.println(color.getRed());
                write.println(color.getGreen());
                write.println(color.getBlue());
            } else {
                write.println(1);
                write.println(1);
                write.println(1);
            }
            if(!choreField[i].getText().equals("")) {
                chores.add(new Entity(choreColor[i].getValue(), String.valueOf(choreField[i].getText())));
            }
        }
        write.flush();
        write.close();
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
