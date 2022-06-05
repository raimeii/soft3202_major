package majorproject.view;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import majorproject.model.AppModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.scene.input.KeyCode.ENTER;

/**
 * Handles the UI elements and their event handlers for the input API elements
 */

public class InputView {

    private final Label prompt = new Label("Search for tag: ");
    private TextField inputField;
    private Button lookupButton;
    private Button clearButton;
    private ListView<String> tagOutputField;
    private final Label currentTagLabel = new Label("Articles with tag: ");;
    private ListView<String> resultOutputField;

    private final ProgressIndicator progressIndicator = new ProgressIndicator(0);
    private AppModel model;
    private final HostServices hostService;

    private Task<Void> lookupTaskAPI;

    private Task<ArrayList<String>> tagOutputTask;

    private final ExecutorService executor = Executors.newFixedThreadPool(2, runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    public InputView (AppModel model, HostServices hostService) {
        this.model = model;
        this.hostService = hostService;
        inputField = createInputField();
        lookupButton = createLookupButton();
        clearButton = createClearButton();
        tagOutputField = createTagOutputView();
        resultOutputField = createResultOutputView();
    }


    public VBox buildInputView() {
        HBox searchField = new HBox(inputField, lookupButton, clearButton, progressIndicator);
        searchField.setSpacing(10);

        VBox viewBox = new VBox(prompt, searchField, tagOutputField, currentTagLabel, resultOutputField);
        viewBox.setSpacing(10);

        return viewBox;
    }

    private TextField createInputField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter your tag here");

        textField.setPrefWidth(500);
        textField.setMaxWidth(500);
        textField.setOnAction(event ->  {
            lookUp();
        });

        return textField;
    }

    //note: maybe feedback to user when queried tag returns no hits?
    private Button createLookupButton() {
        Button lookupBtn = new Button("Lookup");
        lookupBtn.setPrefWidth(100);
        lookupBtn.setOnAction((event -> {
            lookUp();
        }));
        return lookupBtn;
    }

    private Button createClearButton() {
        Button clearBtn = new Button("Clear tag");
        clearBtn.setPrefWidth(100);
        clearBtn.setOnAction((event -> {
            clearFields();
        }));

        return clearBtn;
    }

    public ListView<String> createTagOutputView() {
        ListView<String> tagOutputField = new ListView<>();
        tagOutputField.setPrefWidth(250);
        tagOutputField.setPrefHeight(250);
        //set current tag to whatever is clicked
        tagOutputField.setOnMouseClicked(event -> {
            tagOutputProcessing();
        });

        tagOutputField.setOnKeyPressed(event -> {
            if (event.getCode() == ENTER) {
                tagOutputProcessing();
            }
        });
        return tagOutputField;
    }

    private ListView<String> createResultOutputView() {
        ListView<String> resultOutputField = new ListView<>();
        resultOutputField.setPrefWidth(600);
        resultOutputField.setPrefHeight(250);
        //set current tag to whatever is clicked
        resultOutputField.setOnMouseClicked(event -> {
            resultDisplayProcessing();
        });

        resultOutputField.setOnKeyPressed(event -> {
            if (event.getCode() == ENTER) {
                resultDisplayProcessing();
            }
        });
        return resultOutputField;
    }

    //clear, lookup set as public so main View class can access it for their key listeners
    private void clearTagQuery() {
        tagOutputField.getItems().clear();
        resultOutputField.getItems().clear();
        model.setTagMatches(null);
        model.setResultMatches(null);
        currentTagLabel.setText("Articles with tag: ");
    }

    public void clearFields() {
        clearTagQuery();
        model.setCurrentTag(null);
        inputField.clear();
        currentTagLabel.requestFocus();
    }

    public void lookUp() {
        lookupTaskAPI = new Task<>() {
            @Override
            protected Void call() throws Exception {

                String input = inputField.getText();
                List<String> tagMatches = model.searchMatchingTags(input);
                Platform.runLater(() -> {
                    if (model.hasTagResponseStored()) {
                        //clear list for new tag search
                        clearTagQuery();
                    }
                    model.setTagMatches(tagMatches);
                    tagOutputField.getItems().clear();
                    tagOutputField.getItems().addAll(model.getTagMatches());
                    progressIndicator.setProgress(1);
                });

                return null;
            }
        };
        executor.execute(lookupTaskAPI);
        progressIndicator.setProgress(lookupTaskAPI.getProgress());
    }



    private void tagOutputProcessing() {
        String tag = tagOutputField.getSelectionModel().getSelectedItem();
        model.setCurrentTag(tag);

        currentTagLabel.setText("Articles with tag: " + model.getCurrentTag());
        if (model.checkTagExistsInDatabase(tag)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tag found in database");
            alert.setHeaderText("Cache hit for this tag – use cache, or request fresh data from the API?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                model.setResultMatches(model.getResultsWithTagDB(tag));
                resultOutputField.setItems(FXCollections.observableList(model.getResultMatches()));
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                tagOutputAPICall(tag);
            }
        } else {
            tagOutputAPICall(tag);
        }
    }
    private void tagOutputAPICall(String tag) {
        tagOutputTask = new Task<>() {
            @Override
            protected ArrayList<String> call() throws Exception {
                ArrayList<String> ret = model.getResultsWithTagAPI(tag);
                Platform.runLater(() -> {
                    model.setResultMatches(ret);
                    resultOutputField.setItems(FXCollections.observableList(ret));
                    progressIndicator.setProgress(1);
                });
                return ret;
            }
        };
        executor.execute(tagOutputTask);
        progressIndicator.setProgress(lookupTaskAPI.getProgress());
    }


    private void resultDisplayProcessing() {
        String title = resultOutputField.getSelectionModel().getSelectedItem();
        String url = model.getContentURL(title);
        hostService.showDocument(url);
    }
}
