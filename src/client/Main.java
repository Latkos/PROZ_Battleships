package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void init()
    {
        //Tu zalatwia sie rzeczy wstepne typu polaczenie z baza danych itd
        //Mozliwe, ze tutaj bedzie nawiazywanie polaczenia z serwerem
    }
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("loginWindow.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void stop()
    {
        //sprzatanie po aplikacji
        //czyli np zamkniecie polaczenia z serwerem itd
    }
}
