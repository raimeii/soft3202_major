package major_project.view;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import major_project.model.AppModel;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.scene.input.KeyCode.ENTER;

public class InputView {


    private final Label prompt = new Label("Search for tag: ");
    private TextField inputField;
    private Button lookupButton;
    private Button clearButton;
    private ListView<String> tagOutputField;
    private final Label currentTagLabel = new Label("Articles with tag: ");;
    private ListView<String> resultOutputField;
    private AppModel model;
    private final HostServices hostService;

    /*private Task<ObservableList<String>> lookupTaskAPI;

    private Task<ObservableList<String>> outputTaskAPI;

    private final ExecutorService Executor = Executors.newFixedThreadPool(2, runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });*/

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
        HBox searchField = new HBox(inputField, lookupButton, clearButton);
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
        lookupBtn.setOnAction((event -> {
            lookUp();
        }));
        return lookupBtn;
    }

    private Button createClearButton() {
        Button clearBtn = new Button("Clear tag");
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
        if (model.hasTagResponseStored()) {
            //clear list for new tag search
            clearTagQuery();
        }
        String input = inputField.getText();
        model.setTagMatches(model.searchMatchingTags(input));
        tagOutputField.getItems().clear();
        tagOutputField.getItems().addAll(model.getTagMatches());
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
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                model.setResultMatches(model.getResultsWithTagAPI(tag));
            }
        } else {
            model.setResultMatches(model.getResultsWithTagAPI(tag));
        }
        resultOutputField.setItems(FXCollections.observableList(model.getResultMatches()));
    }

    private void resultDisplayProcessing() {
        String title = resultOutputField.getSelectionModel().getSelectedItem();
        String url = model.getContentURL(title);
        hostService.showDocument(url);
    }
}
