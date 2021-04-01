import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBmanager {

    private static FileReader reader;
    private static JSONParser parser = new JSONParser();
    private static Object data;
    private static JSONArray exerciseArray;
    private static ArrayList<Exercise> exercises = new ArrayList<>();
    private static ArrayList<Workout> workouts = new ArrayList<>();
    private static final String fileName = "src//database";

    //given a exercise name, return an object with correct data
    //given name of a workout, return an arraylist, with all the exercise objects

    public static void main(String[] args) throws Exception{
        addExercise("Back-flips", "1", "legs");
        addWorkout("workout1", "Jumping Jacks", "Curls");

        for(Workout e : getWorkouts()){
            System.out.println(e.getName() + " " + e.getWorkouts());
        }

    }

    public static ArrayList<Exercise> getExercises() throws Exception{
        reader = new FileReader(fileName);
        data = parser.parse(reader);
        exerciseArray = (JSONArray) data;
        exerciseArray.forEach( exe -> parseExercise((JSONObject) exe));
        return exercises;
    }

    public static ArrayList<Workout> getWorkouts() throws Exception{
        reader = new FileReader(fileName);
        data = parser.parse(reader);
        exerciseArray = (JSONArray) data;
        exerciseArray.forEach( exe -> parseWorkout((JSONObject) exe));
        return workouts;
    }


    public static void readExercises(String filename) throws Exception{
        reader = new FileReader(filename);
        data = parser.parse(reader);
        exerciseArray = (JSONArray) data;

        exerciseArray.forEach( exe -> parseExercise((JSONObject) exe));
    }

    public static void readWorkouts() throws Exception{
        reader = new FileReader(fileName);
        data = parser.parse(reader);
        exerciseArray = (JSONArray) data;

        exerciseArray.forEach( exe -> parseWorkout((JSONObject) exe));
    }

    public static void parseExercise(JSONObject exerciseData){
        try {
            JSONObject exerciseObjects = (JSONObject) exerciseData.get("exercise");
            String name = (String) exerciseObjects.get("name");
            String type = (String) exerciseObjects.get("type");
            String calories = (String) exerciseObjects.get("calories");
            Exercise exercise = new Exercise(name, calories, type);
            exercises.add(exercise);
        }
        catch(Exception e){}
    }

    public static void parseWorkout(JSONObject workoutData){
        try {
            JSONObject exerciseObjects = (JSONObject) workoutData.get("workout");
            String name = (String) exerciseObjects.get("name");
            String exercises = (String) exerciseObjects.get("exercises");
            Workout workout = new Workout(name, exercises);
            workouts.add(workout);
        }
        catch(Exception e){}
    }

    public static void addExercise(String name, String calories, String type) throws Exception{
        reader = new FileReader(fileName);
        data = parser.parse(reader);
        exerciseArray = (JSONArray) data;
        JSONObject ex = new JSONObject();
        ex.put("name", name);
        ex.put("calories", calories);
        ex.put("type", type);
        JSONObject exRapper = new JSONObject();
        exRapper.put("exercise", ex);
        exerciseArray.add(exRapper);

        //Write JSON file
        try (FileWriter file = new FileWriter(fileName)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(exerciseArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addWorkout(String name, String ...exercises) throws ParseException, IOException{
        reader = new FileReader(fileName);
        data = parser.parse(reader);
        exerciseArray = (JSONArray) data;

        JSONObject workout = new JSONObject();
        workout.put("name", name);
        String exString = "";
        for(String exercise: exercises){
            exString += " " + exercise;
        }
        workout.put("exercises", exString);
        //the workout is constructed at this point
        //create the wrapper
        JSONObject workoutRapper = new JSONObject();
        workoutRapper.put("workout", workout);
        JSONArray rapperList = new JSONArray();
        //rapperList.add(workoutRapper);
        exerciseArray.add(workoutRapper);

        //Write JSON file
        try (FileWriter file = new FileWriter(fileName)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(exerciseArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

