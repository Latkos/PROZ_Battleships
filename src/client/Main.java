package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{
    private Client client;
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
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginWindow.fxml"));
        Parent root = loader.load();
        LoginWindowController loginWindowController = loader.getController();
        loginWindowController.setClient(client);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Battleships - logowanie");
        stage.show();
    }

    public void stop()
    {
        if(client != null)
        {
            client.stopToListen();

            if(client.getLoginSuccess())
            {
                client.sendMessage("LEFT");
            }
        }

    }
}
