package major_project.view;


import javafx.application.HostServices;
import javafx.collections.FXCollections;
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

import java.util.List;


public class AppWindow {

    private final Scene scene;
    private final AppModel model;

    private final TextField inputField;

    private final ListView<String> tagOutputField;

    private final Label currentTagLabel;

    private final ListView<String> resultOutputField;

    private final HostServices hostService;

    public AppWindow(AppModel model, HostServices hostService) {
        this.model = model;
        this.hostService = hostService;
        BorderPane pane = new BorderPane();
        this.scene = new Scene(pane);

        this.inputField = createTagTextField();

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
    }

    public TextField createTagTextField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter your tag here");

        textField.setPrefWidth(500);
        textField.setMaxWidth(500);
        textField.setOnAction(event ->  {
            String input = inputField.getText();
            List<String> tagMatches = model.searchMatchingTags(input);
            tagOutputField.getItems().addAll(tagMatches);
        });

        return textField;
    }



    //note: maybe feedback to user when queried tag returns no hits?
    public Button createLookupButton() {
        Button lookupBtn = new Button("Lookup");
        lookupBtn.setOnAction((event -> {
            if (model.hasTagResponseStored()) {
                //clear list for new tag search
                clearTagQuery();
            }
            String input = inputField.getText();
            List<String> tagMatches = model.searchMatchingTags(input);
            tagOutputField.getItems().addAll(tagMatches);
        }));
        return lookupBtn;
    }

    public Button createClearButton() {
        Button clearBtn = new Button("Clear tag");
        clearBtn.setOnAction((event -> {
            clearTagQuery();
            model.setCurrentTag(null);
            inputField.clear();

        }));

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
            List<String> resultList = model.getResultsWithTag(tag);
            this.resultOutputField.setItems(FXCollections.observableList(resultList));
        });
        return tagOutputField;
    }

    public ListView<String> createResultOutputView() {
        ListView<String> resultOutputField = new ListView<>();
        resultOutputField.setPrefWidth(600);
        resultOutputField.setPrefHeight(250);
        //set current tag to whatever is clicked
        resultOutputField.setOnMouseClicked(event -> {
            String title = resultOutputField.getSelectionModel().getSelectedItem();
            String url = model.getContentURL(title);
            hostService.showDocument(url);
        });
        return resultOutputField;
    }

    public Button createReportGeneration() {
        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.setOnAction((event -> generateReport()));

        return generateReportBtn;
    }

    private void generateReport() {
        //possible source of M-V leak, not sure how to best approach
        //current flow is looking like AppModel -> View -> PastebinHandler
        //maybe View -> AppModel -> PastebinHandler, then AppModel returns some content to be displayed back to the view
        String pastebinURL = model.generateOutputReport();
        if (pastebinURL != null) {
            hostService.showDocument(pastebinURL);
        } else {
            ;
        }


    }


    private void clearTagQuery() {
        tagOutputField.getItems().clear();
        resultOutputField.getItems().clear();
        currentTagLabel.setText("Articles with tag: ");
    }



    public Scene getScene() {
        return this.scene;
    }
}
