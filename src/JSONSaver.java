import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

public class JSONSaver {
    JSONArray gameCourse;

    public JSONSaver() {
        gameCourse = new JSONArray();
    }

    void put(String username, String response) {
        gameCourse.add(username + " says " + response);
    }

    void save() {
        FileWriter file = null;
        try {
            file = new FileWriter("gameCourse.json");
            file.write(gameCourse.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert file != null;
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
