package xshape;

import java.awt.geom.Point2D;

public interface Shape extends Cloneable {
	void draw();
	Point2D size();
	Shape size(Point2D vec);
	
	Point2D position();
	Shape position(Point2D position);
	Shape translate(Point2D vec);
	Shape rotate(double rad);
	double rotation();
	Shape clone();
}
