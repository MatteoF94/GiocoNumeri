import java.io.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Matteo on 21/04/16.
 */
public class Scoreboard{
    private static Scoreboard ourInstance = null;
    private Vector<Player> board = new Vector<>();
    private String score_path;
    private int size;

    public static Scoreboard getInstance(String score_path){
        if(ourInstance == null) {
            ourInstance = new Scoreboard(score_path);
        }
        return ourInstance;
    }

    private Scoreboard(String score_path){
        String[] tuple;
        this.score_path=score_path;
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

    public void fixSize() {
        int size = 0;
        Iterator iter = board.iterator();
        while(iter.hasNext()) {
            iter.next();
            size++;
        }
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public synchronized void saveBoard() {
        try {
            File file = new File(this.score_path);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            Iterator iter = board.iterator();
            while(iter.hasNext()) {
                Player player = (Player) iter.next();
                String rec = player.getInfoToSave();
                writer.write(rec);
            }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Player findPlayer(String username, String password) {
        Iterator iter = board.iterator();
        while(iter.hasNext()) {
            Player player = (Player) iter.next();
            if (player.getUsername().equals(username)) {
                if(player.getPassword().equals(password)) {
                    fixSize();
                    return player;
                }
                else {
                    return null;
                }
            }
        }
        Player player = new Player(username,password);
        board.add(player);
        fixSize();
        saveBoard();
        return player;
    }

    public Vector<Player> getBoard(){
        return this.board;
    }
}
