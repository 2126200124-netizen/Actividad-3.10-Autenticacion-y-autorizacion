// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.Alumno;
import edu.utj.dsm.poo.escuela.modelo.asistencia;
import edu.utj.dsm.poo.escuela.modelo.materia;
import edu.utj.dsm.poo.escuela.persistencia.AlumnoDAO;
import edu.utj.dsm.poo.escuela.persistencia.AsistenciaDAO;
import edu.utj.dsm.poo.escuela.persistencia.MateriaDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AsistenciaController extends FormularioBase<asistencia> {

    @FXML private TextField txtId, txtPeriodo;
    @FXML private ComboBox<Alumno> cmbMatricula;
    @FXML private ComboBox<materia> cmbMateria;
    @FXML private ComboBox<String> cmbEstatus;
    @FXML private DatePicker dpFecha;
    @FXML private Label lblErrorId, lblErrorMatricula, lblErrorMateria, lblErrorPeriodo, lblErrorFecha, lblErrorEstatus;

    private final AsistenciaDAO dao = new AsistenciaDAO();

    @Override protected listable<asistencia> dao() { return dao; }

    @Override protected String clave(asistencia a) { return String.valueOf(a.getIdAsistencia()); }

    @Override
    protected void configurar() {
        campoEntero(txtId, lblErrorId, 1, 999999);
        convertidor(cmbMatricula, a -> a.getMatricula() + " - " + a.getNombre() + " " + a.getApellido());
        campoSeleccion(cmbMatricula, lblErrorMatricula);
        convertidor(cmbMateria, m -> m.getCodigo() + " - " + m.getNombre());
        campoSeleccion(cmbMateria, lblErrorMateria);
        campoAlfanumerico(txtPeriodo, lblErrorPeriodo, 10, "2026-1");
        campoFecha(dpFecha, lblErrorFecha);
        cmbEstatus.getItems().addAll("Presente", "Ausente", "Retardo", "Justificado");
        campoSeleccion(cmbEstatus, lblErrorEstatus);
    }

    @Override
    protected void cargarCatalogos() {
        cmbMatricula.getItems().setAll(new AlumnoDAO().listar());
        cmbMateria.getItems().setAll(new MateriaDAO().listar());
    }

    @Override
    protected void configurarTabla() {
        col(0, asistencia::getIdAsistencia);
        col(1, asistencia::getMatriculaAlumno);
        col(2, asistencia::getNombreMateria);
        col(3, asistencia::getPeriodo);
        col(4, asistencia::getFecha);
        col(5, asistencia::getEstatus);
    }

    @Override
    protected asistencia leer() {
        return new asistencia(Integer.parseInt(txtId.getText().trim()), cmbMatricula.getValue().getMatricula(),
                cmbMateria.getValue().getIdMateria(), txtPeriodo.getText().trim(), dpFecha.getValue(),
                cmbEstatus.getValue());
    }

    @Override
    protected void mostrar(asistencia a) {
        txtId.setText(String.valueOf(a.getIdAsistencia()));
        cmbMatricula.setValue(buscar(cmbMatricula, x -> x.getMatricula().equals(a.getMatriculaAlumno())));
        cmbMateria.setValue(buscar(cmbMateria, x -> x.getIdMateria() == a.getIdMateria()));
        txtPeriodo.setText(a.getPeriodo());
        dpFecha.setValue(a.getFecha());
        cmbEstatus.setValue(a.getEstatus());
    }

    @Override protected void modoEdicion(boolean editando) { txtId.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtId.clear();
        cmbMatricula.setValue(null);
        cmbMateria.setValue(null);
        txtPeriodo.clear();
        dpFecha.setValue(null);
        cmbEstatus.setValue(null);
    }
}
