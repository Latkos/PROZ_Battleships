package clientGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void ButtonAction(MouseEvent mouseEvent) throws Exception {
        if(mouseEvent.getSource() == loginButton)
        {
            //akcje, ktore wykonaja sie po nacisnieciu loginButton
            Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("clientWindow.fxml"));
            stage.setScene(new Scene(root));
        }
        else if(mouseEvent.getSource() == cancelButton)
        {
            //the same jak wyzej ale dla cancelBUtton
        }

    }
}
