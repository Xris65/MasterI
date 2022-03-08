package xshape;

import java.awt.geom.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleFx extends xshape.Circle{

	private Point2D _pos;
	private Point2D _scale;
	private Circle _shape = new Circle();
	
	@Override
	public Point2D size() {
		return (Point2D)_scale.clone();
	}

	@Override
	public Shape size(Point2D vec) {
		_scale = (Point2D)vec.clone();
		return this;
	}

	public CircleFx(double posX, double posY, double height, double width, Group grp) {
		_pos = new Point2D.Double(posX, posY);
		_scale = new Point2D.Double(width/2, height/2);
		grp.getChildren().add(_shape);
	}

	@Override
	public void draw() {
		_shape.setX(_pos.getX()-_scale.getX());
		_shape.setY(_pos.getY()-_scale.getY());
		_shape.setWidth(_scale.getX()*2);
		_shape.setHeight(_scale.getY()*2);
		_shape.setFill(Color.BLUE);
	}

	@Override
	public Point2D position() {
		return (Point2D)_pos.clone();
	}

	@Override
	public Shape position(Point2D position) {
		_pos = (Point2D)position.clone();
		return this;
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

	@Override
	public Shape translate(Point2D vec) {
		_pos.setLocation(_pos.getX()+vec.getX(), _pos.getY()+vec.getY());	
		return this;
	}
	@Override
	public Shape clone() {
		Shape clone = null;
		clone = (Shape) super.clone();
		return clone;
	}

}
