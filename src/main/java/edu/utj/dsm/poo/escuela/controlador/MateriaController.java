// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.materia;
import edu.utj.dsm.poo.escuela.persistencia.MateriaDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MateriaController extends FormularioBase<materia> {

    @FXML private TextField txtIdMateria, txtCodigo, txtNombre, txtCreditos;
    @FXML private ComboBox<String> cmbEstatus;
    @FXML private Label lblErrorIdMateria, lblErrorCodigo, lblErrorNombre, lblErrorCreditos, lblErrorEstatus;

    private final MateriaDAO dao = new MateriaDAO();

    @Override protected listable<materia> dao() { return dao; }

    @Override protected String clave(materia m) { return String.valueOf(m.getIdMateria()); }

    @Override
    protected void configurar() {
        campoEntero(txtIdMateria, lblErrorIdMateria, 1, 999999);
        campoAlfanumerico(txtCodigo, lblErrorCodigo, 10, "MAT-101");
        campoTexto(txtNombre, lblErrorNombre, 50);
        campoEntero(txtCreditos, lblErrorCreditos, 1, 20);
        cmbEstatus.getItems().addAll("Activa", "Inactiva");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
    }

    @Override
    protected void configurarTabla() {
        col(0, materia::getIdMateria);
        col(1, materia::getCodigo);
        col(2, materia::getNombre);
        col(3, materia::getCreditos);
        col(4, materia::getEstatus);
    }

    @Override
    protected materia leer() {
        materia m = new materia(Integer.parseInt(txtIdMateria.getText().trim()), txtCodigo.getText().trim(),
                txtNombre.getText().trim(), Integer.parseInt(txtCreditos.getText().trim()));
        m.setEstatus(cmbEstatus.getValue());
        return m;
    }

    @Override
    protected void mostrar(materia m) {
        txtIdMateria.setText(String.valueOf(m.getIdMateria()));
        txtCodigo.setText(m.getCodigo());
        txtNombre.setText(m.getNombre());
        txtCreditos.setText(String.valueOf(m.getCreditos()));
        cmbEstatus.setValue(m.getEstatus());
    }

    @Override protected void modoEdicion(boolean editando) { txtIdMateria.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtIdMateria.clear();
        txtCodigo.clear();
        txtNombre.clear();
        txtCreditos.clear();
        cmbEstatus.setValue(null);
    }
}
