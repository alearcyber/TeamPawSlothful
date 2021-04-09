import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App implements Observer<WorkoutModel>{

    private static final JFrame frame = new JFrame("My Exercise Planner");
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel ExercisesPanel;
    private JPanel DetailPanel;
    private JButton addExerciseButton;
    private JPanel currentPlanA;
    private JComboBox comboBox2;
    private JPanel currentPlanB;

    private JComboBox comboBox1;

    private WorkoutController controller;
    private WorkoutModel model;

    public App(){
        model = new WorkoutModel();
        model.addObserver(this);
        controller = new WorkoutController(model);
        controller.updateWorkouts(comboBox1.getSelectedItem().toString());


        ExercisesPanel.setLayout(new BoxLayout(ExercisesPanel, BoxLayout.X_AXIS));
        DetailPanel.setLayout(new BoxLayout(DetailPanel, BoxLayout.X_AXIS));
        currentPlanA.setLayout(new BoxLayout(currentPlanA, BoxLayout.X_AXIS));
        currentPlanB.setLayout(new BoxLayout(currentPlanB, BoxLayout.X_AXIS));


        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.updateWorkouts(comboBox1.getSelectedItem().toString());
                ExercisesPanel.revalidate();
            }
        });


        addExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.addToPlan(model.getSelectedEx());
                }catch(NullPointerException ex){
                    System.out.println("No exercise selected");
                }
            }
        });
    }


    @Override
    public void update(WorkoutModel model) {
        ExercisesPanel.removeAll();
        //Integer index=1;
        for(Exercise exercise: model.getFirstPaneList()){
            String calString = "Cal / Min: " + exercise.getCalories();
            BoxFillerRatio filler = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), calString).getPanel(),
                    BoxLayout.Y_AXIS);
            filler.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    BoxFillerRatio temp = new BoxFillerRatio(3,4,
                            new ExerciseCard(exercise.getName(),exercise.getType(), calString).getPanel(),
                            BoxLayout.Y_AXIS);
                    DetailPanel.removeAll();
                    DetailPanel.add(temp);
                    mainPanel.revalidate();

                    //tracking selected exercise
                    controller.setSelected(exercise);
                }
            });
            ExercisesPanel.add(filler);
        }
        mainPanel.revalidate();


        //setting the selected exercise
        currentPlanA.removeAll();
        for(Exercise exercise: model.getCurrentPlan()){
            BoxFillerRatio newbox = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            currentPlanA.add(newbox);
        }
        mainPanel.revalidate();


        //setting the first panel on the second page here
        currentPlanB.removeAll();
        for(Exercise exercise: model.getCurrentPlan()){
            BoxFillerRatio newbox = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            currentPlanB.add(newbox);
        }
        mainPanel.revalidate();
    }

    public static void main(String[] args) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
        frame.setLocation(new Point((int) size.getWidth() / 4, (int) size.getHeight() / 4));
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
