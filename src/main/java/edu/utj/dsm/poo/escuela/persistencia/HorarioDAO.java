// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.horario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class HorarioDAO implements listable<horario> {

    private static horario mapear(ResultSet rs) throws SQLException {
        horario h = new horario(rs.getInt("id_horario"), rs.getString("dia_semana"),
                rs.getTime("hora_inicio").toLocalTime().toString(), rs.getTime("hora_fin").toLocalTime().toString());
        h.setRutaImagen(rs.getString("ruta_imagen"));
        return h;
    }

    @Override
    public boolean agregar(horario h) {
        return Db.ejecutar("INSERT INTO horario (id_horario, dia_semana, hora_inicio, hora_fin, ruta_imagen) "
                + "VALUES (?,?,?,?,?)", h.getIdHorario(), h.getDiaSemana(), LocalTime.parse(h.getHoraInicio()),
                LocalTime.parse(h.getHoraFin()), h.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(horario h) {
        return Db.ejecutar("UPDATE horario SET dia_semana=?, hora_inicio=?, hora_fin=?, ruta_imagen=? WHERE id_horario=?",
                h.getDiaSemana(), LocalTime.parse(h.getHoraInicio()), LocalTime.parse(h.getHoraFin()),
                h.getRutaImagen(), h.getIdHorario()) > 0;
    }

    @Override
    public boolean eliminar(horario h) {
        return Db.ejecutar("DELETE FROM horario WHERE id_horario=?", h.getIdHorario()) > 0;
    }

    @Override
    public List<horario> listar() {
        return Db.consultar("SELECT * FROM horario ORDER BY id_horario", HorarioDAO::mapear);
    }

    @Override
    public horario traer(String clave) {
        return Db.uno("SELECT * FROM horario WHERE id_horario = ?", HorarioDAO::mapear, Integer.parseInt(clave));
    }
}
