import java.util.ArrayList;
import java.util.List;

public class WorkoutModel {

    private List<Observer<WorkoutModel>> observers;
    private ArrayList<Exercise> firstPaneList = new ArrayList<>();
    private ArrayList<Exercise> currentPlan = new ArrayList<>();
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Exercise selectedEx;

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
     */
    public void updateWorkout(String type) {
        firstPaneList.clear();
        exercises = DBmanager.getExercises();

        for(Exercise e: exercises){

            if(e.getType().equalsIgnoreCase(type) || type == "Exercise Type"){
                firstPaneList.add(e);
            }
        }
        notifyObservers();
    }

    /**Add an exercise to current plan
     * @param ex Exercise to be added to current plan
     */
    public void addToPlan(Exercise ex){
        currentPlan.add(ex);
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
    public ArrayList<Exercise> getCurrentPlan(){
        return this.currentPlan;
    }
}
