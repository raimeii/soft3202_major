package major_project.view;


import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.*;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import major_project.model.AppModel;
import major_project.model.AppModelImpl;

import java.util.Optional;

import static javafx.scene.input.KeyCode.ENTER;


public class AppWindow {

    private final Scene scene;
    private final AppModel model;

    private final TextField inputField;

    private final ListView<String> tagOutputField;

    private final Label currentTagLabel;

    private final ListView<String> resultOutputField;

    private final HostServices hostService;

    private final MediaPlayer mediaPlayer;

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

        this.mediaPlayer = createMediaPlayer();

        this.currentTagLabel = new Label("Articles with tag: ");

        VBox vb = new VBox(prompt, searchField, this.tagOutputField, this.currentTagLabel, this.resultOutputField);
        vb.setSpacing(10);

        Button generateReportBtn = createReportGeneration();

        Button mediaControlBtn = createPlayPauseButton();

        Button clearCacheBtn = createCacheClearButton();

        Insets inset = new Insets(10);

        pane.setCenter(vb);

        HBox bottomBox = new HBox(generateReportBtn, mediaControlBtn, clearCacheBtn);
        bottomBox.setSpacing(10);
        pane.setBottom(bottomBox);

        BorderPane.setMargin(vb, inset);
        BorderPane.setMargin(bottomBox, inset);
    }

    public TextField createTagTextField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter your tag here");

        textField.setPrefWidth(500);
        textField.setMaxWidth(500);
        textField.setOnAction(event ->  {
            String input = inputField.getText();
            model.setTagMatches(model.searchMatchingTags(input));
            tagOutputField.getItems().clear();
            tagOutputField.getItems().addAll(model.getTagMatches());
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
            model.setTagMatches(model.searchMatchingTags(input));
            tagOutputField.getItems().clear();
            tagOutputField.getItems().addAll(model.getTagMatches());
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

            this.resultOutputField.setItems(FXCollections.observableList(model.getResultMatches()));
        });

        tagOutputField.setOnKeyPressed(event -> {
            if (event.getCode() == ENTER) {
                String tag = tagOutputField.getSelectionModel().getSelectedItem();
                model.setCurrentTag(tag);
                this.currentTagLabel.setText("Articles with tag: " + model.getCurrentTag());
                if (model.checkTagExistsInDatabase(tag)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Tag found in cache");
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
                this.resultOutputField.setItems(FXCollections.observableList(model.getResultMatches()));
            }
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


        resultOutputField.setOnKeyPressed(event -> {
            if (event.getCode() == ENTER) {
                String title = resultOutputField.getSelectionModel().getSelectedItem();
                String url = model.getContentURL(title);
                hostService.showDocument(url);
            }
        });
        return resultOutputField;
    }

    public MediaPlayer createMediaPlayer() {
        Media musicMedia = new Media(model.getMusicResource());
        MediaPlayer mediaPlayer = new MediaPlayer(musicMedia);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setVolume(0.2);
        mediaPlayer.play();
        return mediaPlayer;
    }

    public Button createPlayPauseButton() {
        Button playPause = new Button("Play/Pause music");

        playPause.setOnAction(event -> {
            if (model.isAudioPlaying()) {
                mediaPlayer.pause();
                model.setAudioPlaying(false);
            } else {
                mediaPlayer.play();
                model.setAudioPlaying(true);
            }
        });

        return playPause;
    }

    public Button createCacheClearButton() {
        Button cacheClear = new Button("Clear cache");

        cacheClear.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Clear cache?");
            alert.setHeaderText("Are you sure you want to clear the cache?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                model.clearDatabaseCache();
            }

        });

        return cacheClear;
    }

    public Button createReportGeneration() {
        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.setOnAction((event -> generateReport()));

        return generateReportBtn;
    }

    private void generateReport() {
        String pastebinURL = model.generateOutputReport();
        if (pastebinURL != null) {
            TextInputDialog textInput = new TextInputDialog(pastebinURL);
            textInput.setTitle("Pastebin URL");
            textInput.setHeaderText("Pastebin of contents with tag: " + model.getCurrentTag());
            textInput.showAndWait();
        }
    }


    private void clearTagQuery() {
        tagOutputField.getItems().clear();
        resultOutputField.getItems().clear();
        model.setTagMatches(null);
        model.setResultMatches(null);
        currentTagLabel.setText("Articles with tag: ");
    }

    public Scene getScene() {
        return this.scene;
    }
}
