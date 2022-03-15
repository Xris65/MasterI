package xshape;

import java.awt.geom.Point2D;
import java.util.Iterator;

public interface Shape extends Iterable {
	void draw(Object parameter);
	Point2D size();
	Shape size(Point2D vec);
	Point2D position();
	Shape position(Point2D position);
	Shape translate(Point2D vec);
	Shape rotate(double rad);
	double rotation();
	Shape clone();
}
