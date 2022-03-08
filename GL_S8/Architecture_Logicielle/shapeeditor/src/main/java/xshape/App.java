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
        Shape shape = new RectangleFx(225,225,50,50, root);
        Shape shape2 = shape.clone();
        shape = new BorderShapeFx(shape, root); 
        shape = new BorderShapeFx(shape, root);
        shape.translate(new Point2D.Double(150,50));


        //rec.rotate(20);
        shape2.draw();
        shape.draw();
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

}
