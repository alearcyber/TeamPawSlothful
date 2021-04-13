import java.util.ArrayList;
import java.util.List;

public class WorkoutModel {

    private List<Observer<WorkoutModel>> observers;
    private ArrayList<Exercise> firstPaneList = new ArrayList<>();
    private ArrayList<Exercise> currentPlanA = new ArrayList<>();
    private ArrayList<Exercise> currentPlanB = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Exercise selectedEx;
    private ArrayList<String> reps = new ArrayList<>();
    private String workoutName;
    private Workout selectedFromDB; //workout being selected in the import dropdown

    /**Default Constructor*/
    public WorkoutModel(){
        DBmanager.getData();
        observers = new ArrayList<>();
    }

    /**
     * add an observer to the list of observers to be observed
     * @param observer the observer to be registered
     */
    public void addObserver(Observer<WorkoutModel> observer){
        observers.add(observer);
    }


    /**
     * helper function to use when changes occur to notify the observers
     * that a change has occured
     */
    private void notifyObservers(){
        for(Observer<WorkoutModel> observer: observers){
            observer.update(this);
        }
    }

    /**Updates exercises shown in the Add Exercise panel
     * @param type Type of exercises to show in Add Exercise panel
     * @param type2 Type of exercises to show in Tab Two, Current Plan panel
     */
    public void updateWorkout(String type, String type2) {
        firstPaneList.clear();
        currentPlanB.clear();

        exercises = DBmanager.getExercises();
        for (Exercise e : exercises) {
            if (e.getType().equalsIgnoreCase(type) || type == "Exercise Type") {
                firstPaneList.add(e);
            }
        }
        exercises = getCurrentPlanA();
        for(Exercise e : exercises){
            if (e.getType().equalsIgnoreCase(type2) || type2 == "Exercise Type") {
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
        firstPaneList.add(ex);
        reps.add("0");
        notifyObservers();
    }

    /**Set selected exercise from Add Exercise panel
     * @param selectedEx Exercise selected from Add Exercise panel
     */
    public void setSelectedEx(Exercise selectedEx) {
        this.selectedEx = selectedEx;
        notifyObservers();
    }

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
        return selectedEx;
    }

    /**Get current plan
     * @return Current plan
     */
    public ArrayList<Exercise> getCurrentPlanA(){
        return this.currentPlanA;
    }

    public ArrayList<Exercise> getCurrentPlanB(){
        return this.currentPlanB;
    }

    public void removeExercise(Exercise exercise){
        currentPlanA.remove(exercise);
        notifyObservers();
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

    /**
     * save the current plan to the database
     */
    public void savePlanToDB(){
        ArrayList<String> exerciseNames = new ArrayList<>();
        for(Exercise exercise: currentPlanA){
            exerciseNames.add(exercise.getName());
        }
        DBmanager.addWorkout(workoutName, reps, exerciseNames);
    }

    /**
     * set the name of the workouts
     */
    public void setWorkoutName(String name){
        this.workoutName = name;
        notifyObservers();
    }

    /**
     * sets the workout that is being selected in the import dropdown
     * @param workout
     */
    public void setSelectedFromDB(Workout workout){
        this.selectedFromDB = workout;
    }

    /**
     * removes everything from the plan
     */
    public void clearPlan(){
        firstPaneList.clear();
        currentPlanA.clear();
        currentPlanB.clear();
        notifyObservers();
    }

    /**
     * returns the currently selected workout
     */
    public Workout getSelectedFromDB(){
        return this.selectedFromDB;
    }

}
