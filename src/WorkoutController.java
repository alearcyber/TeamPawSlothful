public class WorkoutController {

    private WorkoutModel model;

    /**Default Constructor*/
    public WorkoutController(){};

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


    public void addToPlan(Exercise ex){
        model.addToPlan(ex);
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
