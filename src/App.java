import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class App implements Observer<WorkoutModel>{

    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private static final JFrame frame = new JFrame("My Exercise Planner");
    private JTabbedPane tabbedPane1;
    private JScrollPane ExercisePanelScroll;
    private JPanel mainPanel;
    private JPanel ExercisesPanel;
    private JPanel DetailPanel;
    private JPanel currentPlanA;
    private JPanel currentPlanB;

    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;

    private JButton addExerciseButton;
    private JScrollPane DetailPanelScroll;
    private JScrollPane CurrentPlanAScroll;
    private JScrollPane CurrentPlanBScroll;

    private JPanel settingsPanel;
    private JPanel metricsPanel;
    private JButton updateExerciseButton;
    private JButton importPlanButton;
    private JButton exportPlanButton;
    private JLabel bottomLabel;
    private JTextField name;
    private JComboBox<String> importDropdown;

    private final WorkoutModel model;
    private final WorkoutController controller;

    /**Default Constructor*/
    public App(){
        model = new WorkoutModel();
        model.addObserver(this);
        controller = new WorkoutController(model);
        controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());

        ExercisesPanel.setLayout(new BoxLayout(ExercisesPanel, BoxLayout.X_AXIS));
        DetailPanel.setLayout(new BoxLayout(DetailPanel, BoxLayout.X_AXIS));
        currentPlanA.setLayout(new BoxLayout(currentPlanA, BoxLayout.X_AXIS));
        currentPlanB.setLayout(new BoxLayout(currentPlanB, BoxLayout.X_AXIS));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.X_AXIS));

        ExercisePanelScroll.getHorizontalScrollBar().setUnitIncrement(16);
        CurrentPlanAScroll.getHorizontalScrollBar().setUnitIncrement(16);
        CurrentPlanBScroll.getHorizontalScrollBar().setUnitIncrement(16);

        addExerciseButton.setEnabled(false);

        //Action Listener for pull down box
        comboBox1.addActionListener(e -> {
            controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
        });

        //Action Listener for pull down box 2
        comboBox2.addActionListener(e -> {
            controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
        });

        //Action Listener for Add Exercise Button
        addExerciseButton.addActionListener(e -> {
            try {
                controller.addToPlan(model.getSelectedEx());
                controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
            }catch(NullPointerException ex){
                ex.printStackTrace();
            }
        });

        //*************************************
        // methods that construct various things
        //*************************************
        constructNameField();
        constructExportButton();
        constructImportButton();
        constructImportDropdown();
    }

    /**Update panels contained within application window
     * @param model Data model used by application
     */
    @Override
    public void update(WorkoutModel model) {
        //Update Add Exercises panel
        ExercisesPanel.removeAll();
        int i = 0;
        for(Exercise exercise : model.getFirstPaneList()){
            BoxFillerRatio filler = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            //Mouse Listener that updates Detail Panel with exercise clicked in Add Exercise Panel
            filler.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    BoxFillerRatio temp = new BoxFillerRatio(3,4,
                            new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                            BoxLayout.Y_AXIS);
                    DetailPanel.removeAll();
                    DetailPanel.add(temp);
                    addExerciseButton.setEnabled(true);
                    mainPanel.revalidate();
                    controller.setSelected(Objects.requireNonNull(exercise));
                    if(me.getButton() == MouseEvent.BUTTON3) addExerciseButton.doClick();
                }
            });
            if(i > 0)ExercisesPanel.add(Box.createRigidArea(new Dimension(3, 0)));                          //can be removed per preference
            ExercisesPanel.add(filler);
            i++;
        }

        //Update Tab One Current Plan panel
        currentPlanA.removeAll();
        i = 0;
        for(Exercise exercise: model.getCurrentPlanA()){
            BoxFillerRatio newbox = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            newbox.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    if(me.getButton() != MouseEvent.BUTTON3) {
                        BoxFillerRatio temp = new BoxFillerRatio(3, 4,
                                new ExerciseCard(exercise.getName(), exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                                BoxLayout.Y_AXIS);
                        DetailPanel.removeAll();
                        DetailPanel.add(temp);
                    }
                    else{
                        controller.removeExercises(exercise);                                                                       //last object not being removed
                        controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
                    }
                    mainPanel.revalidate();
                }
            });

            if(i > 0) currentPlanA.add(Box.createRigidArea(new Dimension(3, 0)));                                     //can be removed
            currentPlanA.add(newbox);
            i++;
        }

        //Update Tab Two Current Plan panel
        currentPlanB.removeAll();
        i = 0;
        for(Exercise exercise: model.getCurrentPlanB()){
            BoxFillerRatio newbox = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            if(i > 0) currentPlanB.add(Box.createRigidArea(new Dimension(3, 0)));                                     //can be removed
            String calString = "Cal / Min: " + exercise.getCalories();
            newbox.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    BoxFillerRatio temp = new BoxFillerRatio(3,4,
                            new ExerciseCard(exercise.getName(),exercise.getType(), calString).getPanel(),
                            BoxLayout.Y_AXIS);
                    settingsPanel.removeAll();
                    settingsPanel.add(temp);
                    ExerciseSettings.setMult(exercise.getCalories());
                    ExerciseSettings.setExName(exercise.getName());
                    settingsPanel.add(new ExerciseSettings().getMainPanel());
                    mainPanel.revalidate();
                    controller.setSelected(exercise);
                }
            });
            currentPlanB.add(newbox);
            i++;
        }


        //*****************************************
        //additional updates to be performed here
        //*****************************************
        updateImportDropdown();

        mainPanel.revalidate();
    }

    /**
     * CONSTRUCT
     * creates the functionality for the import dropdown
     * so that when it is used the value is set properly
     */
    public void constructImportDropdown(){

        importDropdown.addItemListener( e->{
            if (e.getStateChange() == ItemEvent.SELECTED && importDropdown.hasFocus()) {
                String item = (String) e.getItem();
                System.out.println("THIS ACTION LISTEN HAS BEEN CALLED");
                String selectedImport = (String) importDropdown.getSelectedItem();
                System.out.println("SELECTED IMPORT " + selectedImport);
                for(Workout workout: DBmanager.getWorkouts()){
                    if(workout.getName().equalsIgnoreCase(item)){
                        System.out.println("SETTING THE SELECTED WORKOUT TO " + workout.getName());
                        controller.setSelectedWorkout(workout);
                    }
                }
            }
        });
    }

    /**
     * adds functionality for the export button.
     * Should be called when the constructor
     */
    public void constructExportButton(){
        exportPlanButton.addActionListener( e -> {
            controller.exportPlan();
        });
    }

    /**
     * adds functionality for the import button.
     * Should be called when the constructor
     */
    public void constructImportButton(){
        importPlanButton.addActionListener(e -> {
            controller.importPlan();
        });
    }

    /**
     * constructs the functionality for
     * naming the Workout
     */
    public void constructNameField(){
        name.addActionListener(e -> {
            controller.setName(e.getActionCommand());
        });
    }

    /**
     * UPDATE
     * adds the names of the workouts in the database to the
     * dropdown
     */
    public void updateImportDropdown(){
        importDropdown.removeAllItems();
        mainPanel.revalidate();
        ArrayList<Workout> workouts = DBmanager.getWorkouts();
        for(Workout workout: workouts){
            importDropdown.addItem(workout.getName());
        }
    }



    /**Main method to set up application window*/
    public static void main(String[] args) {
        frame.setMinimumSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
        frame.setLocation(new Point((int) size.getWidth() / 4, (int) size.getHeight() / 4));
        frame.setContentPane(new App().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
