import java.awt.geom.Point2D;

public class Rectangle implements Shape {
    private Point2D center;
    private Point2D position;
    double layer;
    @Override
    public Point2D position() {
        return position;
    }

    @Override
    public Shape position(Point2D position) {
        this.position = position;
        return this;
    }

    @Override
    public Shape translate(Point2D delta) {
        position.setLocation(position.getX() + delta.getX(), position.getY() + delta.getY());
        return this;
    }

    @Override
    public Shape clone() {
        return this;
    }

    @Override
    public double layer() {
        return layer;
    }

    @Override
    public Shape layer(double layer) {
        this.layer = layer;
        return this;
    }

    @Override
    public Shape rotate(double angle) {
        return this;
    }

    @Override
    public Point2D center() {
        return center;
    }

    @Override
    public Shape center(Point2D center) {
        this.center = center;
        return this;
    }

    @Override
    public Shape scale(Point2D point) {
        return this;
    }

    @Override
    public void draw() {
        
    }

    @Override
    public boolean isIn(Point2D point) {
        return false;
    }

    
}
