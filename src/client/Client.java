package client;

import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class Client {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private Thread msgListener;
    ClientWindowController controller;
    private boolean canStartYet = false;
    private boolean loginSuccess;

    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 12345);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void playListen() {
        msgListener = new Thread(() -> {
            try {
                while (true) {
                    if (in.hasNextLine()) {
                        waitTillAbleToPlay();
                        String response = in.nextLine();
                        if (response.startsWith("ENEMY ")) {
                            controller.usrMsgHandle(response.substring(6));
                        }
                        if (response.startsWith("WINNER")) {
                            controller.winnerMsgHandle();
                            break;
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
                            break;
                        }
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

    public void setController(ClientWindowController cwc) {
        controller = cwc;
    }

    public void startToListen() {
        playListen();
        msgListener.start();
    }

    public void stopToListen() {
        if (msgListener != null && msgListener.isAlive()) {
            msgListener.interrupt();
        }
    }

    public void sendMessage(String msg) {
        System.out.println("S: " + msg);
        out.println(msg);
    }


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

    public boolean getLoginSuccess() {
        return loginSuccess;
    }
}