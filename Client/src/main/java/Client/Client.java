package Client;

import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


/**
 * The type Client.
 * It serves the connection with a server
 */
public class Client {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Thread msgListener;
    /**
     * The Controller.
     */
    ClientWindowController controller;
    private boolean canStartYet = false;



    private boolean didLose=false;
    private boolean loginSuccess;

    /**
     * Instantiates a new Client.
     *
     * @param serverAddress the server address
     * @throws Exception the exception
     */
    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 12345);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Did lose boolean.
     *
     * @return the boolean
     */
    public boolean DidLose() {
        return didLose;
    }

    /**
     * Sets did lose.
     *
     * @param didLose the did lose
     */
    public void setDidLose(boolean didLose) {
        this.didLose = didLose;
    }

    private void playListen() {
        msgListener = new Thread(() -> {
            try {
                boolean theGameHasEnded=false;
                while (!theGameHasEnded) {
                    if (in.hasNextLine()) {
                        waitTillAbleToPlay();
                        String response = in.nextLine();
                        if (response.startsWith("ENEMY ")) {
                            controller.usrMsgHandle(response.substring(6));
                        }
                        if (response.startsWith("WINNER")) {
                            controller.winnerMsgHandle();
                            theGameHasEnded=true;
                        }
                        if (response.startsWith("SHOOT")) {
                            int x = Character.getNumericValue(response.charAt(6));
                            int y = Character.getNumericValue(response.charAt(7));
                            controller.shootMsgHandle(x, y);
                        }

                        if (response.startsWith("YOUR TURN")) {
                            controller.yourTurnMsgHandle();
                        }
                        if (response.startsWith("MISSED")) {

                            controller.missedMsgHandle();
                        }
                        if (response.startsWith("HIT")) {
                            controller.hitMsgHandle();
                        }
                        if (response.startsWith("SANK")) {
                            int x = Character.getNumericValue(response.charAt(6));
                            int y = Character.getNumericValue(response.charAt(7));
                            boolean isV = (Character.getNumericValue(response.charAt(8)) == 1);
                            int length = Character.getNumericValue(response.charAt(9));
                            controller.sankMsgHandle(x, y, isV, length);
                        }
                        if (response.startsWith("LEFT")) {
                            controller.winnerMsgHandle();
                            theGameHasEnded=true;
                        }
                    }
                    else if (!didLose){
                        controller.lostServerConnectionHandler();
                        break;
                    }
                }

            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        });
    }

    private void waitTillAbleToPlay() {
        while (!canStartYet) {
            String response = in.nextLine();
            if (response.startsWith("YOU CAN PLAY NOW")) {
                canStartYet = true;
            }
        }
    }

    /**
     * Sets controller.
     *
     * @param cwc the ClientWindowController
     */
    public void setController(ClientWindowController cwc) {
        controller = cwc;
    }

    /**
     * Start to listen to the server.
     */
    public void startToListen() {
        playListen();
        msgListener.start();
    }

    /**
     * Stop to listen to the server.
     */
    public void stopToListen() {
        if (msgListener != null && msgListener.isAlive()) {
            msgListener.interrupt();
        }
    }

    /**
     * Send message to the server.
     *
     * @param msg the msg
     */
    public void sendMessage(String msg) {
            out.println(msg);
    }


    /**
     * The method logins in to the game and return the result of that operation.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     */
    public boolean login(String login, String password) {
        loginSuccess = false;
        out.println(login);
        out.println(password);
        while (!in.hasNextLine()) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String response = in.nextLine();
        if (response.startsWith("LOGIN SUCCESSFUL")) {
            loginSuccess = true;
        }
        return loginSuccess;
    }

    /**
     * Gets loginSuccess - information about the result of the last attempt to log in.
     *
     * @return the login success
     */
    public boolean getLoginSuccess() {
        return loginSuccess;
    }
}