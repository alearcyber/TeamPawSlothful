import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DBmanager {

    public DBmanager(String filename){

    }

    private static void printExercises(JSONObject json) {
        JSONObject exercise = (JSONObject) json.get("exercise");
        String name = (String) exercise.get("name");
        System.out.println(name);
    }

    public static void main(String[] args) throws ParseException, IOException {
        FileReader reader = new FileReader("src\\database");
        JSONParser parser = new JSONParser();
        Object json = parser.parse(reader);
        JSONArray array = (JSONArray) json;

        reader.close();


        array.forEach( emp -> printExercises( (JSONObject) emp ) );

    }
}
