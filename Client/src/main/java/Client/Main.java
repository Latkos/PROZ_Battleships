package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


/**
 * Class within starts the JavaFX application.
 */
public class Main extends Application
{
    private Client client;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void init()
    {
        try {
            client = new Client("localhost");
        } catch (Exception e) {
            client = null;
        }
    }
    @Override
    public void start(Stage stage) throws Exception
    {
        if(client == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd przy nawiązywaniu połączenia");
            alert.setContentText("Nie odnaleziono serwera");
            alert.showAndWait();
            Platform.exit();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginWindow.fxml"));
            Parent root= loader.load();
            LoginWindowController loginWindowController = loader.getController();
            loginWindowController.setClient(client);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Battleships - logowanie");
            stage.show();
        }
    }

    @Override
    public void stop()
    {
        if(client != null)
        {
            client.stopToListen();

            if(client.getLoginSuccess())
            {
                client.sendMessage("LEFT");
            }
            System.exit(0);
        }

    }
}
