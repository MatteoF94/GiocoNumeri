import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Matteo on 21/04/16.
 */
public class Scoreboard{
    private static Scoreboard ourInstance = null;
    private Vector<Player> board = new Vector<>();

    public static Scoreboard getInstance(String score_path){
        if(ourInstance == null) {
            ourInstance = new Scoreboard(score_path);
        }
        return ourInstance;
    }

    private Scoreboard(String score_path){
        String[] tuple;
        try {
            File file = new File(score_path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                tuple = line.split("%%");
                Player player = new Player(tuple[0], tuple[1], Integer.parseInt(tuple[2]));
                board.add(player);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getSize() {
        int size = 0;
        Iterator iter = board.iterator();
        while(iter.hasNext()) {
            iter.next();
            size++;
        }
        return size;
    }

    public void printBoard() {
        Iterator iter = board.iterator();
        while(iter.hasNext()) {
            Player player = (Player) iter.next();
            System.out.println("------------------------");
            System.out.println("Player: "+player.getUsername()+"\nScore: "+player.getScore());
            System.out.println("------------------------");
        }
    }

    public boolean findPlayer(String username, String password) {
        Iterator iter = board.iterator();
        while(iter.hasNext()) {
            Player player = (Player) iter.next();
            if (player.getUsername().equals(username)) {
                return (player.getPassword().equals(password));
            }
        }
        board.add(new Player(username, password));
        return true;
    }

    public Vector<Player> getBoard(){
        return this.board;
    }
}
