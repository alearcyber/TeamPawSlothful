import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**Class that maintains ratio of exercise cards*/
public class BoxFillerRatio extends Box.Filler {

    /**Width of an exercise panel*/
    private final int xRatio;

    /**Height of an exercise panel*/
    private final int yRatio;

    /**Parameterized Constructor
     * @param xRatio Width of the panel to add
     * @param yRatio Height of the the panel to add
     * @param content Content contained in the panel
     * @param axis Axis to align exercise panel text
     */
    public BoxFillerRatio(int xRatio, int yRatio,Container content, int axis){
        super(new Dimension(100,0),new Dimension(10000,0),new Dimension(100000,1000000));
        this.xRatio=xRatio;
        this.yRatio=yRatio;

        setLayout(new BoxLayout(this,axis));
        setBorder(BorderFactory.createMatteBorder(1, 1, 2, 4, Color.DARK_GRAY));

        add(Box.createHorizontalGlue());
        add(content);
        add(Box.createHorizontalGlue());


        //Listener for window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                fixRatio(content);
            }
        });
    }

    /**Fix panel size when window is resized
     * @param content Content of the exercise panel to resize
     */
    public void fixRatio(Container content){
        Dimension newSize= new Dimension(getHeight() * xRatio/yRatio,0);
        setMaximumSize(new Dimension(getHeight() * xRatio/yRatio,100000));
        setPreferredSize(newSize);

        //Resizes JLabel text in exercise panel
        for(Component c : content.getComponents()){
            c.setFont(new Font("Dialog", Font.PLAIN, (int) (content.getSize().getWidth()) / 8));
        }
    }
}