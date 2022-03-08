package com.projet;

import java.awt.geom.Point2D;
public interface Shape extends Cloneable {
    Point2D position();
    Shape position(Point2D position);
    Shape translate(Point2D delta);
    Shape clone();
    double layer();
    Shape layer(double layer);
    Shape rotate(double angle);
    Point2D center();
    Shape center(Point2D center);
    Shape scale(Point2D point);
    void draw();
    boolean isIn(Point2D point);
}

    