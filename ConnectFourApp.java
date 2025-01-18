import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class representing the Connect Four application.
 * Author: Nishtha Chaudhari , 000930353
 */
public class ConnectFourApp extends Application {

    /**
     * Method to launch the Connect Four application.
     */
    public void launchApp() {
        launch();
    }

    /**
     * Start method for the JavaFX application.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Connect Four Game");

        ConnectFourGame game = new ConnectFourGame();
        game.initGame(primaryStage);

        primaryStage.show();
    }
}