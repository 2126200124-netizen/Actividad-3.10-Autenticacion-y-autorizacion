// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.Seguimiento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SeguimientoDAO {

    private static Seguimiento mapear(ResultSet rs) throws SQLException {
        double pct = rs.getDouble("pct_asistencia");
        Double porcentaje = rs.wasNull() ? null : pct;
        return new Seguimiento(rs.getString("matricula"), rs.getString("alumno"), rs.getString("materia"),
                rs.getString("periodo"), rs.getDouble("calificacion"), rs.getString("resultado"),
                rs.getInt("clases_registradas"), porcentaje);
    }

    public List<Seguimiento> listar() {
        return Db.consultar("SELECT v.matricula, CONCAT(a.nombre, ' ', a.apellido) AS alumno, m.nombre AS materia, "
                + "v.periodo, v.calificacion, v.resultado, v.clases_registradas, v.pct_asistencia "
                + "FROM v_seguimiento v JOIN alumno a ON a.matricula = v.matricula "
                + "JOIN materia m ON m.id_materia = v.id_materia ORDER BY v.periodo DESC, v.matricula",
                SeguimientoDAO::mapear);
    }
}
