import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

/**Class that implements view design pattern*/
public class App implements Observer<WorkoutModel>{

    /**Size of the user's screen*/
    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

    /**Main Frame of application*/
    private static final JFrame frame = new JFrame("My Exercise Planner");

    private JTabbedPane tabbedPane1;
    private JScrollPane ExercisePanelScroll;
    private JScrollPane CurrentPlanAScroll;
    private JScrollPane CurrentPlanBScroll;

    private JPanel mainPanel;
    private JPanel exercisesPanel;
    private JPanel detailPanel;
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

    private JTextArea detailTextArea;

    private final WorkoutModel model;
    private final WorkoutController controller;

    private BoxFillerRatio temp;
    private BoxFillerRatio filler;

    /**Default Constructor*/
    public App(){
        model = new WorkoutModel(this);
        controller = new WorkoutController(model);
        controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());

        exercisesPanel.setLayout(new BoxLayout(exercisesPanel, BoxLayout.X_AXIS));
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
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
        updateImportDropdown();

        //Adds an empty exercise card to details panel
        temp = new BoxFillerRatio(3,4,
                new ExerciseCard("Name", "Type", "Cal / Min: ").getPanel(),
                BoxLayout.Y_AXIS);

        detailPanel.add(temp);

        //Adds space between exercise card and detail pane
        detailPanel.add(Box.createRigidArea(new Dimension(3, 0)));

        //Adds an empty text area to details panel
        detailTextArea = new JTextArea();
        detailTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        detailTextArea.setEditable(false);
        detailPanel.add(detailTextArea);

        //Adds an empty exerciseCard to settings panel
        temp = new BoxFillerRatio(3,4,
                new ExerciseCard("Name", "Type", "Cal / Min: ").getPanel(),
                BoxLayout.Y_AXIS);

        settingsPanel.add(temp);

        //Adds an empty exerciseSettings to settings panel
        ExerciseSettings exerciseSettings = new ExerciseSettings(0);
        settingsPanel.add(exerciseSettings.getMainPanel());
    }

    /**Update panels contained within application window
     * @param model Data model used by application
     */
    @Override
    public void update(WorkoutModel model) {
        updateAddExercisesPanel();
        updateCurrentPlanA();
        updateCurrentPlanB();
        resizeText();
    }

    /**Updates exercises in Add Exercises panel*/
    public void updateAddExercisesPanel(){
        int i = 0;

        //Update Add Exercises panel
        exercisesPanel.removeAll();
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
                    detailPanel.removeAll();
                    detailPanel.add(temp);

                    detailPanel.add(Box.createRigidArea(new Dimension(3, 0)));

                    detailTextArea = new JTextArea();


                    for(String s : exercise.getDetails()){
                        detailTextArea.append(s + "\n");
                    }

                    detailTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
                    detailTextArea.setEditable(false);
                    detailTextArea.setFocusable(false);
                    detailPanel.add(detailTextArea);

                    addExerciseButton.setEnabled(true);
                    controller.setSelectedExercise(Objects.requireNonNull(exercise));
                    if(me.getButton() == MouseEvent.BUTTON3) addExerciseButton.doClick();
                }
            });
            if(i > 0) exercisesPanel.add(Box.createRigidArea(new Dimension(3, 0)));
            exercisesPanel.add(filler);
            i++;
        }
    }

    /**Updates exercises in Tab One, Current Plan panel*/
    public void updateCurrentPlanA(){
        int i = 0;

        //Update Tab One Current Plan panel
        currentPlanA.removeAll();
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
                        detailPanel.removeAll();
                        detailPanel.add(temp);

                        detailPanel.add(Box.createRigidArea(new Dimension(3, 0)));

                        detailTextArea = new JTextArea();

                        for(String s : exercise.getDetails()){
                            detailTextArea.append(s + "\n");
                        }

                        detailTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
                        detailTextArea.setEditable(false);
                        detailTextArea.setFocusable(false);
                        detailPanel.add(detailTextArea);

                        addExerciseButton.setEnabled(true);
                        controller.setSelectedExercise(Objects.requireNonNull(exercise));
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
    }

    /**Updates exercises in Tab Two, Current Plan panel*/
    public void updateCurrentPlanB(){
        int i = 0;

        //Update Tab Two Current Plan panel
        currentPlanB.removeAll();
        for(Exercise exercise: model.getCurrentPlanB()){
            filler = new BoxFillerRatio(3,4,
                    new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                    BoxLayout.Y_AXIS);

            filler.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    temp = new BoxFillerRatio(3,4,
                            new ExerciseCard(exercise.getName(),exercise.getType(), "Cal / Min: " + exercise.getCalories()).getPanel(),
                            BoxLayout.Y_AXIS);
                    settingsPanel.removeAll();
                    settingsPanel.add(temp);


                    controller.setSelectedExercise(exercise);

                    try{
                        settingsPanel.add(new ExerciseSettings(model.getSelectedWorkout().getReps().get(exercise.getName())).getMainPanel());
                    }catch(NullPointerException e){
                        settingsPanel.add(new ExerciseSettings(0).getMainPanel());
                    }


                    //NOW WE LOAD UP THOSE FIELDS BASED ON THAT INFO
                }
            });
            if(i > 0) currentPlanB.add(Box.createRigidArea(new Dimension(3, 0)));
            currentPlanB.add(filler);
            i++;
        }
    }

    /**Updates workout names to Import Dropdown menu*/
    public void updateImportDropdown(){
        importDropdown.removeAllItems();
        for(Workout workout: DBmanager.getWorkouts()){
            importDropdown.addItem(workout.getName());
        }
    }

    /**Resizes all text displayed on the GUI*/
    private void resizeText(){

        tabbedPane1.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getHeight() / 41.75)));

        detailTextArea = new JTextArea("");
        detailTextArea.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getHeight() / 41.75)));

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
                controller.addToPlan(model.getSelectedExercise());
                controller.updateWorkouts(Objects.requireNonNull(comboBox1.getSelectedItem()).toString(), Objects.requireNonNull(comboBox2.getSelectedItem()).toString());
            }catch(NullPointerException ex){
                ex.printStackTrace();
            }
        });

        //Action Listener for Update Exercise button
        updateExerciseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int reps = Integer.parseInt(ExerciseSettings.getReps().strip());
                controller.setReps(reps);
            }
        });

        //Action Listener for Import Plan button
        importPlanButton.addActionListener(e -> controller.importPlan());

        //Action Listener for Export Plan button
        exportPlanButton.addActionListener( e -> {
            int n;
            boolean overwriteFlag = false;

            for(int i = 0; i < importDropdown.getItemCount(); i++){
                if(Objects.requireNonNull(importDropdown.getItemAt(i)).equals(workoutNameField.getText())) {
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
                    if(n == 0) overwriteFlag = true;
                    else overwriteFlag = false;
                }
                else overwriteFlag = true;
            }

            if(overwriteFlag == true){
                DBmanager.overwriteDatabase(workoutNameField.getText());
                controller.exportPlan(Objects.requireNonNull(workoutNameField.getText()));
                controller.setSelectedWorkout(DBmanager.getWorkouts().get(0));
                updateImportDropdown();
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
        controller.setSelectedWorkout(DBmanager.getWorkouts().get(0));
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
