// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.departamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartamentoDAO implements listable<departamento> {

    private static departamento mapear(ResultSet rs) throws SQLException {
        departamento d = new departamento(rs.getInt("id_departamento"), rs.getString("nombre"), rs.getString("edificio"));
        d.setEstatus(rs.getString("estatus"));
        d.setRutaImagen(rs.getString("ruta_imagen"));
        return d;
    }

    @Override
    public boolean agregar(departamento d) {
        return Db.ejecutar("INSERT INTO departamento (id_departamento, nombre, edificio, estatus, ruta_imagen) "
                + "VALUES (?,?,?,?,?)", d.getIdDepartamento(), d.getNombreDepartamento(), d.getEdificio(),
                d.getEstatus(), d.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(departamento d) {
        return Db.ejecutar("UPDATE departamento SET nombre=?, edificio=?, estatus=?, ruta_imagen=? "
                + "WHERE id_departamento=?", d.getNombreDepartamento(), d.getEdificio(), d.getEstatus(),
                d.getRutaImagen(), d.getIdDepartamento()) > 0;
    }

    @Override
    public boolean eliminar(departamento d) {
        return Db.ejecutar("DELETE FROM departamento WHERE id_departamento=?", d.getIdDepartamento()) > 0;
    }

    @Override
    public List<departamento> listar() {
        return Db.consultar("SELECT * FROM departamento ORDER BY id_departamento", DepartamentoDAO::mapear);
    }

    @Override
    public departamento traer(String clave) {
        return Db.uno("SELECT * FROM departamento WHERE id_departamento = ?", DepartamentoDAO::mapear,
                Integer.parseInt(clave));
    }
}
