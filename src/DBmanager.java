import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**Default Constructor*/
public class DBmanager {

    /**Database file to reference*/
    private static final String FILENAME = "src//database";

    /**Array that contains data from database*/
    private static JSONArray dataArray;

    /**ArrayList that contains exercises in database*/
    private static final ArrayList<Exercise> exercises = new ArrayList<>();

    /**ArrayList that contains workouts in database*/
    private static final ArrayList<Workout> workouts = new ArrayList<>();

    /**Gets data from JSON database file*/
    public static void getData() {
        try {
            FileReader reader = new FileReader(FILENAME);
            JSONParser parser = new JSONParser();
            Object data = parser.parse(reader);
            dataArray = (JSONArray) data;
            dataArray.forEach(e -> parseExercise((JSONObject) e));
            dataArray.forEach(e -> parseWorkout((JSONObject) e));
        }catch (IOException| ParseException e) {e.printStackTrace(); }
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
            JSONArray details = (JSONArray) exerciseObjects.get("details");

            Exercise exercise = new Exercise(name, calories, type, details);
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

            ArrayList<String> exeArray = (ArrayList<String>) workoutObjects.get("exercises");
            ArrayList<String> repArray = (ArrayList<String>) workoutObjects.get("reps");

            //Construct repetitions hash map
            HashMap<String, Integer> reps = new HashMap<>();
            for(int i = 0; i < exeArray.size(); i++){
                reps.put(exeArray.get(i), Integer.parseInt(repArray.get(i)));
            }

            Workout workout = new Workout(name, reps, exeArray);
            workouts.add(workout);
        }
    }

    /**Add new workout to database
     * @param name: Name of the workout to add
     * @param reps: Number of reps tied to a given exercise
     * @param exercises: Exercises in the workout to add
     */
    public static void addWorkout(String name, HashMap<String, Integer> reps, ArrayList<String> exercises) {

        Workout workoutToAdd = new Workout(name, reps, exercises);

        workouts.add(workoutToAdd);

        JSONObject workout = new JSONObject();
        JSONObject workoutRapper = new JSONObject();

        workout.put("name", name);
        workout.put("exercises", exercises);

        //construct reps from hashmap
        ArrayList<String> repsArray = new ArrayList<>();
        for(int i = 0; i < exercises.size(); i++){
            try {
                repsArray.add(reps.get(exercises.get(i)).toString());
            }catch (Exception e) { repsArray.add("0"); }
        }

        workout.put("reps", repsArray);

        workoutRapper.put("workout", workout);
        dataArray.add(workoutRapper);

        //Write workout to database file
        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(dataArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**Removes workout if its name matches the user's export name
     * @param workoutToOverwrite Workout to remove
     */
    public static void overwriteDatabase(String workoutToOverwrite){
        for(int i = 0; i < dataArray.size(); i++){
            try{
                JSONObject temp = (JSONObject) dataArray.get(i);
                if(temp.containsKey("workout")){
                    for(Object p : temp.values()){
                        if(((JSONObject) p).containsValue(workoutToOverwrite)){
                            dataArray.remove(i);
                        }
                    }
                }
            }catch (Exception e) { e.printStackTrace();}
        }

        workouts.clear();
        dataArray.forEach(e -> parseWorkout((JSONObject) e));
    }
}

