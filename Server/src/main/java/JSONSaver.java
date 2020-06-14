import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Json saver.
 */
public class JSONSaver {
    /**
     * The Game course.
     */
    JSONArray gameCourse;

    /**
     * Instantiates a new Json saver.
     */
    public JSONSaver() {
        gameCourse = new JSONArray();
    }

    /**
     * Put.
     *
     * @param username the username
     * @param response the response
     */
    void put(String username, String response) {
        gameCourse.add(username + " says " + response + "");

    }

    /**
     * Save to json.
     */
    void save() {
        FileWriter file = null;
        try {
            file = new FileWriter("gameCourse.json");
            file.write(gameCourse.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
