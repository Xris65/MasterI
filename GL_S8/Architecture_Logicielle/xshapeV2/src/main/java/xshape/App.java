package xshape;

import javafx.application.Application;
import java.awt.geom.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Hello World!");

        ShapeFactory factory = new ShapeFactoryFx(root);
        Shape shape = factory.createRectangle(100,100, 50, 50);
        shape = factory.createBorder(shape);
        shape = factory.createBorder(shape);

        Shape shape2 = shape.clone();
        shape2.translate(new Point2D.Double(50,50));
        shape2.draw(null);
        
        shape.draw(null);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
