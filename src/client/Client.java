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
    private boolean canStartYet=false;
    public Client(String serverAddress) throws Exception {
        socket = new Socket(serverAddress, 12345);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /* NOTKA: Patryku, w tej chwili wszystkie te funkcje które byłyby wołane z poziomu tej pętli w poniższej metodzie istnieją
        w którejś z tych plansz. Jeszcze trzeba pozmieniać co nieco (np. potem upiększyć kod, zmienić niektóre public na private, te co się da...)
        nie ma funkcji która by przetwarzała kliknięcie użytkownika na jakiś strzał, najlepiej żeby zwracała właśnie stringa w stylu "Shoot 01"
        co oznaczałoby strzał w rząd 1 kolumnę B
        UWAGA WAŻNA RZECZ. GENERALNIE NUMERACJA MOJA JEST JAVOWA TZN. table[1][2] oznacza PIERWSZY RZĄD I DRUGĄ KOLUMNĘ. W STATKACH SIĘ MÓWI ODWROTNIE
        np strzelam w A3, ale tutaj używajmy tej Javowej proszę, bo tak jest w funkcjach :) */

    /*tą funkcję trzeba w osobny wątek jakoś wepchać
     * dorobię przekazywanie controllera do Client,
     * wysyłanie info o strzale, gdzieś indziej się zrobi*/
    private void playListen() {
        msgListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (in.hasNextLine()) {
                            while(!canStartYet){
                                String response=in.nextLine();
                                if(response.startsWith("YOU CAN PLAY NOW")){
                                    canStartYet=true;
                                }
                            }
                            String response = in.nextLine();
                            System.out.println(response);
                            if (response.startsWith("ENEMY USERNAME")){
                                //zrob cos
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
                                controller.hitMsgHandle(); //doda sie nowy kod msg do obslugi sank cos w stylu -> SANK XY isVertical Length
                            }
                            if(response.startsWith("SANK"))
                            {
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

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sendMessage("LEFT");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        boolean didLoginSucceed = false;
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
        if(response.startsWith("SAME USERNAME")){
            System.out.println("Ten sam username");
        }
        if (response.startsWith("LOGIN SUCCESSFUL")) {
            System.out.println("UDALO SIE");
            didLoginSucceed = true;
        }
        if (response.startsWith("INCORRECT PASSWORD")) {
            //displayWrongPasswordWindow
            System.out.println("Niepoprawne haslo");
        }

        return didLoginSucceed;
    }

}