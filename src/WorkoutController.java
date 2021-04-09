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
    public void updateWorkouts(String type){
        try {
            model.updateWorkout(type);
        }catch(Exception e){e.printStackTrace();}
    }
}
