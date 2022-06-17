package majorproject.view;


import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import majorproject.model.AppModel;

/**
 * The main view class, which collects all the elements of the different parts of the view. Called by the main method to
 * be displayed.
 */
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

        HBox generateReportHBox = outputView.createReportGeneration();

        Button mediaControlBtn = mediaHandler.createPlayPauseButton();

        Button clearCacheBtn = cacheHandler.createCacheClearButton();

        Insets inset = new Insets(10);

        pane.setCenter(vb);

        HBox bottomBox = new HBox(generateReportHBox, mediaControlBtn, clearCacheBtn);
        bottomBox.setSpacing(10);
        pane.setBottom(bottomBox);
        buildKeyListeners();

        BorderPane.setMargin(vb, inset);
        BorderPane.setMargin(bottomBox, inset);
    }


    /**
     * Setup of key listeners to execute events for the app.
     */
    private void buildKeyListeners() {

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

            //exam implementation
            if (event.isControlDown() && event.getCode() == KeyCode.J) {
                inputView.addToSaved();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.K) {
                inputView.removeFromSaved();
            }
        });
    }

    public Scene getScene() {
        return this.scene;
    }
}
