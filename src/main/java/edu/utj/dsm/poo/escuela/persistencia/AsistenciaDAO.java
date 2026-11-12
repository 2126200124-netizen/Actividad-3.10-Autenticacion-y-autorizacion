// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.asistencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AsistenciaDAO implements listable<asistencia> {

    private static final String SEL = "SELECT s.*, m.nombre AS materia FROM asistencia s "
            + "JOIN materia m ON m.id_materia = s.id_materia";

    private static asistencia mapear(ResultSet rs) throws SQLException {
        asistencia a = new asistencia(rs.getInt("id_asistencia"), rs.getString("matricula"), rs.getInt("id_materia"),
                rs.getString("periodo"), rs.getObject("fecha", LocalDate.class), rs.getString("estatus"));
        a.setNombreMateria(rs.getString("materia"));
        a.setRutaImagen(rs.getString("ruta_imagen"));
        return a;
    }

    @Override
    public boolean agregar(asistencia a) {
        return Db.ejecutar("INSERT INTO asistencia (id_asistencia, matricula, id_materia, periodo, fecha, estatus, "
                + "ruta_imagen) VALUES (?,?,?,?,?,?,?)", a.getIdAsistencia(), a.getMatriculaAlumno(), a.getIdMateria(),
                a.getPeriodo(), a.getFecha(), a.getEstatus(), a.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(asistencia a) {
        return Db.ejecutar("UPDATE asistencia SET matricula=?, id_materia=?, periodo=?, fecha=?, estatus=?, "
                + "ruta_imagen=? WHERE id_asistencia=?", a.getMatriculaAlumno(), a.getIdMateria(), a.getPeriodo(),
                a.getFecha(), a.getEstatus(), a.getRutaImagen(), a.getIdAsistencia()) > 0;
    }

    @Override
    public boolean eliminar(asistencia a) {
        return Db.ejecutar("DELETE FROM asistencia WHERE id_asistencia=?", a.getIdAsistencia()) > 0;
    }

    @Override
    public List<asistencia> listar() {
        return Db.consultar(SEL + " ORDER BY s.fecha DESC, s.id_asistencia DESC", AsistenciaDAO::mapear);
    }

    @Override
    public asistencia traer(String clave) {
        return Db.uno(SEL + " WHERE s.id_asistencia = ?", AsistenciaDAO::mapear, Integer.parseInt(clave));
    }
}
