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

    public Game() {
        saver = new JSONSaver();
    }

    Player firstPlayer;
    Player secondPlayer;
    public JSONSaver saver;

    class Player implements Runnable {
        int number;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        String username;
        boolean isReady = false;

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
                    opponent.output.println("LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void login() throws InterruptedException {
            boolean playerLoggedIn = false;
            while (!playerLoggedIn) {

                username = input.nextLine();
                String password = input.nextLine();
                if (opponent != null && username.equals(opponent.username)) {
                    output.println("SAME USERNAME");
                    continue;
                }

                var connector = new DatabaseConnector();
                try {
                    playerLoggedIn = connector.loginUser(username, password);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                if (playerLoggedIn) {
                    output.println("LOGIN SUCCESSFUL");
                    connector.closeConnection();
                } else {
                    output.println("INCORRECT PASSWORD");
                    while (!input.hasNextLine())
                        Thread.sleep(50);
                }
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            if (number == 1) {
                firstPlayer = this;
            } else {
                secondPlayer = this;
                opponent = firstPlayer;
                opponent.opponent = this;
            }

        }

        private void processPlayerCommands() {

            //TEGO WHILE TRUE NIE BEDZIE, ALBO TUTAJ CHOCIAZ JAKIS WARUNEK, ALBO JAKIS INTERRUPTED SLEEP CZY COS
            //doczytam jeszcze o tym, ale no while (true) to nie jest fajna praktyka, robie to dla testow zeby sprawdzic czy nasluchuja
            boolean haveWeInformedAboutReadiness = false;
            while (opponent == null) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            waitForBothPlayersReady();
            while (true) {
                if (input.hasNextLine()) {
                    var command = input.nextLine();
                    saver.put(username, command);
                    opponent.output.println(command);
                    if (command.startsWith("WINNER") || command.startsWith("LEFT"))
                        break;
                }
            }
            saver.save(); //to sie nigdy nie odbedzie dopoki nie poprawi sie tego while (true), na ten moment zostawiam-wiem ze JSON dziala

        }

        private void waitForBothPlayersReady() {
            while (!isReady) {
                if (input.hasNextLine()) {
                    var command = input.nextLine();
                    if (command.startsWith("READY"))
                        isReady = true;
                }
            }
            output.println("YOU CAN PLAY NOW");
            while (!opponent.isReady) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (this == firstPlayer) {
                System.out.println("WSZEDLEM W TO");
                output.println("YOUR TURN");
                output.println("ENEMY USERNAME " + opponent.username);
                opponent.output.println("ENEMY USERNAME " + username);
            }
        }


    }
}