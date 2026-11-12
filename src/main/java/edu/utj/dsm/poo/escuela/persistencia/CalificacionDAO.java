// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.calificacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CalificacionDAO implements listable<calificacion> {

    private static final String SEL = "SELECT c.*, m.nombre AS materia FROM calificacion c "
            + "JOIN materia m ON m.id_materia = c.id_materia";

    private static calificacion mapear(ResultSet rs) throws SQLException {
        calificacion c = new calificacion(rs.getInt("id_calificacion"), rs.getString("matricula"),
                rs.getInt("id_materia"), rs.getString("periodo"), rs.getDouble("valor"));
        c.setNombreMateria(rs.getString("materia"));
        c.setRutaImagen(rs.getString("ruta_imagen"));
        return c;
    }

    @Override
    public boolean agregar(calificacion c) {
        return Db.ejecutar("INSERT INTO calificacion (id_calificacion, matricula, id_materia, periodo, valor, "
                + "ruta_imagen) VALUES (?,?,?,?,?,?)", c.getidCalificaciones(), c.getMatriculaAlumno(),
                c.getIdMateria(), c.getPeriodo(), c.getValorCalificacion(), c.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(calificacion c) {
        return Db.ejecutar("UPDATE calificacion SET matricula=?, id_materia=?, periodo=?, valor=?, ruta_imagen=? "
                + "WHERE id_calificacion=?", c.getMatriculaAlumno(), c.getIdMateria(), c.getPeriodo(),
                c.getValorCalificacion(), c.getRutaImagen(), c.getidCalificaciones()) > 0;
    }

    @Override
    public boolean eliminar(calificacion c) {
        return Db.ejecutar("DELETE FROM calificacion WHERE id_calificacion=?", c.getidCalificaciones()) > 0;
    }

    @Override
    public List<calificacion> listar() {
        return Db.consultar(SEL + " ORDER BY c.id_calificacion", CalificacionDAO::mapear);
    }

    @Override
    public calificacion traer(String clave) {
        return Db.uno(SEL + " WHERE c.id_calificacion = ?", CalificacionDAO::mapear, Integer.parseInt(clave));
    }
}
