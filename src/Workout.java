import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Workout {

    private String name;
    private ArrayList<String> exercises = new ArrayList<>();

    /**Default Constructor*/
    public Workout(){}

    /**Parameterized Constructor
     * @param name The name of this workout
     * @param exercises The exercises in this workout
     */
    public Workout(String name, String ...exercises){
        this.name = name;
        List<String> temp = Arrays.asList(exercises);
        this.exercises = new ArrayList<>(temp);
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
