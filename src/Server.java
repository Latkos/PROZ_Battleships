import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * A server for a multi-player tic tac toe game. Loosely based on an example in
 * Deitel and Deitel’s “Java How to Program” book. For this project I created a
 * new application-level protocol called TTTP (for Tic Tac Toe Protocol), which
 * is entirely plain text. The messages of TTTP are:
 * <p>
 * Client -> Server MOVE <n> QUIT
 * <p>
 * Server -> Client WELCOME <char> VALID_MOVE OTHER_PLAYER_MOVED <n>
 * OTHER_PLAYER_LEFT VICTORY DEFEAT TIE MESSAGE <text>
 */
public class Server {

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58302)) {
            System.out.println("Tic Tac Toe Server is Running...");
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

    /**
     * A Player is identified by a character mark which is either 'X' or 'O'. For
     * communication with the client the player has a socket and associated Scanner
     * and PrintWriter.
     */
    class Player implements Runnable {
        int number;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket, int number) {
            this.socket = socket;
            this.number = number;
        }

        @Override
        public void run() {
            try {
                setup();
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

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + number);
            if (number == 1) {
                currentPlayer = this;
                System.out.println("MESSAGE Waiting for opponent to connect");
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("MESSAGE Your move");
            }
        }

        private void processPlayerCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                    //tutaj np. do zapisu przebiegu gry w JSONie bedzie mozna te wiadomosci odpowiednio przetwarzac
                    //ale na razie ten serwer to jest bardziej taki przekaznik
                    // no i jakis warunek konczacy by sie przydalo dodac
                opponent.output.println(command);
            }
        }


    }
}