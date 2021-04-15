import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**Default Constructor*/
public class DBmanager {

    private static final String FILENAME = "src//database";
    private static JSONArray dataArray;
    private static final ArrayList<Exercise> exercises = new ArrayList<>();
    private static final ArrayList<Workout> workouts = new ArrayList<>();

    /**Main method for testing*/
    public static void main(String[] args) {
        getData();
        for(Workout w : workouts) {
            System.out.println(w.getReps());
        }
        for(Workout w : workouts) {
            System.out.println(w.getReps());
        }
    }

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

            ArrayList<String> exeArray = (ArrayList<String>) workoutObjects.get("exercises");
            ArrayList<String> repArray = (ArrayList<String>) workoutObjects.get("reps");

            Workout workout = new Workout(name, repArray, exeArray);
            workouts.add(workout);
        }
    }

    /**Add new workout to database
     * @param name: Name of the workout to add
     * @param reps: Number of reps tied to a given exercise
     * @param exercises: Exercises in the workout to add
     */
    public static void addWorkout(String name, ArrayList<String> reps, ArrayList<String> exercises) {                   //can only store workouts with new name

        Workout workoutToAdd = new Workout(name, reps, exercises);

        for(Workout w : workouts){
            if(!w.getName().equals(name)){
                workouts.add(workoutToAdd);

                JSONObject workout = new JSONObject();
                JSONObject workoutRapper = new JSONObject();

                workout.put("name", name);
                workout.put("exercises", exercises);
                workout.put("reps", reps);

                workoutRapper.put("workout", workout);
                dataArray.add(workoutRapper);


                try (FileWriter file = new FileWriter(FILENAME)) {
                    file.write(dataArray.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
}

