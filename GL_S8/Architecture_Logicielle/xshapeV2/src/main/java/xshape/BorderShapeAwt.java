package xshape;

import java.awt.geom.Point2D;
import java.awt.*;

public class BorderShapeAwt extends Border {
    public BorderShapeAwt(Shape _shape) {
        super(_shape);
    }

    @Override
    public void draw(Object parameter) {
        Graphics g = (Graphics)parameter;

        Point2D p = super.position();
        Point2D s = super.size();
        Color c = g.getColor();
        g.setColor(java.awt.Color.BLACK);
        double r = (Math.max(s.getX() * Math.sqrt(2), s.getY() * Math.sqrt(2)));

        g.drawOval((int) (p.getX() - r/2),
                (int) (p.getY() - r/2),
                (int) (r),
                (int) (r));
        g.setColor(c);
        super.draw(parameter);
    }


}
