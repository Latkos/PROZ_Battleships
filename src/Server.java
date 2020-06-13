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
            var pool = Executors.newFixedThreadPool(1000);
            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(), 1));
                pool.execute(game.new Player(listener.accept(), 2));
            }
        }
    }
}
