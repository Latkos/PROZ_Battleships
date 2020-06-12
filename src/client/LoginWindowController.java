package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable
{
    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nickField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void ButtonAction(MouseEvent mouseEvent) throws Exception {
        if(mouseEvent.getSource() == loginButton)
        {
            //akcje, ktore wykonaja sie po nacisnieciu loginButton
            String login = nickField.getText();
            String password = passwordField.getText();

            if(client.login(login, password))
            {
                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("clientWindow.fxml"));
                ClientWindowController clientWindowController = loader.getController();
                //clientWindowController.setClient(client);
                Parent root = loader.load();
                stage.setScene(new Scene(root));
            }

            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd logowania");
                alert.setHeaderText("Podano nie prawidłowy login lub hasło");
                alert.showAndWait();
            }
        }
        else if(mouseEvent.getSource() == cancelButton)
        {
            //the same jak wyzej ale dla cancelBUtton
        }

    }
}
