package major_project;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import major_project.model.AppModel;
import major_project.view.AppWindow;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        boolean inputOnline;
        boolean outputOnline;
        Parameters p = getParameters();
        HostServices hostService = getHostServices();
        List<String> listParams = p.getUnnamed();
        if (listParams.size() != 2) {
            System.out.println("Incorrect arguments");
            System.exit(0);
        }
        inputOnline = listParams.get(0).equalsIgnoreCase("online");
        outputOnline = listParams.get(1).equalsIgnoreCase("online");

        AppModel model = new AppModel(inputOnline, outputOnline);
        AppWindow view = new AppWindow(model, hostService);

        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("The Guardian Content Search");
        primaryStage.show();
    }

}
