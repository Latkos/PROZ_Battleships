import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * A client for a multi-player tic tac toe game. Loosely based on an example in
 * Deitel and Deitel’s “Java How to Program” book. For this project I created a
 * new application-level protocol called TTTP (for Tic Tac Toe Protocol), which
 * is entirely plain text. The messages of TTTP are:
 * <p>
 * Client -> Server MOVE <n> QUIT
 * <p>
 * Server -> Client WELCOME <char> VALID_MOVE OTHER_PLAYER_MOVED <n>
 * OTHER_PLAYER_LEFT VICTORY DEFEAT TIE MESSAGE <text>
 */
public class Client {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 58302);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * The main thread of the client will listen for messages from the server. The
     * first message will be a "WELCOME" message in which we receive our mark. Then
     * we go into a loop listening for any of the other messages, and handling each
     * message appropriately. The "VICTORY", "DEFEAT", "TIE", and
     * "OTHER_PLAYER_LEFT" messages will ask the user whether or not to play another
     * game. If the answer is no, the loop is exited and the server is sent a "QUIT"
     * message.
     */
    public void playListen() throws Exception {
        try {
            var response = in.nextLine();
            System.out.println(response);
            while (in.hasNextLine()) {
                response = in.nextLine();
                System.out.println(response);
                if (response.startsWith("WINNER")) {
                    //displayWinnerWindow (czy jakkolwiek tam sygnalizujemy wygraną...)
                    break;
                }
                if (response.startsWith("SHOOT X Y")) {
                    //parseTheCoordinatesFromResponse
                    /*if (isHit()){
                        colorTheCellAsHitOnMotherboard
                        if(isSank()){
                            colorTheWholeShipAsSankOnMotherboard
                            out.println("SANK")
                        }
                        else if (isTheGameOver()){
                            //displayLoserWindow
                            //out.println("WINNER");
                            break;
                        else{
                            out.println("HIT")
                        }
                    }
                    else{
                        colorTheCellAsMissedOnMotherboard
                        out.println("MISSED")
                     */

                }
                if (response.startsWith("YOUR TURN")) {
                    //unblockButtons (mam na mysli pola)
                    //askThePlayerForNextShot
                    //out.println("SHOOT XY"), gdzie X to litera Y to nr
                    //mozliwe ze out.println("SHOOT XY") będzie w jakimś listenerze do klików myszy...
                    //blockButtons
                }
                if (response.startsWith("MISSED")) {
                    //displayMissed
                    //colorTheMissedCellOnEnemyBoard
                    //out.println("YOUR TURN")
                }
                if (response.startsWith("HIT") || response.startsWith("SANK")) {
                    //displayThatWeHaveHit
                    //colorTheHitCellOnEnemyBoard
                    //unblockButtons
                    //askForNextShot
                    //out.println("SHOOT XY"), gdzie X to litera Y to nr
                    //mozliwe ze out.println("SHOOT XY") będzie w jakimś listenerze do klików myszy...
                    //blockButtons
                }
                if (response.startsWith("LEFT")) {
                    //displayThatOpponentHasLeft
                    //displayWinnerWindow
                    break;
                }
            }
            out.println("LEFT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost");
        client.playListen();

    }
}