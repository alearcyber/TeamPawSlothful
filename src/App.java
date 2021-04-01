import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;

public class App implements Observer<WorkoutModel>{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JPanel ExcercisesPanel;
    //private ArrayList<JPanel> exercisePanels = new ArrayList<>();

    private WorkoutController controller;
    private WorkoutModel model;




    //get exercises
            //for each exercise
            //new JPanel

    public App(){
        model = new WorkoutModel();
        model.addObserver(this);
        controller = new WorkoutController(model);


        ExcercisesPanel.setLayout(new BoxLayout(ExcercisesPanel, BoxLayout.X_AXIS));
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateWorkouts(comboBox1.getSelectedItem().toString());
            }
        });
    }


    @Override
    public void update(WorkoutModel model) {
        System.out.println("UPDATING");
        for(Exercise exercise: model.firstPaneList){
            System.out.println("creating view for an excersize");
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
            //exercisePanels.add(temp);
            ExcercisesPanel.add(temp);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
        frame.setLocation(new Point((int) size.getWidth() / 4, (int) size.getHeight() / 4));
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
