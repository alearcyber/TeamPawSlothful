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

    public ArrayList<Exercise> firstPaneList = new ArrayList<>(); //exercises to be displayed

    //private ArrayList<Exercise> listOfExercises;

    //private DBmanager database;


    //public WokroutModel(){
        //databse.getall the exersicse
    //}
    public WorkoutModel(){
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
        System.out.println("DO I GET HERE????");
        firstPaneList.clear();
        ArrayList<Exercise> whatis = DBmanager.getExercises();

        for(Exercise e: whatis){

            if(e.getType().toLowerCase().equals(type.toLowerCase())){
                System.out.println("adding " + e.getName() + " to the list");
                firstPaneList.add(e);
            }
        }

        notifyObservers();
    }
}
