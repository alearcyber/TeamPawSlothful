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
     * @param ex Exercise selected from Add Exercise panel
     */
    public void setSelected(Exercise ex){
        model.setSelectedEx(ex);
    }

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

    public void exportPlan(){model.savePlanToDB();}
    public void setSelectedWorkout(Workout workout){model.setSelectedFromDB(workout);}

    public void importPlan(){
        System.out.println("importing plan...");
        model.clearPlan();
        Workout selectedFromDB = model.getSelectedFromDB();
        System.out.println("importing " + selectedFromDB.getName());
        for(String exercisenames: selectedFromDB.getExercisesInWorkout()){
            System.out.println(exercisenames);
            for(Exercise exercise: DBmanager.getExercises()){
                if(exercise.getName().equalsIgnoreCase(exercisenames)){
                    model.addToPlan(exercise);
                }
            }
        }
    }
}
