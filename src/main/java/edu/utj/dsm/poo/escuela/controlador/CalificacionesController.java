// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.Alumno;
import edu.utj.dsm.poo.escuela.modelo.calificacion;
import edu.utj.dsm.poo.escuela.modelo.materia;
import edu.utj.dsm.poo.escuela.persistencia.AlumnoDAO;
import edu.utj.dsm.poo.escuela.persistencia.CalificacionDAO;
import edu.utj.dsm.poo.escuela.persistencia.MateriaDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalificacionesController extends FormularioBase<calificacion> {

    @FXML private TextField txtId, txtPeriodo, txtValor;
    @FXML private ComboBox<Alumno> cmbMatricula;
    @FXML private ComboBox<materia> cmbMateria;
    @FXML private Label lblErrorId, lblErrorMatricula, lblErrorMateria, lblErrorPeriodo, lblErrorValor;

    private final CalificacionDAO dao = new CalificacionDAO();

    @Override protected listable<calificacion> dao() { return dao; }

    @Override protected String clave(calificacion c) { return String.valueOf(c.getidCalificaciones()); }

    @Override
    protected void configurar() {
        campoEntero(txtId, lblErrorId, 1, 999999);
        convertidor(cmbMatricula, a -> a.getMatricula() + " - " + a.getNombre() + " " + a.getApellido());
        campoSeleccion(cmbMatricula, lblErrorMatricula);
        convertidor(cmbMateria, m -> m.getCodigo() + " - " + m.getNombre());
        campoSeleccion(cmbMateria, lblErrorMateria);
        campoAlfanumerico(txtPeriodo, lblErrorPeriodo, 10, "2026-1");
        campoDecimal(txtValor, lblErrorValor, 0, 100);
    }

    @Override
    protected void cargarCatalogos() {
        cmbMatricula.getItems().setAll(new AlumnoDAO().listar());
        cmbMateria.getItems().setAll(new MateriaDAO().listar());
    }

    @Override
    protected void configurarTabla() {
        col(0, calificacion::getidCalificaciones);
        col(1, calificacion::getMatriculaAlumno);
        col(2, calificacion::getNombreMateria);
        col(3, calificacion::getPeriodo);
        col(4, calificacion::getValorCalificacion);
        col(5, c -> c.estaAprobada() ? "Aprobado" : "Reprobado");
    }

    @Override
    protected calificacion leer() {
        return new calificacion(Integer.parseInt(txtId.getText().trim()), cmbMatricula.getValue().getMatricula(),
                cmbMateria.getValue().getIdMateria(), txtPeriodo.getText().trim(),
                Double.parseDouble(txtValor.getText().trim()));
    }

    @Override
    protected void mostrar(calificacion c) {
        txtId.setText(String.valueOf(c.getidCalificaciones()));
        cmbMatricula.setValue(buscar(cmbMatricula, x -> x.getMatricula().equals(c.getMatriculaAlumno())));
        cmbMateria.setValue(buscar(cmbMateria, x -> x.getIdMateria() == c.getIdMateria()));
        txtPeriodo.setText(c.getPeriodo());
        txtValor.setText(java.math.BigDecimal.valueOf(c.getValorCalificacion()).stripTrailingZeros().toPlainString());
    }

    @Override protected void modoEdicion(boolean editando) { txtId.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtId.clear();
        cmbMatricula.setValue(null);
        cmbMateria.setValue(null);
        txtPeriodo.clear();
        txtValor.clear();
    }
}
