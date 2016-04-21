/**
 * Created by Matteo on 21/04/16.
 */
public class Player {
    private String username;
    private String password;
    private int score;

    public Player(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public Player(String username, String password, int score) {
        this(username, password);
        this.score = score;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getScore() {
        return this.score;
    }

    public String getRecord() {
        return username+" "+score;
    }
}
