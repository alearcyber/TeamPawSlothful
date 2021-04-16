import java.util.ArrayList;
import java.util.HashMap;

public class Workout {

    private String name;
    private ArrayList<String> exercises = new ArrayList<>();
    private HashMap<String, Integer> reps = new HashMap<>();

    /**Default Constructor*/
    public Workout(){}

    /**Parameterized Constructor
     * @param name The name of this workout
     * @param exercises The exercises in this workout
     */
    public Workout(String name, HashMap<String, Integer> reps,ArrayList<String> exercises){
        this.name = name;

        //copy over the hashmap
        for (String exerciseName: reps.keySet()) { //exercise names from the hashmap
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

    public HashMap<String, Integer> getReps(){ return reps;}

    public void setReps(String exerciseName, int numberOfReps){
        reps.put(exerciseName, numberOfReps);
    }
}
