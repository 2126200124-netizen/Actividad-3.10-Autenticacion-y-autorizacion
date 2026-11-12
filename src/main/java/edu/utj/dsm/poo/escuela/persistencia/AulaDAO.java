// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.aula;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AulaDAO implements listable<aula> {

    private static aula mapear(ResultSet rs) throws SQLException {
        aula a = new aula(rs.getInt("id_aula"), rs.getString("numero_aula"), rs.getInt("capacidad"));
        a.setEstatus(rs.getString("estatus"));
        a.setRutaImagen(rs.getString("ruta_imagen"));
        return a;
    }

    @Override
    public boolean agregar(aula a) {
        return Db.ejecutar("INSERT INTO aula (id_aula, numero_aula, capacidad, estatus, ruta_imagen) VALUES (?,?,?,?,?)",
                a.getIdAula(), a.getNumeroAula(), a.getCapacidad(), a.getEstatus(), a.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(aula a) {
        return Db.ejecutar("UPDATE aula SET numero_aula=?, capacidad=?, estatus=?, ruta_imagen=? WHERE id_aula=?",
                a.getNumeroAula(), a.getCapacidad(), a.getEstatus(), a.getRutaImagen(), a.getIdAula()) > 0;
    }

    @Override
    public boolean eliminar(aula a) {
        return Db.ejecutar("DELETE FROM aula WHERE id_aula=?", a.getIdAula()) > 0;
    }

    @Override
    public List<aula> listar() {
        return Db.consultar("SELECT * FROM aula ORDER BY id_aula", AulaDAO::mapear);
    }

    @Override
    public aula traer(String clave) {
        return Db.uno("SELECT * FROM aula WHERE id_aula = ?", AulaDAO::mapear, Integer.parseInt(clave));
    }
}
