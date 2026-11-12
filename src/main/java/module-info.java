module com.mycompany.escuelaf.x {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.j;

    opens edu.utj.dsm.poo.escuela to javafx.fxml;
    opens edu.utj.dsm.poo.escuela.controlador to javafx.fxml;
    exports edu.utj.dsm.poo.escuela;
}