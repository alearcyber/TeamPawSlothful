import java.util.ArrayList;
import java.util.List;

/**Class that implements model design pattern*/
public class WorkoutModel {

    /**List of observers for application*/
    private final List<Observer<WorkoutModel>> observers;

    /**ArrayList of exercises contained in the Add Exercises panel*/
    private final ArrayList<Exercise> firstPaneList = new ArrayList<>();

    /**ArrayList of exercises contained in the Tab One, Current Plan panel*/
    private final ArrayList<Exercise> currentPlanA = new ArrayList<>();

    /**ArrayList of exercises contained in the Tab Two, Current Plan panel*/
    private final ArrayList<Exercise> currentPlanB = new ArrayList<>();

    /**Exercise selected from the Add Exercises panel*/
    private Exercise selectedExercise;

    /**Workout selected from Import Workout drop down menu*/
    private Workout selectedWorkout;

    /** metrics */
    private int totalCalBurnt;
    private int totalTime;
    private String mainMuscleGroup;

    /**Default Constructor
     * @param observer Observer for this model
     */
    public WorkoutModel(Observer<WorkoutModel> observer){
        DBmanager.getData();
        observers = new ArrayList<>();
        observers.add(observer);
    }

    /**Notifies observers when a change has occurred*/
    public void notifyObservers(){
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
        notifyObservers();
    }

    /**Remove an exercise from current plan
     * @param exercise Exercise to remove from current plan
     */
    public void removeExercise(Exercise exercise){
        currentPlanA.remove(exercise);
        notifyObservers();
    }

    /**Save the current plan to the database
     * @param workoutName Name of workout to add to database
     */
    public void savePlanToDB(String workoutName){
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(Exercise exercise: currentPlanA){
            exerciseNames.add(exercise.getName());
        }
        DBmanager.addWorkout(workoutName, selectedWorkout.getReps(), exerciseNames);
    }

    /**Remove everything from Current Plan*/
    public void clearPlan(){
        currentPlanA.clear();
        currentPlanB.clear();
        notifyObservers();
    }

    /**Set selected exercise from Add Exercise panel
     * @param selectedEx Exercise selected from Add Exercise panel
     */
    public void setSelectedExercise(Exercise selectedEx) {
        this.selectedExercise = selectedEx;
        notifyObservers();
    }

    /**Sets the workout selected in the Import Workout drop down menu
     * @param workout The workout selected from the Import Workout drop down menu
     */
    public void setSelectedWorkout(Workout workout){
        this.selectedWorkout = workout;
    }

    /**Sets the number of repetitions for the exercise selected from Tab Two, Current Plan
     * @param numberOfReps Number of repetitions for a given exercise
     */
    public void setReps(int numberOfReps){
        selectedWorkout.setReps(selectedExercise.getName(), numberOfReps);
    }

    /**Get selected exercise from Add Exercise panel
     * @return Selected exercise from Add Exercise panel
     */
    public Exercise getSelectedExercise(){
        return selectedExercise;
    }

    /**Get the currently selected workout from the Import Workout drop down menu
     * @return Selected workout from Import Workout drop down menu
     */
    public Workout getSelectedWorkout(){
        return this.selectedWorkout;
    }

    /**Get list of exercises contained in Add Exercise panel
     * @return List of exercises contained in Add Exercise panel
     */
    public ArrayList<Exercise> getFirstPaneList(){
        return firstPaneList;
    }

    /**Get list of exercises contained in Tab One, Current Plan pane
     * @return List of exercises contained in Tab One, Current Plan pane
     */
    public ArrayList<Exercise> getCurrentPlanA(){
        return this.currentPlanA;
    }

    /**Get list of exercises contained in Tab Two, Current Plan pane
     * @return List of exercises contained in Tab Two, Current Plan pane
     */
    public ArrayList<Exercise> getCurrentPlanB(){
        return this.currentPlanB;
    }

    public void setMainMuscleGroup(String mainMuscleGroup) {
        this.mainMuscleGroup = mainMuscleGroup;
    }

    public void setTotalCalBurnt(int totalCalBurnt) {
        this.totalCalBurnt = totalCalBurnt;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

     public String getMainMuscleGroup(){
        return mainMuscleGroup;
     }

    public int getTotalCalBurnt() {
        return totalCalBurnt;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
