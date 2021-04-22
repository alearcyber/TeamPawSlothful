import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;

public class ExerciseSettings {
    private JPanel mainPanel;
    public JTextField numberOfRepsField;
    public JTextField exerciseTimeField;
    public JTextField caloriesBurntField;
    private JPanel fieldPanel;


    private Document original;
    private static int mult = 1; //its just the calories

    //static field representations of the stats and whatnot
    private static String reps;
    private static String cals;
    private static String time;

    public ExerciseSettings(int reps) {

        for(Component c : fieldPanel.getComponents()){
            if(c instanceof JTextField){
                c.setEnabled(false);
            }
        }

        constructListeners();

        final int y = (reps * mult / 5);
        final String toCals = String.valueOf(y);
        caloriesBurntField.setText(toCals);
        final int x = (reps / 5);
        final String toTime = String.valueOf(x);
        exerciseTimeField.setText(toTime);

    }

    /**Constructs Listeners for Exercise Settings panel*/
    private void constructListeners() {

        //Resizes text on Exercise Settings panel
        for (Component c : mainPanel.getComponents()) {
            c.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    resizeText(c);
                }
            });
        }

        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel(e);
            }
        };

        for(Component c : fieldPanel.getComponents()){
            //Handles when the text field loses focus
            if(c instanceof JTextField){
                c.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        c.setEnabled(false);
                    }
                });

                //Handles when the text field is clicked
                c.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        c.requestFocus();
                        c.setEnabled(true);
                    }
                });

                //Handles when something other than a number is typed
                c.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if(!Character.isDigit(e.getKeyChar()) || ((JTextField) c).getText().length() >= 6) {
                            e.consume();
                        }
                    }
                });
            }
        }

        numberOfRepsField.getDocument().addDocumentListener(docListener);
        caloriesBurntField.getDocument().addDocumentListener(docListener);
        exerciseTimeField.getDocument().addDocumentListener(docListener);
    }

    /**Resizes text of Exercise Settings panel*/
    private void resizeText(Component component){

        if(component instanceof Container){
            for(Component c : (((Container) component).getComponents())){
                c.setFont(new Font("Dialog", Font.PLAIN, (int) (mainPanel.getWidth() / 70.8)));
            }
        }
    }

    private void updateLabel(DocumentEvent e) {

        if (original == null) {
            original = e.getDocument();
            String text = "";
            try {
                text = original.getText(0, original.getLength());
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
            if (!text.isEmpty()) {
                final int p = Integer.parseInt(text);

                if (original.equals(numberOfRepsField.getDocument())) {
                    final int y = (p * mult / 5);
                    final String toCals = String.valueOf(y);
                    caloriesBurntField.setText(toCals);
                    final int x = (p / 5);
                    final String toTime = String.valueOf(x);
                    exerciseTimeField.setText(toTime);
                    reps = p + " ";
                    cals = toCals;
                    time = toTime;

                }else if(original.equals(caloriesBurntField.getDocument())) {
                    final int x = ((p * 5) / mult);
                    final String toReps = String.valueOf(x);
                    numberOfRepsField.setText(toReps);
                    final int y = (p / mult);
                    final String toTime = String.valueOf(y);
                    exerciseTimeField.setText(toTime);
                    reps = toReps;
                    cals = p + " ";
                    time = toTime;

                } else {
                    final int x = (p * 5);
                    final String toReps = String.valueOf(x);
                    numberOfRepsField.setText(toReps);
                    final int y = (p * mult);
                    final String toCals = String.valueOf(y);
                    caloriesBurntField.setText(toCals);
                    reps = toReps;
                    cals = toCals;
                    time = p + " ";
                }

            }
            original = null;
        }
    }

    public static void setMult(int mult) {
        ExerciseSettings.mult = mult;
    }

    /**Gets Exercise Settings Panel
     * @return Exercise Settings Panel
     */
    public JPanel getMainPanel() { return mainPanel; }

    public static String getReps(){
        return reps;
    }

    public static String getCal(){
        return cals;
    }

    public static String getMin(){
        return time;
    }


}
