import java.util.ArrayList;
import java.util.List;

public class WorkoutModel {

    private final List<Observer<WorkoutModel>> observers;
    private final ArrayList<Exercise> firstPaneList = new ArrayList<>();
    private final ArrayList<Exercise> currentPlanA = new ArrayList<>();
    private final ArrayList<Exercise> currentPlanB = new ArrayList<>();
    private final ArrayList<String> reps = new ArrayList<>();

    private Exercise selectedExercise;
    private Workout selectedWorkout;

    /**Default Constructor*/
    public WorkoutModel(Observer<WorkoutModel> observer){
        DBmanager.getData();
        observers = new ArrayList<>();
        observers.add(observer);
    }

    /**Notifies observers when a change has occurred*/
    private void notifyObservers(){
        for(Observer<WorkoutModel> observer: observers){
            observer.update(this);
        }
    }

    /**Updates exercises shown in the Add Exercise panel and the Tab Two, Current Plan panel
     * @param type Type of exercises to show in Add Exercise panel
     * @param type2 Type of exercises to show in Tab Two, Current Plan panel
     */
    public void updateWorkout(String type, String type2) {
        firstPaneList.clear();
        currentPlanB.clear();

        ArrayList<Exercise> exercises = DBmanager.getExercises();
        for (Exercise e : exercises) {
            if (e.getType().equalsIgnoreCase(type) || type.equals("Exercise Type")) {
                firstPaneList.add(e);
            }
        }
        exercises = getCurrentPlanA();
        for(Exercise e : exercises){
            if (e.getType().equalsIgnoreCase(type2) || type2.equals("Exercise Type")) {
                currentPlanB.add(e);
            }
        }

        notifyObservers();
    }

    /**Add an exercise to current plan
     * @param ex Exercise to be added to current plan
     */
    public void addToPlan(Exercise ex){
        currentPlanA.add(ex);
        currentPlanB.add(ex);
        reps.add("0");
        notifyObservers();
    }

    /**Remove an exercise from current plan*/
    public void removeExercise(Exercise exercise){
        currentPlanA.remove(exercise);
        notifyObservers();
    }

    /**Set selected exercise from Add Exercise panel
     * @param selectedEx Exercise selected from Add Exercise panel
     */
    public void setSelectedEx(Exercise selectedEx) {
        this.selectedExercise = selectedEx;
        notifyObservers();
    }

    /**Sets the workout that is being selected in the Import Workout drop down menu
     * @param workout The workout selected from the Import Workout drop down menu
     */
    public void setSelectedWorkout(Workout workout){
        this.selectedWorkout = workout;
    }

    /**
     * function sets the number of reps based on the index.
     * You should give it the index of the corresponding exercise contained
     * within the currentPlanA
     */
    public void setReps(int index, String numberOfReps){
        reps.add(index, numberOfReps);
        notifyObservers();
    }

    /**Set the name of the workout
     * @param name Name of workout to name
     */
    public void setWorkoutName(String name){
        notifyObservers();
    }                                                                         //necessary?

    /**Get list of exercises contained in Add Exercise panel
     * @return List of exercises contained in Add Exercise panel
     */
    public ArrayList<Exercise> getFirstPaneList(){
        return firstPaneList;
    }

    /**Get selected exercise from Add Exercise panel
     * @return Selected exercise from Add Exercise panel
     */
    public Exercise getSelectedEx(){
        return selectedExercise;
    }

    /**Get current plan
     * @return Current plan
     */
    public ArrayList<Exercise> getCurrentPlanA(){
        return this.currentPlanA;
    }

    /**Get plan displayed on Tab Two, Current Plan panel
     * @return Plan displayed on Tab Two, Current Plan panel
     */
    public ArrayList<Exercise> getCurrentPlanB(){
        return this.currentPlanB;
    }

    /**Get the currently selected workout*/
    public Workout getSelectedWorkout(){
        return this.selectedWorkout;
    }





    /**Save the current plan to the database*/
    public void savePlanToDB(String workoutName){
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(Exercise exercise: currentPlanA){
            exerciseNames.add(exercise.getName());
        }
        DBmanager.addWorkout(workoutName, reps, exerciseNames);
    }


    /**Remove everything from Current Plan*/
    public void clearPlan(){
        currentPlanA.clear();
        currentPlanB.clear();
        notifyObservers();
    }

}
