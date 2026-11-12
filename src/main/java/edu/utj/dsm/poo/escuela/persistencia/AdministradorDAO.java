// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.administrador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdministradorDAO implements listable<administrador> {

    private static administrador mapear(ResultSet rs) throws SQLException {
        administrador a = new administrador(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("edad"),
                rs.getString("telefono"), rs.getString("turno"), rs.getString("area"));
        a.setidAdministrador(rs.getInt("id_administrador"));
        a.setRFC(rs.getString("rfc"));
        a.setEstatus(rs.getString("estatus"));
        a.setRutaImagen(rs.getString("ruta_imagen"));
        return a;
    }

    @Override
    public boolean agregar(administrador a) {
        return Db.ejecutar("INSERT INTO administrador (id_administrador, nombre, apellido, edad, telefono, rfc, area, "
                + "turno, estatus, ruta_imagen) VALUES (?,?,?,?,?,?,?,?,?,?)", a.getidAdministrador(), a.getNombre(),
                a.getApellido(), a.getEdad(), a.getTelefono(), a.getRFC(), a.getArea(), a.getTurno(), a.getEstatus(),
                a.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(administrador a) {
        return Db.ejecutar("UPDATE administrador SET nombre=?, apellido=?, edad=?, telefono=?, rfc=?, area=?, turno=?, "
                + "estatus=?, ruta_imagen=? WHERE id_administrador=?", a.getNombre(), a.getApellido(), a.getEdad(),
                a.getTelefono(), a.getRFC(), a.getArea(), a.getTurno(), a.getEstatus(), a.getRutaImagen(),
                a.getidAdministrador()) > 0;
    }

    @Override
    public boolean eliminar(administrador a) {
        return Db.ejecutar("DELETE FROM administrador WHERE id_administrador=?", a.getidAdministrador()) > 0;
    }

    @Override
    public List<administrador> listar() {
        return Db.consultar("SELECT * FROM administrador ORDER BY id_administrador", AdministradorDAO::mapear);
    }

    @Override
    public administrador traer(String clave) {
        return Db.uno("SELECT * FROM administrador WHERE id_administrador = ?", AdministradorDAO::mapear,
                Integer.parseInt(clave));
    }
}
