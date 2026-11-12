// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.Docente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DocenteDAO implements listable<Docente> {

    private static Docente mapear(ResultSet rs) throws SQLException {
        Docente d = new Docente(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("edad"),
                rs.getString("telefono"), String.valueOf(rs.getInt("num_docente")), rs.getString("especialidad"));
        d.setRFC(rs.getString("rfc"));
        int dep = rs.getInt("id_departamento");
        d.setIdDepartamento(rs.wasNull() ? null : dep);
        d.setEstatus(rs.getString("estatus"));
        d.setRutaImagen(rs.getString("ruta_imagen"));
        return d;
    }

    @Override
    public boolean agregar(Docente d) {
        return Db.ejecutar("INSERT INTO docente (num_docente, nombre, apellido, edad, telefono, rfc, especialidad, "
                + "id_departamento, estatus, ruta_imagen) VALUES (?,?,?,?,?,?,?,?,?,?)",
                Integer.parseInt(d.getNumDocente()), d.getNombre(), d.getApellido(), d.getEdad(), d.getTelefono(),
                d.getRFC(), d.getespecialidad(), d.getIdDepartamento(), d.getEstatus(), d.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(Docente d) {
        return Db.ejecutar("UPDATE docente SET nombre=?, apellido=?, edad=?, telefono=?, rfc=?, especialidad=?, "
                + "id_departamento=?, estatus=?, ruta_imagen=? WHERE num_docente=?",
                d.getNombre(), d.getApellido(), d.getEdad(), d.getTelefono(), d.getRFC(), d.getespecialidad(),
                d.getIdDepartamento(), d.getEstatus(), d.getRutaImagen(), Integer.parseInt(d.getNumDocente())) > 0;
    }

    @Override
    public boolean eliminar(Docente d) {
        return Db.ejecutar("DELETE FROM docente WHERE num_docente=?", Integer.parseInt(d.getNumDocente())) > 0;
    }

    @Override
    public List<Docente> listar() {
        return Db.consultar("SELECT * FROM docente ORDER BY num_docente", DocenteDAO::mapear);
    }

    @Override
    public Docente traer(String clave) {
        return Db.uno("SELECT * FROM docente WHERE num_docente = ?", DocenteDAO::mapear, Integer.parseInt(clave));
    }
}
