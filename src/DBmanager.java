import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBmanager {

    private static final String fileName = "src//database";
    private static FileReader reader;
    private static JSONParser parser = new JSONParser();
    private static Object data;
    private static JSONArray dataArray;
    private static ArrayList<Exercise> exercises = new ArrayList<>();
    private static ArrayList<Workout> workouts = new ArrayList<>();

    /**Main method, constructs model from database*/
    public static void main(String[] args) {
        getData();
        /*Un-suppress to test adding exercises to database
        System.out.println(getExercises());
        addExercise("Back-flips", "legs", "1");
        System.out.println(getExercises());


        /*Un-suppress to test adding workouts to database
        System.out.println(getWorkouts());
        addWorkout("workout2", "Back-flips", "Curls");
        System.out.println(getWorkouts());
        */
    }

    /**Gets data from JSON database file
     * @throws Exception: IOException, ParseException, FileNotFoundException
     */
    public static void getData() {
        try {
            reader = new FileReader(fileName);
            data = parser.parse(reader);
            dataArray = (JSONArray) data;
            dataArray.forEach( exe -> parseExercise((JSONObject) exe));
            dataArray.forEach( exe -> parseWorkout((JSONObject) exe));
        }catch (Exception e) {e.printStackTrace();}

    }

    /**Parse database for exercises
     * @param exerciseData: Exercise data from database
     */
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

    /**Parse database for workouts
     * @param workoutData: Workout data from database
     */
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

    /**Add new exercise to database
     * @param name: Name of the exercise to add
     * @param calories: Calories burned per minute of the exercise to add
     * @param type: Type of the exercise to add
     */
    public static void addExercise(String name, String type, String calories) {
        JSONObject ex = new JSONObject();
        JSONObject exRapper = new JSONObject();

        ex.put("name", name);
        ex.put("type", type);
        ex.put("calories", calories);

        exRapper.put("exercise", ex);
        dataArray.add(exRapper);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(dataArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Exercise exerciseToAdd = new Exercise(name, type, calories);
        exercises.add(exerciseToAdd);
    }

    /**Add new workout to database
     * @param name: Name of the workout to add
     * @param exercises: Exercises in the workout to add
     * @throws Exception: Exception
     */
    private static void addWorkout(String name, String ...exercises) {
        JSONObject workout = new JSONObject();
        String exString = "";
        JSONObject workoutRapper = new JSONObject();

        workout.put("name", name);
        for(String exercise: exercises){
            exString += " " + exercise;
        }
        workout.put("exercises", exString);

        workoutRapper.put("workout", workout);
        dataArray.add(workoutRapper);

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(dataArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Workout workoutToAdd = new Workout(name, exercises);
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

