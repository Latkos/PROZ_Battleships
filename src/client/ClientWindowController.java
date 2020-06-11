package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientWindowController implements Initializable
{
    private enum ChosenShip { noShip, singleMaster, twoMaster, threeMaster, fourMaster};
    private ChosenShip chosenShip;
    //choiceButtons
    @FXML
    private Button singleMastedChoiceButton, twoMasterChoiceButton, threeMasterChoiceButton, fourMasterChoiceButton;

    //V-H RadioButtons

    @FXML
    private RadioButton verticallyOption, horizontallyOption;

    //Ships Amount Texts + chosenShipTypeText

    @FXML
    private Text singleMasterShipsAmountText, twoMasterShipsAmountText, threeMasterShipsAmountText, fourMasterShipsAmountText, chosenShipTypeText;

    //player and enemy fields
    @FXML
    private Pane field_pA1, field_pA2, field_pA3, field_pA4, field_pA5, field_pA6, field_pA7, field_pA8, field_pA9, field_pA10,
                 field_pB1, field_pB2, field_pB3, field_pB4, field_pB5, field_pB6, field_pB7, field_pB8, field_pB9, field_pB10,
                 field_pC1, field_pC2, field_pC3, field_pC4, field_pC5, field_pC6, field_pC7, field_pC8, field_pC9, field_pC10,
                 field_pD1, field_pD2, field_pD3, field_pD4, field_pD5, field_pD6, field_pD7, field_pD8, field_pD9, field_pD10,
                 field_pE1, field_pE2, field_pE3, field_pE4, field_pE5, field_pE6, field_pE7, field_pE8, field_pE9, field_pE10,
                 field_pF1, field_pF2, field_pF3, field_pF4, field_pF5, field_pF6, field_pF7, field_pF8, field_pF9, field_pF10,
                 field_pG1, field_pG2, field_pG3, field_pG4, field_pG5, field_pG6, field_pG7, field_pG8, field_pG9, field_pG10,
                 field_pH1, field_pH2, field_pH3, field_pH4, field_pH5, field_pH6, field_pH7, field_pH8, field_pH9, field_pH10,
                 field_pI1, field_pI2, field_pI3, field_pI4, field_pI5, field_pI6, field_pI7, field_pI8, field_pI9, field_pI10,
                 field_pJ1, field_pJ2, field_pJ3, field_pJ4, field_pJ5, field_pJ6, field_pJ7, field_pJ8, field_pJ9, field_pJ10;

    @FXML
    private Pane field_eA1, field_eA2, field_eA3, field_eA4, field_eA5, field_eA6, field_eA7, field_eA8, field_eA9, field_eA10,
                 field_eB1, field_eB2, field_eB3, field_eB4, field_eB5, field_eB6, field_eB7, field_eB8, field_eB9, field_eB10,
                 field_eC1, field_eC2, field_eC3, field_eC4, field_eC5, field_eC6, field_eC7, field_eC8, field_eC9, field_eC10,
                 field_eD1, field_eD2, field_eD3, field_eD4, field_eD5, field_eD6, field_eD7, field_eD8, field_eD9, field_eD10,
                 field_eE1, field_eE2, field_eE3, field_eE4, field_eE5, field_eE6, field_eE7, field_eE8, field_eE9, field_eE10,
                 field_eF1, field_eF2, field_eF3, field_eF4, field_eF5, field_eF6, field_eF7, field_eF8, field_eF9, field_eF10,
                 field_eG1, field_eG2, field_eG3, field_eG4, field_eG5, field_eG6, field_eG7, field_eG8, field_eG9, field_eG10,
                 field_eH1, field_eH2, field_eH3, field_eH4, field_eH5, field_eH6, field_eH7, field_eH8, field_eH9, field_eH10,
                 field_eI1, field_eI2, field_eI3, field_eI4, field_eI5, field_eI6, field_eI7, field_eI8, field_eI9, field_eI10,
                 field_eJ1, field_eJ2, field_eJ3, field_eJ4, field_eJ5, field_eJ6, field_eJ7, field_eJ8, field_eJ9, field_eJ10;

    @FXML
    private ToggleGroup PositionRadioButtons;

    private Pane[][] playerFields;
    private Pane[][] enemyFields;

    private Game game;
    public void setGame(Game aGame)
    {
        game = aGame;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        playerFields = new Pane[][] {{field_pA1, field_pA2, field_pA3, field_pA4, field_pA5, field_pA6, field_pA7, field_pA8, field_pA9, field_pA10},
                                     {field_pB1, field_pB2, field_pB3, field_pB4, field_pB5, field_pB6, field_pB7, field_pB8, field_pB9, field_pB10},
                                     {field_pC1, field_pC2, field_pC3, field_pC4, field_pC5, field_pC6, field_pC7, field_pC8, field_pC9, field_pC10},
                                     {field_pD1, field_pD2, field_pD3, field_pD4, field_pD5, field_pD6, field_pD7, field_pD8, field_pD9, field_pD10},
                                     {field_pE1, field_pE2, field_pE3, field_pE4, field_pE5, field_pE6, field_pE7, field_pE8, field_pE9, field_pE10},
                                     {field_pF1, field_pF2, field_pF3, field_pF4, field_pF5, field_pF6, field_pF7, field_pF8, field_pF9, field_pF10},
                                     {field_pG1, field_pG2, field_pG3, field_pG4, field_pG5, field_pG6, field_pG7, field_pG8, field_pG9, field_pG10},
                                     {field_pH1, field_pH2, field_pH3, field_pH4, field_pH5, field_pH6, field_pH7, field_pH8, field_pH9, field_pH10},
                                     {field_pI1, field_pI2, field_pI3, field_pI4, field_pI5, field_pI6, field_pI7, field_pI8, field_pI9, field_pI10},
                                     {field_pJ1, field_pJ2, field_pJ3, field_pJ4, field_pJ5, field_pJ6, field_pJ7, field_pJ8, field_pJ9, field_pJ10}};

        enemyFields = new Pane[][] {{field_eA1, field_eA2, field_eA3, field_eA4, field_eA5, field_eA6, field_eA7, field_eA8, field_eA9, field_eA10},
                                    {field_eB1, field_eB2, field_eB3, field_eB4, field_eB5, field_eB6, field_eB7, field_eB8, field_eB9, field_eB10},
                                    {field_eC1, field_eC2, field_eC3, field_eC4, field_eC5, field_eC6, field_eC7, field_eC8, field_eC9, field_eC10},
                                    {field_eD1, field_eD2, field_eD3, field_eD4, field_eD5, field_eD6, field_eD7, field_eD8, field_eD9, field_eD10},
                                    {field_eE1, field_eE2, field_eE3, field_eE4, field_eE5, field_eE6, field_eE7, field_eE8, field_eE9, field_eE10},
                                    {field_eF1, field_eF2, field_eF3, field_eF4, field_eF5, field_eF6, field_eF7, field_eF8, field_eF9, field_eF10},
                                    {field_eG1, field_eG2, field_eG3, field_eG4, field_eG5, field_eG6, field_eG7, field_eG8, field_eG9, field_eG10},
                                    {field_eH1, field_eH2, field_eH3, field_eH4, field_eH5, field_eH6, field_eH7, field_eH8, field_eH9, field_eH10},
                                    {field_eI1, field_eI2, field_eI3, field_eI4, field_eI5, field_eI6, field_eI7, field_eI8, field_eI9, field_eI10},
                                    {field_eJ1, field_eJ2, field_eJ3, field_eJ4, field_eJ5, field_eJ6, field_eJ7, field_eJ8, field_eJ9, field_eJ10}};

        for(int x = 0; x < 10; x++)
            for(int y = 0; y < 10; y++)
            {
                GridPane.setColumnIndex(playerFields[x][y], x);
                GridPane.setRowIndex(playerFields[x][y], y+1);

                GridPane.setColumnIndex(enemyFields[x][y], x+1);
                GridPane.setRowIndex(enemyFields[x][y], y+1);
            }
        chosenShip = ChosenShip.noShip;
        game = new Game();
    }

    public void gridMouseAction(MouseEvent mouseEvent)
    {
        if(game.getGameState() == Game.GameState.SHIPS_SETUP && chosenShip != ChosenShip.noShip)
        {
            Node source = (Node) mouseEvent.getSource();

            if (source instanceof Pane)
            {
                if(chosenShip == ChosenShip.singleMaster)
                {
                    int row = GridPane.getRowIndex(source);
                    int column = GridPane.getColumnIndex(source);
                    playerFields[column][row - 1].setStyle("-fx-background-color: #612727");
                }
                else if(chosenShip == ChosenShip.twoMaster)
                {
                    if(PositionRadioButtons.getSelectedToggle() == verticallyOption)
                    {
                        int row = GridPane.getRowIndex(source);
                        int column = GridPane.getColumnIndex(source);
                        playerFields[column][row - 1].setStyle("-fx-background-color: #613b27");
                        playerFields[column][row].setStyle("-fx-background-color: #613b27");
                    }
                    else
                    {
                        int row = GridPane.getRowIndex(source);
                        int column = GridPane.getColumnIndex(source);
                        playerFields[column][row - 1].setStyle("-fx-background-color: #613b27");
                        playerFields[column + 1][row - 1].setStyle("-fx-background-color: #613b27");
                    }
                }
                else if(chosenShip == ChosenShip.threeMaster)
                {
                    if(PositionRadioButtons.getSelectedToggle() == verticallyOption)
                    {
                        int row = GridPane.getRowIndex(source);
                        int column = GridPane.getColumnIndex(source);
                        playerFields[column][row - 1].setStyle("-fx-background-color: #426127");
                        playerFields[column][row].setStyle("-fx-background-color: #426127");
                        playerFields[column][row+1].setStyle("-fx-background-color: #426127");
                    }
                    else
                    {
                        int row = GridPane.getRowIndex(source);
                        int column = GridPane.getColumnIndex(source);
                        playerFields[column][row - 1].setStyle("-fx-background-color: #426127");
                        playerFields[column + 1][row - 1].setStyle("-fx-background-color: #426127");
                        playerFields[column + 2][row - 1].setStyle("-fx-background-color: #426127");
                    }
                }
                else if(chosenShip == ChosenShip.fourMaster)
                {
                    if(PositionRadioButtons.getSelectedToggle() == verticallyOption)
                    {
                        int row = GridPane.getRowIndex(source);
                        int column = GridPane.getColumnIndex(source);
                        playerFields[column][row - 1].setStyle("-fx-background-color: #272961");
                        playerFields[column][row].setStyle("-fx-background-color: #272961");
                        playerFields[column][row+1].setStyle("-fx-background-color: #272961");
                        playerFields[column][row+2].setStyle("-fx-background-color: #272961");

                    }
                    else
                    {
                        int row = GridPane.getRowIndex(source);
                        int column = GridPane.getColumnIndex(source);
                        playerFields[column][row - 1].setStyle("-fx-background-color: #272961");
                        playerFields[column + 1][row - 1].setStyle("-fx-background-color: #272961");
                        playerFields[column + 2][row - 1].setStyle("-fx-background-color: #272961");
                        playerFields[column + 3][row - 1].setStyle("-fx-background-color: #272961");
                    }
                }
            }
        }
    }

    public void choiceButtonsAction(MouseEvent mouseEvent)
    {
        if(mouseEvent.getSource() == singleMastedChoiceButton)
        {
            chosenShipTypeText.setText("Jednomasztowiec");
            chosenShip = ChosenShip.singleMaster;
        }

        else if(mouseEvent.getSource() == twoMasterChoiceButton)
        {
            chosenShipTypeText.setText("Dwumasztowiec");
            chosenShip = ChosenShip.twoMaster;
        }

        else if(mouseEvent.getSource() == threeMasterChoiceButton)
        {
            chosenShipTypeText.setText("Trójmasztowiec");
            chosenShip = ChosenShip.threeMaster;
        }

        else if(mouseEvent.getSource() == fourMasterChoiceButton)
        {
            chosenShipTypeText.setText("Czteromasztowiec");
            chosenShip = ChosenShip.fourMaster;
        }
    }
}
