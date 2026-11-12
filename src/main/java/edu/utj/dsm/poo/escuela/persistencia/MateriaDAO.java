// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.materia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MateriaDAO implements listable<materia> {

    private static materia mapear(ResultSet rs) throws SQLException {
        materia m = new materia(rs.getInt("id_materia"), rs.getString("codigo"), rs.getString("nombre"),
                rs.getInt("creditos"));
        m.setEstatus(rs.getString("estatus"));
        m.setRutaImagen(rs.getString("ruta_imagen"));
        return m;
    }

    @Override
    public boolean agregar(materia m) {
        return Db.ejecutar("INSERT INTO materia (id_materia, codigo, nombre, creditos, estatus, ruta_imagen) "
                + "VALUES (?,?,?,?,?,?)", m.getIdMateria(), m.getCodigo(), m.getNombre(), m.getCreditos(),
                m.getEstatus(), m.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(materia m) {
        return Db.ejecutar("UPDATE materia SET codigo=?, nombre=?, creditos=?, estatus=?, ruta_imagen=? "
                + "WHERE id_materia=?", m.getCodigo(), m.getNombre(), m.getCreditos(), m.getEstatus(),
                m.getRutaImagen(), m.getIdMateria()) > 0;
    }

    @Override
    public boolean eliminar(materia m) {
        return Db.ejecutar("DELETE FROM materia WHERE id_materia=?", m.getIdMateria()) > 0;
    }

    @Override
    public List<materia> listar() {
        return Db.consultar("SELECT * FROM materia ORDER BY id_materia", MateriaDAO::mapear);
    }

    @Override
    public materia traer(String clave) {
        return Db.uno("SELECT * FROM materia WHERE id_materia = ?", MateriaDAO::mapear, Integer.parseInt(clave));
    }
}
