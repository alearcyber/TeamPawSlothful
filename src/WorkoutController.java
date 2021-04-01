public class WorkoutController {

    /** local instance of the model */
    private WorkoutModel model;

    /**
     * construct and set the model
     * @param model this classes instance of the model
     */
    public WorkoutController(WorkoutModel model){
        this.model = model;
    }


    /**
     * update workouts int he first panel thing
     */
    public void updateWorkouts(String type){
        try {
            model.updateWorkout(type);
        }catch(Exception e){}
    }

}
