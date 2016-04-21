/**
 * Created by Matteo on 21/04/16.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private Scoreboard scoreboard= Scoreboard.getInstance("/Users/Matteo/IdeaProjects/GiocoNumeri/src/scoreboard.txt");
    public Server(int port) {
        this.port = port;
    }

    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server ready");
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ServerClientHandler(socket, scoreboard));
            } catch(IOException e){
                break;
            }
        }
        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
    }

    public static void main(String[] args) {
        Server gameServer = new Server(3000);
        gameServer.startServer();
    }
}
