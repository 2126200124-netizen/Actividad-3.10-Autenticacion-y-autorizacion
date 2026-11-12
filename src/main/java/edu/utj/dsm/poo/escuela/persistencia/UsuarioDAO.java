// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.Usuario;
import edu.utj.dsm.poo.escuela.seguridad.Contrasenas;
import edu.utj.dsm.poo.escuela.seguridad.CredencialesException;
import java.sql.ResultSet;
import java.sql.SQLException;

 
public class UsuarioDAO {

    private static final String SEL = "SELECT u.id_usuario, u.nombre, u.correo, u.contrasena, u.id_rol, "
            + "r.nombre AS rol, u.estatus FROM usuario u JOIN rol r ON r.id_rol = u.id_rol";


    private static final class Fila {
        Usuario usuario;
        String hash;
    }

    private static Fila mapear(ResultSet rs) throws SQLException {
        Fila f = new Fila();
        f.usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("correo"),
                rs.getInt("id_rol"), rs.getString("rol"), rs.getString("estatus"));
        f.hash = rs.getString("contrasena");
        return f;
    }

    /**
     * @param identificador ID numérico de usuario o correo electrónico
     * @throws CredencialesException si los datos no son correctos o la cuenta está inactiva
     */
    public Usuario autenticar(String identificador, String clave) {
        String id = identificador == null ? "" : identificador.trim();
        boolean esId = id.matches("\\d{1,9}");
        Fila f = Db.uno(SEL + (esId ? " WHERE u.id_usuario = ?" : " WHERE u.correo = ?"),
                UsuarioDAO::mapear, esId ? (Object) Integer.valueOf(id) : id);

        if (f == null || !Contrasenas.verificar(clave, f.hash)) {
            throw new CredencialesException("ID/correo o contraseña incorrectos.");
        }
        if (!f.usuario.estaActivo()) {
            throw new CredencialesException("La cuenta está inactiva. Contacta al administrador.");
        }
        return f.usuario;
    }
}
