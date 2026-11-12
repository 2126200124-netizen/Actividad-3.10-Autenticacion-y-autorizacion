// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.horario;
import edu.utj.dsm.poo.escuela.persistencia.HorarioDAO;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import edu.utj.dsm.poo.escuela.util.Validaciones;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HorarioController extends FormularioBase<horario> {

    @FXML private TextField txtId, txtInicio, txtFin;
    @FXML private ComboBox<String> cmbDia;
    @FXML private Label lblErrorId, lblErrorDia, lblErrorInicio, lblErrorFin;

    private final HorarioDAO dao = new HorarioDAO();

    @Override protected listable<horario> dao() { return dao; }

    @Override protected String clave(horario h) { return String.valueOf(h.getIdHorario()); }

    @Override
    protected void configurar() {
        campoEntero(txtId, lblErrorId, 1, 999999);
        cmbDia.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
        campoSeleccion(cmbDia, lblErrorDia);
        Validaciones.filtrar(txtInicio, lblErrorInicio, Validaciones.R_HORA, 5, false, "Solo números y dos puntos (ej. 08:00)");
        registrar(txtInicio.textProperty(), lblErrorInicio, () -> Validaciones.hora(txtInicio.getText()));
        Validaciones.filtrar(txtFin, lblErrorFin, Validaciones.R_HORA, 5, false, "Solo números y dos puntos (ej. 10:00)");
        registrar(txtFin.textProperty(), lblErrorFin, this::validarHoraFin, txtInicio.textProperty());
    }

    @Override
    protected void configurarTabla() {
        col(0, horario::getIdHorario);
        col(1, horario::getDiaSemana);
        col(2, horario::getHoraInicio);
        col(3, horario::getHoraFin);
    }

    @Override
    protected horario leer() {
        return new horario(Integer.parseInt(txtId.getText().trim()), cmbDia.getValue(),
                txtInicio.getText().trim(), txtFin.getText().trim());
    }

    @Override
    protected void mostrar(horario h) {
        txtId.setText(String.valueOf(h.getIdHorario()));
        cmbDia.setValue(h.getDiaSemana());
        txtInicio.setText(h.getHoraInicio());
        txtFin.setText(h.getHoraFin());
    }

    @Override protected void modoEdicion(boolean editando) { txtId.setDisable(editando); }

    @Override
    protected void limpiarCampos() {
        txtId.clear();
        cmbDia.setValue(null);
        txtInicio.clear();
        txtFin.clear();
    }

    private String validarHoraFin() {
        String e = Validaciones.hora(txtFin.getText());
        if (e != null) return e;
        if (Validaciones.hora(txtInicio.getText()) == null
                && Validaciones.minutos(txtFin.getText()) <= Validaciones.minutos(txtInicio.getText())) {
            return "La hora fin debe ser posterior a la hora inicio";
        }
        return null;
    }
}
