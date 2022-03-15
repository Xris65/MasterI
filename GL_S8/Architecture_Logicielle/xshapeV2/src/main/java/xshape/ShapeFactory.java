package xshape;

public interface ShapeFactory {
    Rectangle createRectangle(double posX, double posY, double height, double width);
    Shape createBorder(Shape s);
}

