module com.projet.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.projet to javafx.fxml;
    exports com.projet;
    exports com.projet.Shapes;
    opens com.projet.Shapes to javafx.fxml;
}