import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class App implements Observer<WorkoutModel>{

    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private static final JFrame frame = new JFrame("My Exercise Planner");
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JPanel ExercisesPanel;
    private JPanel DetailPanel;
    private JPanel currentPlanA;
    private JPanel currentPlanB;

    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;

    private JButton addExerciseButton;

    private WorkoutModel model;
    private WorkoutController controller;

    /**Default Constructor*/
    public App(){
        model = new WorkoutModel();
        model.addObserver(this);
        controller = new WorkoutController(model);
        controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString());

        ExercisesPanel.setLayout(new BoxLayout(ExercisesPanel, BoxLayout.X_AXIS));
        DetailPanel.setLayout(new BoxLayout(DetailPanel, BoxLayout.X_AXIS));
        currentPlanA.setLayout(new BoxLayout(currentPlanA, BoxLayout.X_AXIS));
        currentPlanB.setLayout(new BoxLayout(currentPlanB, BoxLayout.X_AXIS));

        //Action Listener for pull down box
        comboBox1.addActionListener(e -> {
            controller.updateWorkouts(comboBox1.getSelectedItem().toString());
            ExercisesPanel.revalidate();
        });

        //Need Action Listener for pull down box 2

        //Action Listener for Add Exercise Button
        addExerciseButton.addActionListener(e -> {
            try {
                controller.addToPlan(model.getSelectedEx());
            }catch(NullPointerException ex){
                System.out.println("No exercise selected");
            }
        });
    }

    /**Update panels contained within application window
     * @param model Data model used by application
     */
    @Override
    public void update(WorkoutModel model) {
        //Update Add Exercises panel
        ExercisesPanel.removeAll();
        for(Exercise exercise: model.getFirstPaneList()){
            String calString = "Cal / Min: " + exercise.getCalories();
            BoxFillerRatio filler = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), calString).getPanel(),
                    BoxLayout.Y_AXIS);

            //Mouse Listener that updates Detail Panel with exercise clicked in Add Exercise Panel
            filler.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    BoxFillerRatio temp = new BoxFillerRatio(3,4,
                            new ExerciseCard(exercise.getName(),exercise.getType(), calString).getPanel(),
                            BoxLayout.Y_AXIS);
                    DetailPanel.removeAll();
                    DetailPanel.add(temp);
                    mainPanel.revalidate();
                    controller.setSelected(exercise);
                }
            });
            ExercisesPanel.add(filler);
        }
        mainPanel.revalidate();

        //Update Tab One Current Plan panel
        currentPlanA.removeAll();
        for(Exercise exercise: model.getCurrentPlan()){
            BoxFillerRatio newbox = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            currentPlanA.add(newbox);
        }
        mainPanel.revalidate();

        //Update Tab Two Current Plan panel
        currentPlanB.removeAll();
        for(Exercise exercise: model.getCurrentPlan()){
            BoxFillerRatio newbox = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            currentPlanB.add(newbox);
        }
        mainPanel.revalidate();
    }

    /**Main method to set up application window*/
    public static void main(String[] args) {
        frame.setPreferredSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
        frame.setLocation(new Point((int) size.getWidth() / 4, (int) size.getHeight() / 4));
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
