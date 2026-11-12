// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.Alumno;
import edu.utj.dsm.poo.escuela.persistencia.AlumnoDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlumnoController extends FormularioBase<Alumno> {

    @FXML private TextField txtNombre, txtApellido, txtEdad, txtMatricula, txtTelefono;
    @FXML private ComboBox<String> cmbGrado, cmbEstatus;
    @FXML private Label lblErrorNombre, lblErrorApellido, lblErrorEdad, lblErrorMatricula, lblErrorTelefono,
            lblErrorGrado, lblErrorEstatus;

    private final AlumnoDAO dao = new AlumnoDAO();

    @Override protected listable<Alumno> dao() { return dao; }

    @Override protected String clave(Alumno a) { return a.getMatricula(); }

    @Override
    protected void configurar() {
        campoLetras(txtNombre, lblErrorNombre, 40);
        campoLetras(txtApellido, lblErrorApellido, 40);
        campoEntero(txtEdad, lblErrorEdad, 3, 99);
        campoAlfanumerico(txtMatricula, lblErrorMatricula, 12, "A12345");
        campoTelefono(txtTelefono, lblErrorTelefono);
        cmbGrado.getItems().addAll("1", "2", "3", "4", "5", "6");
        campoSeleccion(cmbGrado, lblErrorGrado);
        cmbEstatus.getItems().addAll("Activo", "Inactivo", "Baja", "Egresado");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
    }

    @Override
    protected void configurarTabla() {
        col(0, Alumno::getMatricula);
        col(1, Alumno::getNombre);
        col(2, Alumno::getApellido);
        col(3, Alumno::getGrado);
        col(4, a -> String.format("%.1f", a.getCalificacion()));
        col(5, Alumno::getEstatus);
    }

    @Override
    protected Alumno leer() {
        Alumno a = new Alumno(txtNombre.getText().trim(), txtApellido.getText().trim(),
                Integer.parseInt(txtEdad.getText().trim()), txtTelefono.getText().trim(),
                txtMatricula.getText().trim(), cmbGrado.getValue(), 0);
        a.setEstatus(cmbEstatus.getValue());
        return a;
    }

    @Override
    protected void mostrar(Alumno a) {
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtEdad.setText(String.valueOf(a.getEdad()));
        txtMatricula.setText(a.getMatricula());
        txtTelefono.setText(a.getTelefono());
        cmbGrado.setValue(a.getGrado());
        cmbEstatus.setValue(a.getEstatus());
    }

    @Override protected void modoEdicion(boolean editando) { txtMatricula.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtMatricula.clear();
        txtTelefono.clear();
        cmbGrado.setValue(null);
        cmbEstatus.setValue(null);
    }
}
