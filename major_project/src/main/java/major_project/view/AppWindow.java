package major_project.view;


import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.model.AppModel;

import java.util.ArrayList;


public class AppWindow {

    private final Scene scene;
    private final AppModel model;

    private final TextField inputField;

    private final ListView<String> tagOutputField;

    private final Label currentTagLabel;

    private final ListView<String> resultOutputField;

    public AppWindow(AppModel model) {
        this.model = model;
        BorderPane pane = new BorderPane();
        this.scene = new Scene(pane);

        this.inputField = new TextField();
        this.inputField.setPrefWidth(500);
        this.inputField.setMaxWidth(500);

        Button lookupBtn = createLookupButton();
        Button clearBtn = createClearButton();

        HBox searchField = new HBox(inputField, lookupBtn, clearBtn);
        searchField.setSpacing(10);

        Label prompt = new Label("Search for tag: ");

        this.tagOutputField = createTagOutputView();

        this.resultOutputField = createResultOutputView();

        this.currentTagLabel = new Label("Articles with tag: ");

        VBox vb = new VBox(prompt, searchField, this.tagOutputField, this.currentTagLabel, this.resultOutputField);
        vb.setSpacing(10);

        Button generateReportBtn = createReportGeneration();

        Insets inset = new Insets(10);

        pane.setCenter(vb);
        pane.setBottom(generateReportBtn);

        BorderPane.setMargin(vb, inset);
        BorderPane.setMargin(generateReportBtn, inset);

        setupKeyListeners();
    }

    private void setupKeyListeners() {
        //remember to cite Task 3
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.ENTER) {
                //question: I can make this into an AppWindow method, so I don't have to repeat it, but is passing the variable "input" considered a breach of separation?
                String input = inputField.getText();
                ArrayList<String> tagMatches = model.searchMatchingTags(input);
                tagOutputField.getItems().addAll(tagMatches);
            }
        });
    }

    public Button createLookupButton() {
        Button lookupBtn = new Button("Lookup");
        lookupBtn.setOnAction((event -> {
            
            String input = inputField.getText();
            ArrayList<String> tagMatches = model.searchMatchingTags(input);
            tagOutputField.getItems().addAll(tagMatches);
        }));
        return lookupBtn;
    }

    public Button createClearButton() {
        Button clearBtn = new Button("Clear tag");
        clearBtn.setOnAction((event -> clearQueries()));

        return clearBtn;
    }

    public ListView<String> createTagOutputView() {
        ListView<String> tagOutputField = new ListView<>();
        tagOutputField.setPrefWidth(250);
        tagOutputField.setPrefHeight(250);
        //set current tag to whatever is clicked
        tagOutputField.setOnMouseClicked(event -> {
            String tag = tagOutputField.getSelectionModel().getSelectedItem();
            model.setCurrentTag(tag);
            this.currentTagLabel.setText("Articles with tag: " + model.getCurrentTag());
            ArrayList<String> resultList = model.getResultsWithTag(tag);
            resultOutputField.getItems().addAll(resultList);
        });
        return tagOutputField;
    }

    public ListView<String> createResultOutputView() {
        ListView<String> resultOutputField = new ListView<>();
        resultOutputField.setPrefWidth(250);
        resultOutputField.setPrefHeight(250);
        //set current tag to whatever is clicked
        resultOutputField.setOnMouseClicked(event -> {
            String title = resultOutputField.getSelectionModel().getSelectedItem();
            String url = model.getContentURL(title);

        });
        return resultOutputField;
    }

    public Button createReportGeneration() {
        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.setOnAction((event -> generateReport()));

        return generateReportBtn;
    }

    private void generateReport() {

    }


    private void clearQueries() {
        model.setCurrentTag(null);
        tagOutputField.getItems().clear();
        resultOutputField.getItems().clear();
        currentTagLabel.setText("Articles with tag: ");
        inputField.clear();
    }



    public Scene getScene() {
        return this.scene;
    }
}
