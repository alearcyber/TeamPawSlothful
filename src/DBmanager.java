import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**Default Constructor*/
public class DBmanager {

    private static final String FILENAME = "src//database";
    private static JSONArray dataArray;
    private static final ArrayList<Exercise> exercises = new ArrayList<>();
    private static final ArrayList<Workout> workouts = new ArrayList<>();

    /**Gets data from JSON database file*/
    public static void getData() {
        try {
            FileReader reader = new FileReader(FILENAME);
            JSONParser parser = new JSONParser();
            Object data = parser.parse(reader);
            dataArray = (JSONArray) data;
            dataArray.forEach(exe -> parseExercise((JSONObject) exe));
            dataArray.forEach(exe -> parseWorkout((JSONObject) exe));
        }catch (IOException| ParseException e) {e.printStackTrace();}
    }

    /**Parse database for exercises
     * @param exerciseData: Exercise data from database
     */
    public static void parseExercise(JSONObject exerciseData){
        if(exerciseData.containsKey("exercise")) {
            JSONObject exerciseObjects = (JSONObject) exerciseData.get("exercise");
            String name = (String) exerciseObjects.get("name");
            String type = (String) exerciseObjects.get("type");
            String calories = (String) exerciseObjects.get("calories");
            Exercise exercise = new Exercise(name, calories, type);
            exercises.add(exercise);
        }
    }

    /**Parse database for workouts
     * @param workoutData: Workout data from database
     */
    public static void parseWorkout(JSONObject workoutData){
        if(workoutData.containsKey("workout")) {
                JSONObject workoutObjects = (JSONObject) workoutData.get("workout");
                String name = (String) workoutObjects.get("name");

                ArrayList<String> tempArray = (ArrayList<String>) workoutObjects.get("exercises");

                Workout workout = new Workout(name, tempArray);
                workouts.add(workout);
        }
    }

    /*
    /**Add new exercise to database
     * @param name: Name of the exercise to add
     * @param calories: Calories burned per minute of the exercise to add
     * @param type: Type of the exercise to add
    public static void addExercise(String name, String type, String calories) {
        JSONObject ex = new JSONObject();
        JSONObject exRapper = new JSONObject();

        ex.put("name", name);
        ex.put("type", type);
        ex.put("calories", calories);

        exRapper.put("exercise", ex);
        dataArray.add(exRapper);

        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(dataArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Exercise exerciseToAdd = new Exercise(name, type, calories);
        exercises.add(exerciseToAdd);
    }*/

    /**Add new workout to database
     * @param name: Name of the workout to add
     * @param exercises: Exercises in the workout to add
     */
    private static void addWorkout(String name, String ...exercises) {
        JSONObject workout = new JSONObject();
        JSONObject workoutRapper = new JSONObject();
        ArrayList<String> workoutExercises = new ArrayList<>();

        Collections.addAll(workoutExercises, exercises);

        workout.put("name", name);
        workout.put("exercises", workoutExercises);

        workoutRapper.put("workout", workout);
        dataArray.add(workoutRapper);

        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(dataArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Workout workoutToAdd = new Workout(name, workoutExercises);
        workouts.add(workoutToAdd);
    }

    /**Gets workouts stored in the database
     * @return Workouts stored in database
     */
    public static ArrayList<Workout> getWorkouts(){
        return workouts;
    }

    /**Gets exercises stored in the database
     * @return Exercises stored in database
     */
    public static ArrayList<Exercise> getExercises(){
        return exercises;
    }
}

