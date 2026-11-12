// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.Permiso;
import edu.utj.dsm.poo.escuela.seguridad.Accion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PermisoDAO {

    private static Permiso mapear(ResultSet rs) throws SQLException {
        return new Permiso(rs.getString("modulo"), rs.getInt("crear") == 1, rs.getInt("leer") == 1,
                rs.getInt("actualizar") == 1, rs.getInt("eliminar") == 1);
    }


    public List<Permiso> listarPorRol(int idRol) {
        return Db.consultar("SELECT modulo, crear, leer, actualizar, eliminar FROM permiso WHERE id_rol = ?",
                PermisoDAO::mapear, idRol);
    }


    public boolean tiene(int idRol, String modulo, Accion accion) {

        Boolean r = Db.uno("SELECT " + accion.columna() + " FROM permiso WHERE id_rol = ? AND modulo = ?",
                rs -> rs.getInt(1) == 1, idRol, modulo);
        return r != null && r;
    }
}
