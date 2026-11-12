// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.seguridad;

import edu.utj.dsm.poo.escuela.modelo.Permiso;
import edu.utj.dsm.poo.escuela.modelo.Usuario;
import edu.utj.dsm.poo.escuela.persistencia.SesionDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Sesion {

    private static Usuario usuario;
    private static int idSesion;
    private static final Map<String, Permiso> PERMISOS = new HashMap<>();

    private Sesion() { }

    public static void iniciar(Usuario u, List<Permiso> permisos, int id) {
        usuario = u;
        idSesion = id;
        PERMISOS.clear();
        for (Permiso p : permisos) PERMISOS.put(p.getModulo(), p);
    }


    public static void cerrar() {
        if (usuario != null) {
            try {
                new SesionDAO().registrarCierre(idSesion);
            } catch (RuntimeException ex) {
                System.err.println("No se pudo registrar el cierre de sesión: " + ex.getMessage());
            }
        }
        usuario = null;
        idSesion = 0;
        PERMISOS.clear();
    }

    public static boolean estaActiva() { return usuario != null; }

    public static Usuario getUsuario() { return usuario; }

    public static String nombreRol() { return usuario == null ? "sin sesión" : usuario.getRol(); }


    public static boolean puede(String modulo, Accion accion) {
        Permiso p = PERMISOS.get(modulo);
        return usuario != null && p != null && p.permite(accion);
    }
}
