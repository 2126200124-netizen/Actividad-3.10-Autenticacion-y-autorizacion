// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.aula;
import edu.utj.dsm.poo.escuela.persistencia.AulaDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AulaController extends FormularioBase<aula> {

    @FXML private TextField txtIdAula, txtNumeroAula, txtCapacidad;
    @FXML private ComboBox<String> cmbEstatus;
    @FXML private Label lblErrorIdAula, lblErrorNumeroAula, lblErrorCapacidad, lblErrorEstatus;

    private final AulaDAO dao = new AulaDAO();

    @Override protected listable<aula> dao() { return dao; }

    @Override protected String clave(aula a) { return String.valueOf(a.getIdAula()); }

    @Override
    protected void configurar() {
        campoEntero(txtIdAula, lblErrorIdAula, 1, 999999);
        campoAlfanumerico(txtNumeroAula, lblErrorNumeroAula, 10, "A-101");
        campoEntero(txtCapacidad, lblErrorCapacidad, 1, 500);
        cmbEstatus.getItems().addAll("Disponible", "Ocupada", "Mantenimiento");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
    }

    @Override
    protected void configurarTabla() {
        col(0, aula::getIdAula);
        col(1, aula::getNumeroAula);
        col(2, aula::getCapacidad);
        col(3, aula::getEstatus);
    }

    @Override
    protected aula leer() {
        aula a = new aula(Integer.parseInt(txtIdAula.getText().trim()), txtNumeroAula.getText().trim(),
                Integer.parseInt(txtCapacidad.getText().trim()));
        a.setEstatus(cmbEstatus.getValue());
        return a;
    }

    @Override
    protected void mostrar(aula a) {
        txtIdAula.setText(String.valueOf(a.getIdAula()));
        txtNumeroAula.setText(a.getNumeroAula());
        txtCapacidad.setText(String.valueOf(a.getCapacidad()));
        cmbEstatus.setValue(a.getEstatus());
    }

    @Override protected void modoEdicion(boolean editando) { txtIdAula.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtIdAula.clear();
        txtNumeroAula.clear();
        txtCapacidad.clear();
        cmbEstatus.setValue(null);
    }
}
