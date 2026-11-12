package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.App;
import edu.utj.dsm.poo.escuela.modelo.Usuario;
import edu.utj.dsm.poo.escuela.seguridad.Accion;
import edu.utj.dsm.poo.escuela.seguridad.Sesion;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class PrincipalController {

    @FXML private ScrollPane contenedor;
    @FXML private Label lblUsuario;
    @FXML private MenuItem miAlumnos, miDocentes, miAdministradores, miMaterias, miCalificaciones,
            miAsistencia, miSeguimiento, miDepartamentos, miAulas, miHorarios;


    @FXML
    private void initialize() {
        Usuario u = Sesion.getUsuario();
        lblUsuario.setText(u == null ? "" : u.getNombre() + "  |  " + u.getRol());
        restringir(miAlumnos, "alumno");
        restringir(miDocentes, "docente");
        restringir(miAdministradores, "administrador");
        restringir(miMaterias, "materia");
        restringir(miCalificaciones, "calificaciones");
        restringir(miAsistencia, "asistencia");
        restringir(miSeguimiento, "seguimiento");
        restringir(miDepartamentos, "departamento");
        restringir(miAulas, "aula");
        restringir(miHorarios, "horario");
    }

    private void restringir(MenuItem item, String modulo) {
        item.setDisable(!Sesion.puede(modulo, Accion.LEER));
    }

    @FXML private void onAlumnos()         { abrir("alumno"); }
    @FXML private void onDocentes()        { abrir("docente"); }
    @FXML private void onAdministradores() { abrir("administrador"); }
    @FXML private void onMaterias()        { abrir("materia"); }
    @FXML private void onCalificaciones()  { abrir("calificaciones"); }
    @FXML private void onAsistencia()      { abrir("asistencia"); }
    @FXML private void onSeguimiento()     { abrir("seguimiento"); }
    @FXML private void onDepartamentos()   { abrir("departamento"); }
    @FXML private void onAulas()           { abrir("aula"); }
    @FXML private void onHorarios()        { abrir("horario"); }


    @FXML
    private void onCerrarSesion() {
        Sesion.cerrar();
        ((Stage) contenedor.getScene().getWindow()).close();
    }

    private void abrir(String vista) {
        if (!Sesion.puede(vista, Accion.LEER)) {   
            new Alert(Alert.AlertType.WARNING, "Tu perfil (" + Sesion.nombreRol() + ") no tiene acceso a este módulo.").showAndWait();
            return;
        }
        try {
            contenedor.setContent(App.cargarVista(vista));
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir el módulo: " + ex.getMessage()).showAndWait();
        }
    }
}
