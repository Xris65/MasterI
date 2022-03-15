package xshape;
import java.awt.geom.Point2D;

public abstract class Border extends ShapeDecorator {
    public Border(Shape s) {
        super(s);
    }
    @Override
    public Border clone() {
        return (Border)super.clone();
    }
    @Override
    public Point2D size() {
        Point2D s = super.size();
        double s2 = Math.max(s.getX() * Math.sqrt(2), s.getY() * Math.sqrt(2));
        return new Point2D.Double(s2, s2);
    }

}
