public class WorkoutController {

    private WorkoutModel model;

    /**Default Constructor*/
    public WorkoutController(){}

    /**Parameterized Constructor
     * @param model this classes instance of the model
     */
    public WorkoutController(WorkoutModel model){
        this.model = model;
    }

    /**Set selected exercise from Add Exercise panel
     * @param exercise Exercise selected from Add Exercise panel
     */
    public void setSelectedExercise(Exercise exercise){
        model.setSelectedEx(exercise);
    }

    /**Set selected workout from Import Workout drop down menu*/
    public void setSelectedWorkout(Workout workout){model.setSelectedWorkout(workout);}

    /**Add selected exercise from Add Exercise panel to Current Plan panel
     * @param ex Exercise from Add Exercise panel to Current Plan panel
     */
    public void addToPlan(Exercise ex){
        model.addToPlan(ex);
    }

    /**Update exercises in the Add Exercises panel
     * @param type Type of exercises to show
     */
    public void updateWorkouts(String type, String type2){
        try {
            model.updateWorkout(type, type2);
        }catch(Exception e){e.printStackTrace();}
    }

    public void removeExercises(Exercise exercise){
        model.removeExercise(exercise);
    }

    public void setName(String name){ model.setWorkoutName(name);}



    public void importPlan(){
        model.clearPlan();
        Workout selectedFromDB = model.getSelectedWorkout();
        for(String exerciseNames: selectedFromDB.getExercisesInWorkout()){
            for(Exercise exercise: DBmanager.getExercises()){
                if(exercise.getName().equals(exerciseNames)){
                    model.addToPlan(exercise);
                }
            }
        }
    }

    public void exportPlan(String workoutName){ model.savePlanToDB(workoutName);}
}
