import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    public void ButtonAction(MouseEvent mouseEvent)
    {
        if(mouseEvent.getSource() == loginButton)
        {
            //akcje, ktore wykonaja sie po nacisnieciu loginButton
        }
        else if(mouseEvent.getSource() == cancelButton)
        {
            //the same jak wyzej ale dla cancelBUtton
        }

    }
}
