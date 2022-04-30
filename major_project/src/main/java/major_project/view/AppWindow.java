package major_project.view;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import major_project.model.AppModel;

public class AppWindow {

    private final Scene scene;
    private final AppModel model;

    private final TagSearchPane tagSearchPane;


    public AppWindow(AppModel model) {
        this.model = model;
        BorderPane pane = new BorderPane();
        this.scene = new Scene(pane);
        this.tagSearchPane = new TagSearchPane();


        Insets inset = new Insets(10);

        pane.setTop(this.tagSearchPane.getPane());
        BorderPane.setMargin(this.tagSearchPane.getPane(), inset);

    }





    public Scene getScene() {
        return this.scene;
    }
}
