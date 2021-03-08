import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DBmanager {

    /** the name of the json file */
    private String filename;

    private static Object jsonObject;

    /** json array that the json object is cast to */
    private static JSONArray jsonarray;


    public DBmanager(String filename)throws IOException, ParseException{
        FileReader reader = new FileReader("src\\database");
        JSONParser parser = new JSONParser();
        jsonObject = parser.parse(reader);
        jsonarray = (JSONArray) jsonObject;
    }

    private static void printExercises(JSONObject json) {
        JSONObject exercise = (JSONObject) json.get("exercise");
        String name = (String) exercise.get("name");
        System.out.println(name);
    }


    private static void testDatabse() throws ParseException, IOException{
        FileReader reader = new FileReader("src\\database");
        JSONParser parser = new JSONParser();
        Object json = parser.parse(reader);
        JSONArray array = (JSONArray) json;

        reader.close();


        array.forEach( emp -> printExercises( (JSONObject) emp ) );
    }

    public static void main(String[] args) throws ParseException, IOException {
        testDatabse();

    }
}
