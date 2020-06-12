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
    private void playListen(){
        msgListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.println("MELLON"); //test
                    while (true) {
                        if (in.hasNextLine()) {
                            String response = in.nextLine();
                            System.out.println(response);
                            if (response.startsWith("WINNER")) {
                                controller.winnerMsgHandle();
                                //displayWinnerWindow (czy jakkolwiek tam sygnalizujemy wygraną...)
                                break;
                            }
                            if (response.startsWith("SHOOT")) {
                                //parseTheCoordinatesFromResponse
                    /*if (allShipsAreSunk){
                    out.println("WINNER")
                    displayLoserWindow
                    break;
                    }
                    int result=isHit();
                    if (result==0){
                    zamaluj w GUI jako pudlo (stan juz jest zmieniony po funkcji isHit(), moze potrzeba jakiejs tylko funkcji odswiezajacej? :D )
                    out.println("MISSED");
                    }
                    if (result==1){
                    zamaluj w gui jako zatopiony (stan juz jest zmieniony po funkcji isHit(), moze potrzeba jakiejs tylko funkcji odswiezajacej? :D )
                    out.println("SANK");
                    }
                    if (result==2){
                    zamaluj w gui jako trafione pole (stan juz jest zmieniony po funkcji isHit(), moze potrzeba jakiejs tylko funkcji odswiezajacej? :D )
                    out.println("HIT")*/

                            int x = Character.getNumericValue(response.charAt(6));
                            int y = Character.getNumericValue(response.charAt(7));

                                controller.shootMsgHandle(x, y);
                            }

                            if (response.startsWith("YOUR TURN")) {
                                //unblockButtons (mam na mysli pola)
                                //askThePlayerForNextShot (to z klawiatury zapewne leci)
                                //out.println("SHOOT XY"), gdzie X to litera Y to nr
                                //mozliwe ze out.println("SHOOT XY") będzie w jakimś listenerze do klików myszy...
                                //blockButtons
                                controller.yourTurnMsgHandle();
                            }
                            if (response.startsWith("MISSED")) {
                                //displayMissed (to GUI znowu jakies okienko)
                                //ustaw pozycje ostatnio strzelonego jako nietrafiona (na planszy enemyboard)
                                //out.println("YOUR TURN")
                                controller.missedMsgHandle();
                            }
                            if (response.startsWith("HIT") || response.startsWith("SANK")) {
                                //displayThatWeHaveHit (cos do wypisania w glownym oknie)
                                //colorTheHitCellOnEnemyBoard (znowu mamy tutaj funkcje juz do ustalenia ostatniej pozycji jako nietrafiona)
                                //unblockButtons
                                //askForNextShot
                                //out.println("SHOOT XY"), gdzie X to litera Y to nr
                                //mozliwe ze out.println("SHOOT XY") będzie w jakimś listenerze do klików myszy...
                                //blockButtons

                                controller.sankHitMsgHandle(); //doda sie nowy kod msg do obslugi sank cos w stylu -> SANK XY isVertical Length
                            }
                            if (response.startsWith("LEFT")) {
                                //displayThatOpponentHasLeft (tez jakies okienko)
                                //displayWinnerWindow (te dwa moga byc razem albo cos, whatever)
                                controller.winnerMsgHandle();
                                break;
                            }
                        }
                    }
                    sendMessage("LEFT");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }});
    }

    public void setController(ClientWindowController cwc)
    {
        controller = cwc;
    }
    public void startToListen()
    {
        playListen();
        msgListener.start();
    }

    public void stopToListen()
    {
        if (msgListener != null && msgListener.isAlive()) {
            msgListener.interrupt();
        }
    }

    public void sendMessage(String msg)
    {
        out.println(msg);
    }


    public boolean login(String login, String password) {
        boolean didLoginSucceed = false;
            //te  ponizsze linijki nie powinny byc zakomentowane, tylko no nie ma funkcji jeszcze xD
            //displayWindowForLoginAndPassword
            //String login=getLoginFromWindow() TUTAJ PATRYK TRZEBA PRZESLAC JAKOS
            //String password=getPasswordFromWindow
            out.println(login);
            out.println(password);
            while (!in.hasNextLine()) {
                try{
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String response = in.nextLine();
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