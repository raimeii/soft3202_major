package major_project.view;


import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private InputView inputView;

    private OutputView outputView;

    private MediaHandler mediaHandler;

    private CacheHandler cacheHandler;



    public AppWindow(AppModel model, HostServices hostService) {
        this.model = model;
        this.inputView = new InputView(model, hostService);
        this.outputView = new OutputView(model);
        this.mediaHandler = new MediaHandler(model);
        this.cacheHandler = new CacheHandler(model);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: white, linear-gradient(#61b5fa, #ffffff)");
        this.scene = new Scene(pane);


        VBox vb = inputView.buildInputView();
        vb.setSpacing(10);

        Button generateReportBtn = outputView.createReportGeneration();

        Button mediaControlBtn = mediaHandler.createPlayPauseButton();

        Button clearCacheBtn = cacheHandler.createCacheClearButton();

        Insets inset = new Insets(10);

        pane.setCenter(vb);

        HBox bottomBox = new HBox(generateReportBtn, mediaControlBtn, clearCacheBtn);
        bottomBox.setSpacing(10);
        pane.setBottom(bottomBox);
        buildKeyListeners();

        BorderPane.setMargin(vb, inset);
        BorderPane.setMargin(bottomBox, inset);
    }


    private void buildKeyListeners() {
        // This allows keyboard input. Note that the scene is used, so any time
        // the window is in focus the keyboard input will be registered.
        // More often, keyboard input is more closely linked to a specific
        // node that must have focus, i.e. the Enter key in a text input to submit
        // a form.

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.L) {
                inputView.lookUp();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.C) {
                inputView.clearFields();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.G) {
                outputView.generateReport();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.P) {
                mediaHandler.playPause();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.V) {
                cacheHandler.cacheClear();
            }
        });
    }

    public Scene getScene() {
        return this.scene;
    }
}
