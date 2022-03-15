package xshape;

import java.awt.geom.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class RectangleFx extends Rectangle {

	javafx.scene.shape.Rectangle _shape = new javafx.scene.shape.Rectangle();
    Group _grp = null;

	public RectangleFx(double posX, double posY, double height, double width, Group grp) {
		position(new Point2D.Double(posX, posY));
		size(new Point2D.Double(width, height));
		_grp = grp;
		_grp.getChildren().add(_shape);
	}

	@Override
    public RectangleFx clone() {
        RectangleFx _clone = (RectangleFx)super.clone();
        _shape = new javafx.scene.shape.Rectangle();
		_grp.getChildren().add(_shape);
        return _clone;
    }

	@Override
	public void draw(Object parameter) {
		Point2D pos = position();
		Point2D size = size();
		_shape.setX(pos.getX()- size.getX()/2);
		_shape.setY(pos.getY()- size.getY()/2);
		_shape.setWidth(size.getX());
		_shape.setHeight(size.getY());
		_shape.setFill(Color.BLUE);
	}

	@Override
	public Shape rotate(double rad) {
		_shape.setRotate(rad);
		return this;
	}

	@Override
	public double rotation() {
		return _shape.getRotate();
	}

}
