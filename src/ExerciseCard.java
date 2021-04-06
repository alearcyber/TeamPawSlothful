import javax.swing.*;

public class ExerciseCard {
    private JPanel panel1;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel indexLabel;

    public ExerciseCard(String name,String type,Integer index, String calories){
        nameLabel.setText(name);
        typeLabel.setText(type);
        //indexLabel.setText(index.toString());
        indexLabel.setText(calories);
    }
    public JPanel getPanel(){
        return panel1;
    }
}
