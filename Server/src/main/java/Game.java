import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * The type Game.
 */
class Game {

    /**
     * Instantiates a new Game.
     */
    public Game() {
        saver = new JSONSaver();
    }

    /**
     * The First player.
     */
    Player firstPlayer;
    /**
     * The Second player.
     */
    Player secondPlayer;
    /**
     * The Saver.
     */
    public JSONSaver saver;

    /**
     * The type Player.
     */
    class Player implements Runnable {
        /**
         * The Number.
         */
        int number;
        /**
         * The Opponent.
         */
        Player opponent;
        /**
         * The Socket.
         */
        Socket socket;
        /**
         * The Input.
         */
        Scanner input;
        /**
         * The Output.
         */
        PrintWriter output;
        /**
         * The Username.
         */
        String username;
        /**
         * The Is ready.
         */
        boolean isReady = false;

        /**
         * Instantiates a new Player.
         *
         * @param socket the socket
         * @param number the number
         */
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
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void login() {
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
                    while (!input.hasNextLine()) {
                        sleep();
                    }
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

        /**
         * Sleep.
         */
        void sleep() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void processPlayerCommands() {

            while (opponent == null) sleep();
            waitForBothPlayersReady();
            boolean theGameHasEnded=false;
            while (!theGameHasEnded) {
                if (input.hasNextLine()) {
                    var command = input.nextLine();
                    saver.put(username, command);
                    opponent.output.println(command);
                    if (command.startsWith("WINNER") || command.startsWith("LEFT"))
                        theGameHasEnded=true;
                }
            }
            saver.save();

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
            while (!opponent.isReady) sleep();
            if (this == firstPlayer) {
                output.println("ENEMY " + opponent.username);
                output.println("YOUR TURN");
                opponent.output.println("ENEMY " + username);
            }
        }


    }
}