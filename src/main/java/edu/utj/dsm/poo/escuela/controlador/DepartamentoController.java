//Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.departamento;
import edu.utj.dsm.poo.escuela.persistencia.DepartamentoDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoController extends FormularioBase<departamento> {

    @FXML private TextField txtId, txtNombre, txtEdificio;
    @FXML private ComboBox<String> cmbEstatus;
    @FXML private Label lblErrorId, lblErrorNombre, lblErrorEdificio, lblErrorEstatus;

    private final DepartamentoDAO dao = new DepartamentoDAO();

    @Override protected listable<departamento> dao() { return dao; }

    @Override protected String clave(departamento d) { return String.valueOf(d.getIdDepartamento()); }

    @Override
    protected void configurar() {
        campoEntero(txtId, lblErrorId, 1, 999999);
        campoTexto(txtNombre, lblErrorNombre, 50);
        campoTexto(txtEdificio, lblErrorEdificio, 30);
        cmbEstatus.getItems().addAll("Activo", "Inactivo");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
    }

    @Override
    protected void configurarTabla() {
        col(0, departamento::getIdDepartamento);
        col(1, departamento::getNombreDepartamento);
        col(2, departamento::getEdificio);
        col(3, departamento::getEstatus);
    }

    @Override
    protected departamento leer() {
        departamento d = new departamento(Integer.parseInt(txtId.getText().trim()), txtNombre.getText().trim(),
                txtEdificio.getText().trim());
        d.setEstatus(cmbEstatus.getValue());
        return d;
    }

    @Override
    protected void mostrar(departamento d) {
        txtId.setText(String.valueOf(d.getIdDepartamento()));
        txtNombre.setText(d.getNombreDepartamento());
        txtEdificio.setText(d.getEdificio());
        cmbEstatus.setValue(d.getEstatus());
    }

    @Override protected void modoEdicion(boolean editando) { txtId.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtEdificio.clear();
        cmbEstatus.setValue(null);
    }
}
