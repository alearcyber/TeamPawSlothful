import java.util.ArrayList;

public class Workout {

    private String name;
    private ArrayList<String> exercises = new ArrayList<>();

    /**Default Constructor*/
    public Workout(){}

    /**Parameterized Constructor
     * @param name The name of this workout
     * @param exercises The exercises in this workout
     */
    public Workout(String name, ArrayList<String> exercises){
        this.name = name;
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
    public String getExercisesInWorkout() {
        return exercises.toString();
    }
}
