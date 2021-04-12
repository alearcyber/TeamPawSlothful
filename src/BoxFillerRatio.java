import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoxFillerRatio extends Box.Filler {

    private int xRatio;
    private int yRatio;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

    /**Parameterized Constructor
     * @param xRatio Width of the panel to add
     * @param yRatio Height of the the panel to add
     * @param content Content contained in the panel
     * @param axis NOT USED
     */
    public BoxFillerRatio(int xRatio, int yRatio,Container content, int axis){
        super(new Dimension(100,0),new Dimension(10000,0),new Dimension(100000,1000000));
        this.xRatio=xRatio;
        this.yRatio=yRatio;
        setLayout(new BoxLayout(this,axis));
        if(axis==0){
            add(Box.createHorizontalGlue());
            add(content);
            add(Box.createHorizontalGlue());
        }
        else{
            add(Box.createVerticalGlue());
            add(content);
            add(Box.createVerticalGlue());
        }
        setBorder(BorderFactory.createMatteBorder(1, 1, 2, 4, Color.DARK_GRAY));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                fixRatio(content);
            }
        });
    }

    /**Fix panel size when window is resized*/
    public void fixRatio(Container content){
        Dimension newSize= new Dimension(getHeight() * xRatio/yRatio,0);
        setMaximumSize(new Dimension(getHeight() * xRatio/yRatio,100000));
        setPreferredSize(newSize);

        for(Component c : content.getComponents()){
            c.setFont(new Font("Dialog", Font.PLAIN, (int) (content.getSize().getWidth()) / 8));
        }

        revalidate();
    }
}