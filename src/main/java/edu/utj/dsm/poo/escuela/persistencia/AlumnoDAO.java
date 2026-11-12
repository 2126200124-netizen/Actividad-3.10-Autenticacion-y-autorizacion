// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import edu.utj.dsm.poo.escuela.modelo.Alumno;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AlumnoDAO implements listable<Alumno> {

    private static final String SEL = "SELECT a.*, COALESCE((SELECT AVG(c.valor) FROM calificacion c "
            + "WHERE c.matricula = a.matricula), 0) AS promedio FROM alumno a";

    private static Alumno mapear(ResultSet rs) throws SQLException {
        Alumno a = new Alumno(rs.getString("nombre"), rs.getString("apellido"), rs.getInt("edad"),
                rs.getString("telefono"), rs.getString("matricula"), String.valueOf(rs.getInt("grado")),
                rs.getDouble("promedio"));
        a.setEstatus(rs.getString("estatus"));
        a.setRutaImagen(rs.getString("ruta_imagen"));
        return a;
    }

    @Override
    public boolean agregar(Alumno a) {
        return Db.ejecutar("INSERT INTO alumno (matricula, nombre, apellido, edad, telefono, grado, estatus, ruta_imagen) "
                + "VALUES (?,?,?,?,?,?,?,?)", a.getMatricula(), a.getNombre(), a.getApellido(), a.getEdad(),
                a.getTelefono(), Integer.parseInt(a.getGrado()), a.getEstatus(), a.getRutaImagen()) > 0;
    }

    @Override
    public boolean actualizar(Alumno a) {
        return Db.ejecutar("UPDATE alumno SET nombre=?, apellido=?, edad=?, telefono=?, grado=?, estatus=?, ruta_imagen=? "
                + "WHERE matricula=?", a.getNombre(), a.getApellido(), a.getEdad(), a.getTelefono(),
                Integer.parseInt(a.getGrado()), a.getEstatus(), a.getRutaImagen(), a.getMatricula()) > 0;
    }

    @Override
    public boolean eliminar(Alumno a) {
        return Db.ejecutar("DELETE FROM alumno WHERE matricula=?", a.getMatricula()) > 0;
    }

    @Override
    public List<Alumno> listar() {
        return Db.consultar(SEL + " ORDER BY a.matricula", AlumnoDAO::mapear);
    }

    @Override
    public Alumno traer(String clave) {
        return Db.uno(SEL + " WHERE a.matricula = ?", AlumnoDAO::mapear, clave);
    }
}
