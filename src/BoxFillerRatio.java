import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoxFillerRatio extends Box.Filler {
    private int xRatio,yRatio;

    public BoxFillerRatio(int xRatio, int yRatio,Container content, int axis){
        super(new Dimension(100,0),new Dimension(100,0),new Dimension(100,1000000));
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
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                fixRatio();
            }
        });
    }

    public void fixRatio(){
        Dimension newSize= new Dimension(getHeight()*xRatio/yRatio,100000);
        setMaximumSize(newSize);
        revalidate();
    }
}
