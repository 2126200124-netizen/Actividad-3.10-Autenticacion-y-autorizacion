package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.App;
import edu.utj.dsm.poo.escuela.modelo.Permiso;
import edu.utj.dsm.poo.escuela.modelo.Usuario;
import edu.utj.dsm.poo.escuela.persistencia.DbException;
import edu.utj.dsm.poo.escuela.persistencia.PermisoDAO;
import edu.utj.dsm.poo.escuela.persistencia.SesionDAO;
import edu.utj.dsm.poo.escuela.persistencia.UsuarioDAO;
import edu.utj.dsm.poo.escuela.seguridad.CredencialesException;
import edu.utj.dsm.poo.escuela.seguridad.Sesion;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AutenticacionController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private Label lblError;
    @FXML private Button btnEntrar;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final PermisoDAO permisoDAO = new PermisoDAO();
    private final SesionDAO sesionDAO = new SesionDAO();

    @FXML
    private void onEntrar() {
        lblError.setText("");
        String identificador = txtUsuario.getText() == null ? "" : txtUsuario.getText().trim();
        String clave = txtClave.getText() == null ? "" : txtClave.getText();
        if (identificador.isEmpty() || clave.isEmpty()) {
            lblError.setText("Escribe tu ID o correo y tu contraseña.");
            return;
        }
        btnEntrar.setDisable(true);
        try {
            Usuario usuario = usuarioDAO.autenticar(identificador, clave);        // 1. credenciales + cuenta activa
            List<Permiso> permisos = permisoDAO.listarPorRol(usuario.getIdRol()); // 2. permisos del perfil
            int idSesion = sesionDAO.registrarInicio(usuario.getIdUsuario());     // 3. bitácora de inicio
            Sesion.iniciar(usuario, permisos, idSesion);
            App.abrirPrincipal();
        } catch (CredencialesException | DbException ex) {
            lblError.setText(ex.getMessage());
            txtClave.clear();
        } catch (IOException ex) {
            Sesion.cerrar();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir la ventana principal: " + ex.getMessage()).showAndWait();
        } finally {
            btnEntrar.setDisable(false);
        }
    }

    @FXML
    private void onCerrar() {
        Platform.exit(); // finaliza la aplicación por completo
    }
}
