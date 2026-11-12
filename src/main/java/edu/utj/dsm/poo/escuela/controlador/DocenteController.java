// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.Docente;
import edu.utj.dsm.poo.escuela.modelo.departamento;
import edu.utj.dsm.poo.escuela.persistencia.DepartamentoDAO;
import edu.utj.dsm.poo.escuela.persistencia.DocenteDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DocenteController extends FormularioBase<Docente> {

    @FXML private TextField txtNombre, txtApellido, txtTelefono, txtEdad, txtRfc, txtNumDocente;
    @FXML private ComboBox<String> cmbEspecialidad, cmbEstatus;
    @FXML private ComboBox<departamento> cmbDepartamento;
    @FXML private Label lblErrorNombre, lblErrorApellido, lblErrorTelefono, lblErrorEdad, lblErrorRfc,
            lblErrorNumDocente, lblErrorEspecialidad, lblErrorEstatus;

    private static final departamento NINGUNO = new departamento(0, "- Sin departamento -", "");
    private final DocenteDAO dao = new DocenteDAO();

    @Override protected listable<Docente> dao() { return dao; }

    @Override protected String clave(Docente d) { return d.getNumDocente(); }

    @Override
    protected void configurar() {
        campoLetras(txtNombre, lblErrorNombre, 40);
        campoLetras(txtApellido, lblErrorApellido, 40);
        campoTelefono(txtTelefono, lblErrorTelefono);
        campoEntero(txtEdad, lblErrorEdad, 18, 99);
        campoRfc(txtRfc, lblErrorRfc);
        campoEntero(txtNumDocente, lblErrorNumDocente, 1, 999999);
        cmbEspecialidad.getItems().addAll("Matemáticas", "Programación", "Redes", "Bases de datos", "Inglés");
        campoSeleccion(cmbEspecialidad, lblErrorEspecialidad);
        cmbEstatus.getItems().addAll("Activo", "Inactivo", "Licencia");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
        convertidor(cmbDepartamento, departamento::getNombreDepartamento);
    }

    @Override
    protected void cargarCatalogos() {
        cmbDepartamento.getItems().setAll(NINGUNO);
        cmbDepartamento.getItems().addAll(new DepartamentoDAO().listar());
    }

    @Override
    protected void configurarTabla() {
        col(0, Docente::getNumDocente);
        col(1, Docente::getNombre);
        col(2, Docente::getApellido);
        col(3, Docente::getespecialidad);
        col(4, Docente::getEstatus);
    }

    @Override
    protected Docente leer() {
        Docente d = new Docente(txtNombre.getText().trim(), txtApellido.getText().trim(),
                Integer.parseInt(txtEdad.getText().trim()), txtTelefono.getText().trim(),
                txtNumDocente.getText().trim(), cmbEspecialidad.getValue());
        d.setRFC(txtRfc.getText().trim().toUpperCase());
        d.setEstatus(cmbEstatus.getValue());
        departamento dep = cmbDepartamento.getValue();
        d.setIdDepartamento(dep == null || dep.getIdDepartamento() == 0 ? null : dep.getIdDepartamento());
        return d;
    }

    @Override
    protected void mostrar(Docente d) {
        txtNombre.setText(d.getNombre());
        txtApellido.setText(d.getApellido());
        txtTelefono.setText(d.getTelefono());
        txtEdad.setText(String.valueOf(d.getEdad()));
        txtRfc.setText(d.getRFC());
        txtNumDocente.setText(d.getNumDocente());
        cmbEspecialidad.setValue(d.getespecialidad());
        cmbEstatus.setValue(d.getEstatus());
        Integer id = d.getIdDepartamento();
        cmbDepartamento.setValue(id == null ? NINGUNO : buscar(cmbDepartamento, x -> x.getIdDepartamento() == id));
    }

    @Override protected void modoEdicion(boolean editando) { txtNumDocente.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEdad.clear();
        txtRfc.clear();
        txtNumDocente.clear();
        cmbEspecialidad.setValue(null);
        cmbEstatus.setValue(null);
        cmbDepartamento.setValue(null);
    }
}
