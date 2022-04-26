package au.edu.sydney.soft3202.task3.view;

import au.edu.sydney.soft3202.task3.model.Database;
import au.edu.sydney.soft3202.task3.model.GameBoard;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This is the overall window scene for the application. It creates and contains the different elements in the
 * top, bottom, center, and right side of the window, along with linking them to the model.
 *
 * Identify the mutable couplings between the View and the Model: This class and the BoardPane are the only 2 View
 * classes that mutate the Model, and all mutations go first to the GameBoard. There is coupling in other ways
 * from the View to the Model, but they are accessor methods only.
 *
 * Also note that while this represents the game window, it *contains* the Scene that JavaFX needs, and does not
 * inherit from Scene. This is true for all JavaFX components in this application, they are contained, not extended.
 */
public class GameWindow {
    private final BoardPane boardPane;
    private final Scene scene;
    private MenuBar menuBar;
    private VBox sideButtonBar;

    private final GameBoard model;

    public GameWindow(GameBoard model) {
        this.model = model;

        BorderPane pane = new BorderPane();
        this.scene = new Scene(pane);

        this.boardPane = new BoardPane(model);
        StatusBarPane statusBar = new StatusBarPane(model);
        buildMenu();
        buildSideButtons();
        buildKeyListeners();

        pane.setCenter(boardPane.getPane());
        pane.setTop(menuBar);
        pane.setRight(sideButtonBar);
        pane.setBottom(statusBar.getStatusBar());

        //prompt login
        promptLogin();


    }

    //new - prompt login
    private void promptLogin() {
        TextInputDialog textInput = new TextInputDialog("");
        textInput.setTitle("Login");
        textInput.setHeaderText("Please enter your username: ");

        Optional<String> input = textInput.showAndWait();

        if (input.isPresent()) {
            String userName = input.get();
            try {
                if (!Database.queryUserExists(userName)) {
                    Database.addUser(userName);
                }
                model.setCurrentUser(userName);
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(e.getMessage());

                alert.showAndWait();
            }
        } else {
            //throw a warning if x/cancel is pressed when the input is empty, pressing ok will make/use a user with an blank name
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Warning");
            alert.setHeaderText("Please enter a username before proceeding.");
            alert.showAndWait();
            promptLogin();
        }
    }


    private void buildKeyListeners() {
        // This allows keyboard input. Note that the scene is used, so any time
        // the window is in focus the keyboard input will be registered.
        // More often, keyboard input is more closely linked to a specific
        // node that must have focus, i.e. the Enter key in a text input to submit
        // a form.

        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.N) {
                newGameAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                serialiseAction();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.L) {
                deserialiseAction();
            }
        });
    }

    private void buildSideButtons() {
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction((event) -> newGameAction());

        Button serialiseBtn = new Button("Save Game");
        serialiseBtn.setOnAction((event) -> serialiseAction());

        Button deserialiseBtn = new Button("Load Game");
        deserialiseBtn.setOnAction((event) -> deserialiseAction());

        this.sideButtonBar = new VBox(newGameBtn, serialiseBtn, deserialiseBtn);
        sideButtonBar.setSpacing(10);
    }

    private void buildMenu() {
        Menu actionMenu = new Menu("Actions");

        MenuItem newGameItm = new MenuItem("New Game");
        newGameItm.setOnAction((event)-> newGameAction());

        MenuItem serialiseItm = new MenuItem("Save Game");
        serialiseItm.setOnAction((event)-> serialiseAction());

        MenuItem deserialiseItm = new MenuItem("Load Game");
        deserialiseItm.setOnAction((event)-> deserialiseAction());

        actionMenu.getItems().addAll(newGameItm, serialiseItm, deserialiseItm);

        this.menuBar = new MenuBar();
        menuBar.getMenus().add(actionMenu);
    }

    private void newGameAction() {
        // Note the separation here between newGameAction and doNewGame. This allows
        // for the validation aspects to be separated from the operation itself.

        if (null == model.getCurrentTurn()) { // no current game
            doNewGame();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Game Warning");
        alert.setHeaderText("Starting a new game now will lose all current progress.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
         doNewGame();
        }
    }

    private void serialiseAction() {
        // Serialisation is a way of turning some data into a communicable form.
        // In Java, it has a library to support it, but here we are just manually converting the field
        // we know we need into a string (in the model). We can then use that string in reverse to get that state back

        if (null == model.getCurrentTurn()) { // no current game
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save Game Error");
            alert.setHeaderText("There is no game to save!");

            alert.showAndWait();
            return;
        }

        String serialisation = model.serialise();
        String currentUser = model.getCurrentUser();

        TextInputDialog textInput = new TextInputDialog();
        textInput.setTitle("Save Game");
        textInput.setHeaderText("What would you like to call your save?: ");
        Optional<String> saveNameInput = textInput.showAndWait();

        if (saveNameInput.isPresent()) {
            String saveName = saveNameInput.get();
            try {
                if (Database.queryCheckExists(saveName, currentUser)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Overwrite save?");
                    alert.setHeaderText("A save file with this name already exists. Would you like to overwrite it?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Database.updateSave(saveName, serialisation);
                    }
                } else {
                    Database.addSaveFile(currentUser, saveName, serialisation);
                }

            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Serialisation Error");
                alert.setHeaderText(e.getMessage());

                alert.showAndWait();
            }
        }

    }

    private void deserialiseAction() {
        // Here we take an existing serialisation string and feed it back into the model to retrieve that state.
        // We don't do any validation here, as that would leak model knowledge into the view.

        /*needs complete overhaul:
            - display all saves of current user using Database.queryUserSaves (ArrayList of strings)
            - on click, call model.deserialise() using the string you've selected
        */
        ArrayList<String> saveList = Database.queryUserSaves(model.getCurrentUser());
        ChoiceDialog<String> choiceInput = new ChoiceDialog<>("");

        choiceInput.setTitle("Load game");
        choiceInput.setHeaderText("Save files for user: " + model.getCurrentUser());
        choiceInput.getItems().addAll(saveList);

        //user's returned input is whichever option is clicked at the moment
        Optional<String> input = choiceInput.showAndWait();
        if (input.isPresent()) {
            String saveName = input.get();
            String serialisation = Database.querySerialisation(saveName, model.getCurrentUser());
            try {
                model.deserialise(serialisation);
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load Game Error");
                alert.setHeaderText(e.getMessage());

                alert.showAndWait();
                return;
            }

            boardPane.updateBoard();
        }
    }

    private void doNewGame() {
        // Here we have an action that we know would likely mutate the state of the model, and so the view should
        // update. Unlike the StatusBarPane that uses the observer pattern to do this, here we can just trigger it
        // because we know the model will mutate as a result of our call to it.
        // Generally speaking the observer pattern is superior - I would recommend using it instead of
        // doing it this way.

        model.newGame();
        boardPane.updateBoard();
    }

    public Scene getScene() {
        return this.scene;
    }
}
