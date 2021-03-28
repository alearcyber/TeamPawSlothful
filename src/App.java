import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;

public class App {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JPanel ExcercisesPanel;
    private ArrayList<JPanel> exercisePanels = new ArrayList<>();


    //get exercises
            //for each exercise
            //new JPanel

    public App() {
        ExcercisesPanel.setLayout(new BoxLayout(ExcercisesPanel,BoxLayout.X_AXIS));
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox1.getSelectedItem());
                ArrayList<Exercise> exerciseList = new ArrayList<>();
                try {
                    exerciseList = DBmanager.getExercises();
                    System.out.println(exerciseList);
                }
                catch(Exception er){
                    System.out.println(er);
                }
                for(Exercise exercise: exerciseList){
                    JPanel temp=new JPanel();
                    temp.setLayout(new BoxLayout(temp,BoxLayout.Y_AXIS));
                    temp.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent me) {
                            System.out.println(exercise.getName());
                        }
                    });
                    temp.add(new JLabel(exercise.getName()));
                    temp.add(new JLabel(exercise.getType()));
                    temp.add(new JLabel(exercise.getCalories()));
                    exercisePanels.add(temp);
                    ExcercisesPanel.add(temp);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
