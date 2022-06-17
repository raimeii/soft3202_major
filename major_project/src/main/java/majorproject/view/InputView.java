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
    private final Label tagSearchResults = new Label("Tag search results:");
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

    //Exam addition
    private Label savedArticleLabel = new Label("Your currently saved articles:");
    private ListView<String> savedArticleField;

    private Button saveArticleButton;

    private Button unsaveArticleButton;

    public InputView (AppModel model, HostServices hostService) {
        this.model = model;
        this.hostService = hostService;
        inputField = createInputField();
        lookupButton = createLookupButton();
        clearButton = createClearButton();
        tagOutputField = createTagOutputView();
        resultOutputField = createResultOutputView();

        //Exam addition
        savedArticleField = createSavedArticleField();
        saveArticleButton = createSaveArticleButton();
        unsaveArticleButton = createUnsaveArticleButton();
    }


    public VBox buildInputView() {
        HBox saveButtons = new HBox(saveArticleButton, unsaveArticleButton);
        saveButtons.setSpacing(10);
        VBox saveSection = new VBox(savedArticleLabel, savedArticleField, saveButtons);
        saveSection.setSpacing(10);

        HBox searchField = new HBox(inputField, lookupButton, clearButton, progressIndicator);
        searchField.setSpacing(10);

        VBox tagOutputBox = new VBox(tagSearchResults, tagOutputField);
        tagOutputBox.setSpacing(10);

        HBox sideBySidePanels = new HBox(tagOutputBox, saveSection);
        sideBySidePanels.setSpacing(10);


        VBox viewBox = new VBox(prompt, searchField, sideBySidePanels, currentTagLabel, resultOutputField);
        viewBox.setSpacing(10);

        return viewBox;
    }

    private TextField createInputField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter your tag here");

        textField.setPrefWidth(900);
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
        tagOutputField.setPrefWidth(600);
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
        //modified in exam - only open the article when double-clicked
        resultOutputField.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                resultDisplayProcessing();
            }
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
        if (lookupTaskAPI == null) {
            return;
        }
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

    //exam implementation

    private Button createUnsaveArticleButton() {
        Button unsaveButton = new Button("Delete from saved list");
        unsaveButton.setOnAction((event -> {
            removeFromSaved();
        }));
        return unsaveButton;
    }

    private Button createSaveArticleButton() {
        Button saveButton = new Button("Add to saved list");
        saveButton.setOnAction((event -> {
            addToSaved();
        }));
        return saveButton;
    }

    private ListView<String> createSavedArticleField() {
        ListView<String> savedArticleField = new ListView<>();
        savedArticleField.setPrefWidth(600);
        savedArticleField.setPrefHeight(250);
        //set current tag to whatever is clicked
        savedArticleField.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                savedResultDisplayProcessing();
            }
        });

        savedArticleField.setOnKeyPressed(event -> {
            if (event.getCode() == ENTER) {
                savedResultDisplayProcessing();
            }
        });
        return savedArticleField;
    }

    private void clearAndUpdateSaved() {
        savedArticleField.getItems().clear();
        savedArticleField.setItems(FXCollections.observableList(model.getSavedArticles()));
    }

    public void addToSaved() {
        String title = resultOutputField.getSelectionModel().getSelectedItem();
        if (model.addToSavedArticles(title)) {
            clearAndUpdateSaved();
        }


    }

    public void removeFromSaved() {
        String title = savedArticleField.getSelectionModel().getSelectedItem();
        if (model.removeFromSavedArticles(title)) {
            clearAndUpdateSaved();
        }
    }

    private void savedResultDisplayProcessing() {
        String title = savedArticleField.getSelectionModel().getSelectedItem();
        String url = model.getSavedArticleURL(title);
        hostService.showDocument(url);
    }
}
