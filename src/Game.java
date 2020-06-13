import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

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