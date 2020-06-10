import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(12345)) {
            System.out.println("Server is Running...");
            var pool = Executors.newFixedThreadPool(2);
            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(), 1));
                pool.execute(game.new Player(listener.accept(), 2));
            }
        }
    }
}

class Game {


    Player currentPlayer;
    public int playerAmount;


    class Player implements Runnable {
        int number;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        String username;
        public Player(Socket socket, int number) {
            this.socket = socket;
            this.number = number;
        }

        @Override
        public void run() {
            try {
                setup();
                login();
                processPlayerCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void login() throws InterruptedException {
            boolean playerLoggedIn=false;
            while(!playerLoggedIn) {
                username = input.nextLine();
                String password = input.nextLine();
                var connector = new DatabaseConnector();
                try {
                    playerLoggedIn=connector.loginUser(username, password);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (playerLoggedIn)
                    output.println("LOGIN SUCCESSFUL");
                else{
                    output.println("INCORRECT PASSWORD");
                    while(!input.hasNextLine())
                        Thread.sleep(500);
                }
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);

        }

        private void processPlayerCommands() {

            output.println("WELCOME " + number);
            //to się wytnie, na razie tego używam żeby sprawdzić
            if (number == 1) {
                currentPlayer = this;
                System.out.println("MESSAGE Waiting for opponent to connect");
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("MESSAGE Your move");
            }
            //TEGO WHILE TRUE NIE BEDZIE, ALBO TUTAJ CHOCIAZ JAKIS WARUNEK, ALBO JAKIS INTERRUPTED SLEEP CZY COS
            //doczytam jeszcze o tym, ale no while (true) to nie jest fajna praktyka, robie to dla testow zeby sprawdzic czy nasluchuja

            while (true) {
                while (opponent==null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(input.hasNextLine()) {
                    var command = input.nextLine();
                    //tutaj np. do zapisu przebiegu gry w JSONie bedzie mozna te wiadomosci odpowiednio przetwarzac
                    //ale na razie ten serwer to jest bardziej taki przekaznik
                    // no i jakis warunek konczacy by sie przydalo dodac

                    opponent.output.println(command);
                }
            }
        }


    }
}