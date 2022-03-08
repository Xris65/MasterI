import java.awt.geom.Point2D;
public interface Shape {
    public Point2D position();
    public Shape position(Point2D position);
    public Shape translate(Point2D delta);
    public Shape clone();
    public double layer();
    public Shape layer(double layer);
    public Shape rotate(double angle);
    public Point2D center();
    public Shape center(Point2D center);
    public Shape scale(Point2D point);
    public void draw();
    public boolean isIn(Point2D point);
}

    