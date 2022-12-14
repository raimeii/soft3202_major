package majorproject.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import majorproject.model.AppModel;

import java.util.Optional;

/**
 * Handles the UI elements and their event handlers for the cache
 */
public class CacheHandler {

    private final AppModel model;

    public CacheHandler(AppModel model) {
        this.model = model;
    }

    public Button createCacheClearButton() {
        Button cacheClear = new Button("Clear cache");

        cacheClear.setOnAction(event -> {
            cacheClear();
        });

        return cacheClear;
    }

    public void cacheClear() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear cache?");
        alert.setHeaderText("Are you sure you want to clear the cache?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            model.clearDatabaseCache();
        }
    }
}
