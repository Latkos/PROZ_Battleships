import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class Client {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

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
    public void playListen() throws Exception {
        try {
            out.println("MELLON"); //test
            while (true) {
                if (in.hasNextLine()) {
                    String response = in.nextLine();
                    System.out.println(response);
                    if (response.startsWith("WINNER")) {
                        //displayWinnerWindow (czy jakkolwiek tam sygnalizujemy wygraną...)
                        break;
                    }
                    if (response.startsWith("SHOOT X Y")) {
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
                    out.println("HIT")
                    }
                        */
                    }
                    if (response.startsWith("YOUR TURN")) {
                        //unblockButtons (mam na mysli pola)
                        //askThePlayerForNextShot (to z klawiatury zapewne leci)
                        //out.println("SHOOT XY"), gdzie X to litera Y to nr
                        //mozliwe ze out.println("SHOOT XY") będzie w jakimś listenerze do klików myszy...
                        //blockButtons
                    }
                    if (response.startsWith("MISSED")) {
                        //displayMissed (to GUI znowu jakies okienko)
                        //ustaw pozycje ostatnio strzelonego jako nietrafiona (na planszy enemyboard)
                        //out.println("YOUR TURN")
                    }
                    if (response.startsWith("HIT") || response.startsWith("SANK")) {
                        //displayThatWeHaveHit (cos do wypisania w glownym oknie)
                        //colorTheHitCellOnEnemyBoard (znowu mamy tutaj funkcje juz do ustalenia ostatniej pozycji jako nietrafiona)
                        //unblockButtons
                        //askForNextShot
                        //out.println("SHOOT XY"), gdzie X to litera Y to nr
                        //mozliwe ze out.println("SHOOT XY") będzie w jakimś listenerze do klików myszy...
                        //blockButtons
                    }
                    if (response.startsWith("LEFT")) {
                        //displayThatOpponentHasLeft (tez jakies okienko)
                        //displayWinnerWindow (te dwa moga byc razem albo cos, whatever)
                        break;
                    }
                }
            }
                out.println("LEFT");

            } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public void login() {
        boolean didLoginSucceed = false;
        while (!didLoginSucceed) {
            //te  ponizsze linijki nie powinny byc zakomentowane, tylko no nie ma funkcji jeszcze xD
            //displayWindowForLoginAndPassword
            //String login=getLoginFromWindow() TUTAJ PATRYK TRZEBA PRZESLAC JAKOS
            //String password=getPasswordFromWindow
            out.println("test2");
            out.println("test2");
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
        }
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost");
        client.login();
        client.playListen();
    }
}