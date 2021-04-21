
/**Class that implements controller design pattern*/
public class WorkoutController {

    /**This class's instance of the model*/
    private WorkoutModel model;

    /**Default Constructor*/
    public WorkoutController(){}

    /**Parameterized Constructor
     * @param model This class's instance of the model
     */
    public WorkoutController(WorkoutModel model){
        this.model = model;
    }

    /**Add selected exercise from Add Exercise panel to Current Plan panel
     * @param ex Exercise from Add Exercise panel to Current Plan panel
     */
    public void addToPlan(Exercise ex){
        model.addToPlan(ex);
    }

    /**Remove given exercise from current plan
     * @param exercise Exercise to remove
     */
    public void removeExercises(Exercise exercise){
        model.removeExercise(exercise);
    }

    /**Update exercises in the Add Exercises panel and Tab Two, Current Plan panel
     * @param type Type of exercises to show in Add Exercises panel
     * @param type2 Type of exercises to show in Tab Two, Current Plan panel
     */
    public void updateWorkouts(String type, String type2){
        try {
            model.updateWorkout(type, type2);
        }catch(Exception e){e.printStackTrace();}
    }

    /**Import selected workout from database*/
    public void importPlan(){
        model.clearPlan();
        Workout selectedWorkout = model.getSelectedWorkout();
        for(String exerciseNames: selectedWorkout.getExercisesInWorkout()){
            for(Exercise exercise: DBmanager.getExercises()){
                if(exercise.getName().equals(exerciseNames)){
                    model.addToPlan(exercise);
                }
            }
        }
    }

    /**Export selected workout to database
     * @param workoutName Name of workout to export to database
     */
    public void exportPlan(String workoutName){
        model.savePlanToDB(workoutName);
        model.notifyObservers();
    }

    /**Set selected exercise from Add Exercise panel
     * @param exercise Exercise selected from Add Exercise panel
     */
    public void setSelectedExercise(Exercise exercise){
        ExerciseSettings.setMult(exercise.getCalories());
        model.setSelectedExercise(exercise);
    }

    /**Set selected workout from Import Workout drop down menu
     * @param workout Selected workout from Import Workout drop down menu
     */
    public void setSelectedWorkout(Workout workout){model.setSelectedWorkout(workout);}

    /**Set the number of repetitions to perform for selected exercise
     * @param numberOfReps Number of repetitions to perform
     */
    public void setReps(int numberOfReps){
        model.setReps(numberOfReps);
        model.notifyObservers();
    }
}
