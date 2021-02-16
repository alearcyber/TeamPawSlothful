import javax.swing.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * gui class for sudoku, represents the veiw,
 * actually not sure what part of MVC it is...
 * will code stuff and find out later
 *
 * @Author Aidan Lear, atl9004, 5/22/2019
 */

public class WorkoutGUI extends JFrame implements Observer<WorkoutModel> {



    /** instance of the model here */
    WorkoutModel model;

    /** instance of the controller here */
    WorkoutController controller;





    public void init(){
        System.out.println("starting initialization...");

        model = new WorkoutModel();
        controller = new WorkoutController(model);
        model.addObserver(this);

        //todo finish initialization...


        System.out.println("initialization completed.");
    }




    public void stop(){
        System.out.println("stop was called....");
    }


    /**
     * lets us know that changes have been made to the model.
     * From here, we update the changes in the view by reading the model
     * @param WorkoutModel
     */
    @Override
    public void update(WorkoutModel WorkoutModel) {
        //TODO update the gui based on the model?
    }


    public static void main(String[] args){
        System.out.println("Testing the GUI...");
    }

}