package xshape;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.awt.geom.Point2D;

public class BorderShapeFx extends Border {
    Circle _circle = null;
    Group _grp = null;
    public BorderShapeFx(Shape _shape, Group grp) {
        super(_shape);
        _circle = new Circle();
        _grp = grp;
        grp.getChildren().add(_circle);
    }

    @Override
    public BorderShapeFx clone() {
        BorderShapeFx _clone = (BorderShapeFx)super.clone();
        _circle = new Circle();
        _grp.getChildren().add(_circle);
        return _clone;
    }

    @Override
    public void draw(Object parameter) {
        Point2D p = super.position();
        Point2D s = super.size();
        _circle.setCenterX(p.getX());
        _circle.setCenterY(p.getY());
        _circle.setRadius(Math.max(0.5 * s.getX() * Math.sqrt(2), 
        0.5 * s.getY() * Math.sqrt(2)));
        _circle.setFill(Color.TRANSPARENT);
        _circle.setStrokeWidth(1);
        _circle.setStroke(Color.BLACK);
        super.draw(parameter);
    }
}
