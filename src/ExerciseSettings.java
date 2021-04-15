import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ExerciseSettings {
    private JPanel MainPanel;
    public JTextField numberOfRepsField;
    public JTextField exerciseTimeField;
    public JTextField caloriesBurntField;

    private static int mult = 1;
    private static String exName = "";

    public static void setMult(int mult) {
        ExerciseSettings.mult = mult;
    }

    public static void setExName(String exName) {
        ExerciseSettings.exName = exName;
    }

    public ExerciseSettings() {


        DocumentListener docListener = new DocumentListener() {
            private Document original;
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
                        final int rate = mult;

                        if (original.equals(numberOfRepsField.getDocument())) {
                            final int y = (p * rate / 5);
                            final String toCals = String.valueOf(y);
                            caloriesBurntField.setText(toCals);
                            final int x = (p / 5);
                            final String toTime = String.valueOf(x);
                            exerciseTimeField.setText(toTime);

                        }else if(original.equals(caloriesBurntField.getDocument())) {
                            final int x = ((p * 5) / rate);
                            final String toReps = String.valueOf(x);
                            numberOfRepsField.setText(toReps);
                            final int y = (p / rate);
                            final String toTime = String.valueOf(y);
                            exerciseTimeField.setText(toTime);

                        } else {        //if(original.equals(exerciseTimeField.getDocument()))
                            final int x = (p * 5);
                            final String toReps = String.valueOf(x);
                            numberOfRepsField.setText(toReps);
                            final int y = (p * rate);
                            final String toCals = String.valueOf(y);
                            caloriesBurntField.setText(toCals);

                        }

                    }



                    original = null;
                }
            }
        };
        numberOfRepsField.getDocument().addDocumentListener(docListener);
        caloriesBurntField.getDocument().addDocumentListener(docListener);
        exerciseTimeField.getDocument().addDocumentListener(docListener);
        numberOfRepsField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        caloriesBurntField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        exerciseTimeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }

    public JPanel getMainPanel() {return MainPanel;}
}
