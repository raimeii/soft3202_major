package major_project.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.model.AppModel;

public class TagSearchPane {
    //input textfield, output choicebox, model (duh), lookup button, clear button
    private AppModel model;

    private final TextField inputField;

    private ListView<String> outputField;

    private final Button lookupBtn;

    private Button clearBtn;

    private VBox pane;

    public TagSearchPane() {
        //hbox textfield and buttons
        this.inputField = new TextField();
        inputField.setPrefWidth(500);
        inputField.setMaxWidth(500);
        this.lookupBtn = new Button("Lookup");
        this.clearBtn = new Button("Clear tag");
        HBox searchField = new HBox(this.inputField, this.lookupBtn, this.clearBtn);
        searchField.setSpacing(10);
        Label prompt = new Label("Search for tag: ");

        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise", "1", "2", "3");

        this.outputField = new ListView<>(names);
        this.outputField.setPrefWidth(250);
        this.outputField.setPrefHeight(250);

        this.pane = new VBox(prompt, searchField, this.outputField);
        this.pane.setSpacing(10);
    }

    public VBox getPane() {
        return this.pane;
    }
}
