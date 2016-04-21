/**
 * Created by Matteo on 21/04/16.
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Math;

public class ServerClientHandler implements Runnable {
    private Socket socket;
    private Scoreboard scoreboard;
    private int score;

    public ServerClientHandler(Socket socket, Scoreboard scoreboard) {
        this.socket = socket;
        this.scoreboard = scoreboard;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String line = in.nextLine();
            if(line.equals("login")) {
                loginHandler(in, out);
                scoreboard.printBoard();
            }
            label:
            while(true) {
                line = in.nextLine();
                System.out.println("|"+line+"|");
                switch (line) {
                    case "partita":
                        gameHandler(in, out);
                        break;
                    case "record":
                        System.out.println(scoreboard.getSize());
                        showRecords(out);
                        break;
                    case "fine":
                        break label;
                    default:
                        break;
                }
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void loginHandler(Scanner in, PrintWriter out) {
        String username = in.nextLine();
        String password = in.nextLine();
        if(scoreboard.findPlayer(username,password)){
            out.println(true);
            out.flush();
        } else {
            out.println(false);
            out.flush();
        }
    }

    private void showRecords(PrintWriter out) {
        out.println(scoreboard.getSize());
        out.flush();
        Vector<Player> board = scoreboard.getBoard();
        Iterator iter = board.iterator();
        while(iter.hasNext()) {
            Player player = (Player) iter.next();
            out.println(player.getRecord());
            out.flush();
        }
    }

    private void gameHandler(Scanner in, PrintWriter out) {
        String line;
        String sequence_string = "";
        Vector<Integer> sequence = new Vector<>();
        while (true) {
            while (score >= 0) {
                sequence.add((int) (Math.random() * 100));
                score--;
            }
            for(int i=0; i<sequence.size(); i++) {
                sequence_string += sequence.get(i);
                if(i != sequence.size()-1) {
                    sequence_string += " ";
                }
            }
            score = sequence.size() - 1;
            sequence.clear();
            out.println(sequence_string);
            out.flush();

            line = in.nextLine();
            if (line.equals(sequence_string)) {
                System.out.println("Corretto!");
                sequence_string="";
                out.println(true);
                out.flush();
                score++;
            } else {
                System.out.println("Errato...");
                out.println(false);
                out.flush();
                break;
            }
        }
    }
}
