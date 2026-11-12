//Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.administrador;
import edu.utj.dsm.poo.escuela.persistencia.AdministradorDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdministradorController extends FormularioBase<administrador> {

    @FXML private TextField txtNombre, txtApellido, txtTelefono, txtRfc, txtIdAdministracion, txtEdad;
    @FXML private ComboBox<String> cmbArea, cmbTurno, cmbEstatus;
    @FXML private Label lblErrorNombre, lblErrorApellido, lblErrorTelefono, lblErrorRfc, lblErrorIdAdministracion,
            lblErrorEdad, lblErrorArea, lblErrorTurno, lblErrorEstatus;

    private final AdministradorDAO dao = new AdministradorDAO();

    @Override protected listable<administrador> dao() { return dao; }

    @Override protected String clave(administrador a) { return String.valueOf(a.getidAdministrador()); }

    @Override
    protected void configurar() {
        campoLetras(txtNombre, lblErrorNombre, 40);
        campoLetras(txtApellido, lblErrorApellido, 40);
        campoTelefono(txtTelefono, lblErrorTelefono);
        campoRfc(txtRfc, lblErrorRfc);
        campoEntero(txtIdAdministracion, lblErrorIdAdministracion, 1, 999999);
        campoEntero(txtEdad, lblErrorEdad, 18, 99);
        cmbArea.getItems().addAll("Administración", "Finanzas", "Recursos Humanos", "Servicios Escolares");
        campoSeleccion(cmbArea, lblErrorArea);
        cmbTurno.getItems().addAll("Matutino", "Vespertino", "Mixto");
        campoSeleccion(cmbTurno, lblErrorTurno);
        cmbEstatus.getItems().addAll("Activo", "Inactivo");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
    }

    @Override
    protected void configurarTabla() {
        col(0, administrador::getidAdministrador);
        col(1, administrador::getNombre);
        col(2, administrador::getApellido);
        col(3, administrador::getArea);
        col(4, administrador::getTurno);
        col(5, administrador::getEstatus);
    }

    @Override
    protected administrador leer() {
        administrador a = new administrador(txtNombre.getText().trim(), txtApellido.getText().trim(),
                Integer.parseInt(txtEdad.getText().trim()), txtTelefono.getText().trim(),
                cmbTurno.getValue(), cmbArea.getValue());
        a.setidAdministrador(Integer.parseInt(txtIdAdministracion.getText().trim()));
        a.setRFC(txtRfc.getText().trim().toUpperCase());
        a.setEstatus(cmbEstatus.getValue());
        return a;
    }

    @Override
    protected void mostrar(administrador a) {
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtTelefono.setText(a.getTelefono());
        txtRfc.setText(a.getRFC());
        txtIdAdministracion.setText(String.valueOf(a.getidAdministrador()));
        txtEdad.setText(String.valueOf(a.getEdad()));
        cmbArea.setValue(a.getArea());
        cmbTurno.setValue(a.getTurno());
        cmbEstatus.setValue(a.getEstatus());
    }

    @Override protected void modoEdicion(boolean editando) { txtIdAdministracion.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtRfc.clear();
        txtIdAdministracion.clear();
        txtEdad.clear();
        cmbArea.setValue(null);
        cmbTurno.setValue(null);
        cmbEstatus.setValue(null);
    }
}
