package major_project;
import javafx.application.Application;
import javafx.stage.Stage;
import major_project.model.AppModel;
import major_project.view.AppWindow;

public class Main extends Application {

    private final AppModel model = new AppModel();

    private final AppWindow view = new AppWindow(model);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("REEEEE");
        primaryStage.show();
    }

}
