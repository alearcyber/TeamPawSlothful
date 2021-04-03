import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class App implements Observer<WorkoutModel>{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JPanel ExercisesPanel;

    private WorkoutController controller;
    private WorkoutModel model;





    public App(){
        model = new WorkoutModel();
        model.addObserver(this);
        controller = new WorkoutController(model);


        ExercisesPanel.setLayout(new BoxLayout(ExercisesPanel, BoxLayout.X_AXIS));
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateWorkouts(comboBox1.getSelectedItem().toString());
                ExercisesPanel.revalidate();
            }
        });
    }


    @Override
    public void update(WorkoutModel model) {
        ExercisesPanel.removeAll();
        for(Exercise exercise: model.firstPaneList){
            JPanel temp2 = new JPanel();
            BoxFillerRatio temp=new BoxFillerRatio(3,4,temp2,BoxLayout.Y_AXIS);
            temp2.setLayout(new BoxLayout(temp2,BoxLayout.Y_AXIS));
            temp.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    System.out.println(exercise.getName());
                }
            });
            temp2.add(new JLabel(exercise.getName()));
            temp2.add(new JLabel(exercise.getType()));
            temp2.add(new JLabel(exercise.getCalories()));
            ExercisesPanel.add(temp);
        }
        panel1.revalidate();
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
