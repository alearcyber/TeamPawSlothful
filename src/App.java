import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class App implements Observer<WorkoutModel>{

    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

    private static final JFrame frame = new JFrame("My Exercise Planner");

    private JTabbedPane tabbedPane1;
    private JScrollPane ExercisePanelScroll;
    private JScrollPane CurrentPlanAScroll;
    private JScrollPane CurrentPlanBScroll;

    private JPanel mainPanel;
    private JPanel ExercisesPanel;
    private JPanel DetailPanel;
    private JPanel currentPlanA;
    private JPanel currentPlanB;
    private JPanel settingsPanel;
    private JPanel metricsPanel;

    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private JComboBox<String> importDropdown;

    private JButton addExerciseButton;
    private JButton updateExerciseButton;
    private JButton importPlanButton;
    private JButton exportPlanButton;

    private JTextField workoutNameField;

    private final WorkoutModel model;
    private final WorkoutController controller;

    private BoxFillerRatio temp;

    /**Default Constructor*/
    public App(){
        model = new WorkoutModel(this);
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
        workoutNameField.setForeground(Color.lightGray);

        //Methods that construct various things
        constructListeners();

        temp = new BoxFillerRatio(3,4,                                                                     //put an empty card in exercise settings
                new ExerciseCard("Name", "Type", "Cal / Min: ").getPanel(),
                BoxLayout.Y_AXIS);

        settingsPanel.add(temp);

        ExerciseSettings exerciseSettings = new ExerciseSettings();
        settingsPanel.add(exerciseSettings.getMainPanel());
    }

    /**Update panels contained within application window
     * @param model Data model used by application
     */
    @Override
    public void update(WorkoutModel model) {

        BoxFillerRatio filler;
        int i = 0;

        //Update Add Exercises panel
        ExercisesPanel.removeAll();
        for(Exercise exercise : model.getFirstPaneList()){
            filler = new BoxFillerRatio(3,4,
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
                    controller.setSelectedExercise(Objects.requireNonNull(exercise));
                    if(me.getButton() == MouseEvent.BUTTON3) addExerciseButton.doClick();
                }
            });
            if(i > 0)ExercisesPanel.add(Box.createRigidArea(new Dimension(3, 0)));
            ExercisesPanel.add(filler);
            i++;
        }

        //Update Tab One Current Plan panel
        currentPlanA.removeAll();
        i = 0;
        for(Exercise exercise: model.getCurrentPlanA()){
            filler = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            filler.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    if(me.getButton() != MouseEvent.BUTTON3) {
                        temp = new BoxFillerRatio(3, 4,
                                new ExerciseCard(exercise.getName(), exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                                BoxLayout.Y_AXIS);
                        DetailPanel.removeAll();
                        DetailPanel.add(temp);
                    }
                    else{
                        controller.removeExercises(exercise);
                        controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
                    }
                }
            });

            if(i > 0) currentPlanA.add(Box.createRigidArea(new Dimension(3, 0)));
            currentPlanA.add(filler);
            i++;
        }

        //Update Tab Two Current Plan panel
        currentPlanB.removeAll();
        for(Exercise exercise: model.getCurrentPlanB()){
            filler = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            if(i > 0) currentPlanB.add(Box.createRigidArea(new Dimension(3, 0)));
            filler.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    temp = new BoxFillerRatio(3,4,
                            new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                            BoxLayout.Y_AXIS);
                    settingsPanel.removeAll();
                    settingsPanel.add(temp);

                    settingsPanel.add(new ExerciseSettings().getMainPanel());
                    controller.setSelectedExercise(exercise);
                }
            });
            currentPlanB.add(filler);
        }


        //*****************************************
        //additional updates to be performed here
        //*****************************************
        updateImportDropdown();
        resizeText();
        //mainPanel.revalidate();
    }

    /**
     * UPDATE
     * adds the names of the workouts in the database to the
     * dropdown
     */
    public void updateImportDropdown(){
        importDropdown.removeAllItems();
        for(Workout workout: DBmanager.getWorkouts()){
            importDropdown.addItem(workout.getName());
        }
    }

    /**Resizes all text displayed on the GUI*/
    private void resizeText(){

        tabbedPane1.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getHeight() / 41.75)));

        for(Component c : tabbedPane1.getComponents()){
            if(c instanceof Container){
                for (Component d : (((Container) c).getComponents())) {
                    if(d instanceof Container){
                        for (Component e : (((Container) d).getComponents())) {
                            e.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getWidth() / 70.8)));
                        }
                    }
                    d.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getWidth() / 70.8)));
                }
            }
        }

        for(Component c : metricsPanel.getComponents()){
            if(c instanceof Container){
                for(Component d : (((Container) c).getComponents())){
                    d.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getWidth() / 70.8)));
                }
            }
        }
    }

    /**Constructs Listeners for Application*/
    private void constructListeners(){

        //Action Listener for text resizing
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { resizeText(); }
        });

        //Action Listener for changing tabs
        tabbedPane1.addChangeListener(e -> comboBox2.setSelectedIndex(0));

        //Action Listener for pull down box
        comboBox1.addActionListener(e -> controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(),
                Objects.requireNonNull(comboBox2.getSelectedItem()).toString()));

        //Action Listener for pull down box 2
        comboBox2.addActionListener(e -> controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(),
                Objects.requireNonNull(comboBox2.getSelectedItem()).toString()));

        //Action Listener for Add Exercise Button
        addExerciseButton.addActionListener(e -> {
            try {
                controller.addToPlan(model.getSelectedEx());
                controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
            }catch(NullPointerException ex){
                ex.printStackTrace();
            }
        });

        //Action Listener for Update Exercise button
        updateExerciseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(exerciseSettings.getSetting());
            }
        });

        //Action Listener for Import Plan button
        importPlanButton.addActionListener(e -> controller.importPlan());

        //Action Listener for Export Plan button
        exportPlanButton.addActionListener( e -> {
            int n = 0;
            if(Objects.requireNonNull(importDropdown.getSelectedItem()).toString().equals(workoutNameField.getText())){
                Object[] options = {"Overwrite",
                        "Cancel"};
                n = JOptionPane.showOptionDialog(frame,
                        "Error:" + "\n" +"New Workout name is the same as one in the database",
                        "Error Adding Workout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);
            }
            if(n == 0){
                //controller.exportPlan(Objects.requireNonNull(workoutNameField.getText()));
                System.out.println("This is where database overwriting would be, IF I HAD IT");                             //consider adding overwrite
            }
        });

        //Action Listener for clicking into Workout Name field
        workoutNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                workoutNameField.setText("");
                workoutNameField.setForeground(Color.black);
            }
        });

        //Action Listener for clearing Workout Name field
        workoutNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if((e.getKeyCode() == KeyEvent.VK_BACK_SPACE)){
                    if(workoutNameField.getText().equals("")){
                        workoutNameField.setForeground(Color.lightGray);
                        workoutNameField.setText("Enter name of workout to export ");
                        mainPanel.requestFocus();
                    }
                }
            }
        });

        //Action Listener for Workout import button
        importDropdown.addItemListener( e->{
            if (e.getStateChange() == ItemEvent.SELECTED && importDropdown.hasFocus()) {
                String item = (String) e.getItem();
                for(Workout workout: DBmanager.getWorkouts()){
                    if(workout.getName().equals(item)){
                        controller.setSelectedWorkout(workout);
                    }
                }
            }
        });
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
