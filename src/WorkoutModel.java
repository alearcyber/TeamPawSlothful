import java.util.ArrayList;
import java.util.List;

/**
 * this class will serve as the model in the mvc to hold the data that belongs in the
 * application.
 *
 * @Author - add your name when you make changes and whatnot
 * Aidan Lear
 *
 * Date - 2/15/2021
 *
 * Notes:
 *  currently this will be my strating point with regards to coding. Also testing git a little
 */
public class WorkoutModel {

    /** list of observers to notify when something changes */
    private List<Observer<WorkoutModel>> observers;

    private ArrayList<Exercise> firstPaneList = new ArrayList<>(); //exercises to be displayed

    private ArrayList<Exercise> currentPlan = new ArrayList<>();

    private Exercise selectedEx;

    //private ArrayList<Exercise> listOfExercises;

    //private DBmanager database;


    //public WokroutModel(){
        //databse.getall the exersicse
    //}
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



    public void updateWorkout(String type) throws Exception{
        firstPaneList.clear();
        ArrayList<Exercise> exercises = DBmanager.getExercises();

        for(Exercise e: exercises){

            if(e.getType().toLowerCase().equals(type.toLowerCase()) || type == "Exercise Type"){
                firstPaneList.add(e);
            }
        }
        notifyObservers();
    }

    public ArrayList<Exercise> getFirstPaneList(){
        return firstPaneList;
    }


    public void setSelectedEx(Exercise selectedEx) {
        this.selectedEx = selectedEx;
        notifyObservers();
    }

    public Exercise getSelectedEx(){
        return selectedEx;
    }

    public ArrayList<Exercise> getCurrentPlan(){
        return this.currentPlan;
    }

    /***
     * add an exercise to the plan
     * @param ex exercise to be added
     */
    public void addToPlan(Exercise ex){
        currentPlan.add(ex);
        notifyObservers();
    }
}
