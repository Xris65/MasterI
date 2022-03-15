package xshape;
import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

class GUIHelper {
    public static void showOnFrame(JComponent component, String frameName) {
        JFrame frame = new JFrame(frameName);
        WindowAdapter wa = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(wa);
        frame.getContentPane().add(component);
        frame.pack();
        frame.setVisible(true);
    }
}

class JCanvas extends JPanel {
    Shape _shape = null;
    public JCanvas(Shape s) {
        _shape = s;
    }
    public void paint(Graphics g) {
        super.paint(g);
        _shape.draw(g);
    }
}

public class AppAwt {

    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactoryAwt();
        Shape shape = factory.createRectangle(100,100, 50, 50);
        shape = factory.createBorder(shape);
        shape = factory.createBorder(shape);
        
        JCanvas jc = new JCanvas(shape);
        jc.setBackground(Color.WHITE);
        jc.setPreferredSize(new Dimension(500, 500));
        GUIHelper.showOnFrame(jc, "test"); 
    }

}
