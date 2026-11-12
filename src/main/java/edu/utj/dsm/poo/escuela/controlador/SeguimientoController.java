// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.Seguimiento;
import edu.utj.dsm.poo.escuela.persistencia.SeguimientoDAO;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class SeguimientoController implements Initializable {

    @FXML private TableView<Seguimiento> tabla;

    private final SeguimientoDAO dao = new SeguimientoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        col(0, Seguimiento::getMatricula);
        col(1, Seguimiento::getAlumno);
        col(2, Seguimiento::getMateria);
        col(3, Seguimiento::getPeriodo);
        col(4, Seguimiento::getCalificacion);
        col(5, Seguimiento::getResultado);
        col(6, Seguimiento::getClases);
        col(7, s -> s.getPctAsistencia() == null ? "Sin registros" : s.getPctAsistencia() + " %");
        try {
            tabla.getItems().setAll(dao.listar());
        } catch (RuntimeException ex) {
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait());
        }
    }

    @FXML
    private void onActualizar() {
        try {
            tabla.getItems().setAll(dao.listar());
        } catch (RuntimeException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    @SuppressWarnings("unchecked")
    private void col(int indice, Function<Seguimiento, ?> valor) {
        TableColumn<Seguimiento, String> c = (TableColumn<Seguimiento, String>) tabla.getColumns().get(indice);
        c.setCellValueFactory(d -> new SimpleStringProperty(Objects.toString(valor.apply(d.getValue()), "")));
    }
}
