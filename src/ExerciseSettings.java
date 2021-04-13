import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ExerciseSettings {
    private JPanel MainPanel;
    private JTextField numberOfRepsField;
    private JTextField caloriesBurntField;


    public ExerciseSettings() {
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
    }

    public JPanel getMainPanel() {return MainPanel;}
}
