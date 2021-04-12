import javax.swing.*;
import java.awt.*;

public class ExerciseCard {
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel calorieLabel;

    /**Default Constructor*/
    public ExerciseCard(){};

    /**Parameterized Constructor
     * @param name Name of exercise
     * @param type Type of exercise
     * @param calories Calories burned per minute of exercise
     */
    public ExerciseCard(String name,String type, String calories){
        nameLabel.setText(name);
        typeLabel.setText(type);
        calorieLabel.setText(calories);
    }

    /**Get Main Panel of Exercise Card
     * @return Main Panel of Exercise Card
     */
    public JPanel getPanel(){
        return mainPanel;
    }
}
