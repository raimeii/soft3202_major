package majorproject.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import majorproject.model.AppModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Handles the UI elements and their event handlers for the output API elements
 */

public class OutputView {

    private final ProgressIndicator progressIndicator = new ProgressIndicator(0);
    private final AppModel model;

    private Task<Void> outputTask;

    private final ExecutorService executor = Executors.newFixedThreadPool(2, runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    public OutputView(AppModel model) {
        this.model = model;
    }

    public HBox createReportGeneration() {
        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.setOnAction((event -> generateReport()));
        HBox hb = new HBox(generateReportBtn, progressIndicator);
        hb.setSpacing(10);

        return hb;
    }

    public void generateReport() {
        outputTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String pastebinURL = model.generateOutputReport();
                Platform.runLater(() -> {
                    if (pastebinURL != null) {
                        TextInputDialog textInput = new TextInputDialog(pastebinURL);
                        textInput.setTitle("Pastebin URL");
                        textInput.setHeaderText("Pastebin of contents with tag: " + model.getCurrentTag());
                        progressIndicator.setProgress(1);
                        textInput.showAndWait();
                    }
                });
                return null;
            }
        };
        executor.execute(outputTask);
        progressIndicator.setProgress(outputTask.getProgress());
    }

}
