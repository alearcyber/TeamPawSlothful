import java.util.ArrayList;
import java.util.HashMap;

/**Class that defines a workout*/
public class Workout {

    /**Name of workout*/
    private String name;

    /**ArrayList of exercises in workout*/
    private ArrayList<String> exercises = new ArrayList<>();

    /**Hashmap that relates name of exercise to number of repetitions to perform*/
    private HashMap<String, Integer> reps = new HashMap<>();

    /**Default Constructor*/
    public Workout(){}

    /**Parameterized Constructor
     * @param name The name of this workout
     * @param reps The number of repetitions to perform for each exercise
     * @param exercises The exercises in this workout
     */
    public Workout(String name, HashMap<String, Integer> reps,ArrayList<String> exercises){
        this.name = name;

        for (String exerciseName: reps.keySet()) {
            this.reps.put(exerciseName, reps.get(exerciseName));
        }

        this.exercises.addAll(exercises);
    }

    /**Get name of this workout
     * @return The name of this workout
     */
    public String getName(){
        return this.name;
    }

    /**Get exercises in workout
     * @return The exercises in this workout
     */
    public ArrayList<String> getExercisesInWorkout() {
        return exercises;
    }

    /**Get repetitions of this workout
     * @return HashMap of exercise name keys and repetitions values
     */
    public HashMap<String, Integer> getReps(){ return reps;}

    /**Set the name of a workout
     * @param name New Name of workout
     */
    public void setName(String name){
        this.name = name;
    }

    /**Set the number of repetitions to perform for an exercise
     * @param exerciseName Name of exercise to alter
     * @param numberOfReps Number of repetitions to perform for a given exercise
     */
    public void setReps(String exerciseName, int numberOfReps){
        reps.put(exerciseName, numberOfReps);
    }

    /**Set the exercises contained in a workout
     * @param exercises Exercises to replace those in a workout
     */
    public void setExercises(ArrayList<String> exercises){
        this.exercises.clear();
        this.exercises.addAll(exercises);
    }
}
