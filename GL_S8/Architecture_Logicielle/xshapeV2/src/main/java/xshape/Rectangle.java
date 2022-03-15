package xshape;

import java.awt.geom.Point2D;

public abstract class Rectangle implements Shape, Cloneable {
    private Point2D _pos = new Point2D.Double(0, 0);
    private Point2D _size = new Point2D.Double(1, 1);

    @Override
    public Rectangle clone() {
		Rectangle newShape = null;
		try {
			newShape = (Rectangle)super.clone();
		}
		catch (CloneNotSupportedException e) {
		}
		return newShape;
	}

    @Override
    public Point2D size() {
        return (Point2D) _size.clone();
    }

    @Override
    public Shape size(Point2D vec) {
        _size = (Point2D) vec.clone();
        return this;
    }

    @Override
    public Point2D position() {
        return (Point2D) _pos.clone();
    }

    @Override
    public Shape position(Point2D position) {
        _pos = (Point2D) position.clone();
        return this;
    }

    @Override
    public Shape translate(Point2D vec) {
        _pos.setLocation(_pos.getX() + vec.getX(),
                _pos.getY() + vec.getY());
        return this;
    }
    
    @Override
	public Shape rotate(double rad) {
		return this;
	}

	@Override
	public double rotation() {
		return 0;
	}

}
