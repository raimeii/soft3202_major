package major_project.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import major_project.model.AppModel;


//intellij prompted that this class could be turned into a record
public record OutputView(AppModel model) {


    public Button createReportGeneration() {
        Button generateReportBtn = new Button("Generate Report");
        generateReportBtn.setOnAction((event -> generateReport()));

        return generateReportBtn;
    }

    public void generateReport() {
        String pastebinURL = model.generateOutputReport();
        if (pastebinURL != null) {
            TextInputDialog textInput = new TextInputDialog(pastebinURL);
            textInput.setTitle("Pastebin URL");
            textInput.setHeaderText("Pastebin of contents with tag: " + model.getCurrentTag());
            textInput.showAndWait();
        }
    }
}
