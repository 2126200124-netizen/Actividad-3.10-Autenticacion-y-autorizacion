// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import java.sql.SQLException;


public class DbException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DbException(SQLException causa) {
        super(traducir(causa), causa);
    }

    private static String traducir(SQLException e) {
        String estado = e.getSQLState();
        if (estado != null && estado.startsWith("08")) {
            return "No se pudo conectar con MySQL. Verifica que el servidor esté encendido y los datos de Db.java.";
        }
        switch (e.getErrorCode()) {
            case 1062:
                return "Ya existe un registro con ese identificador o con un dato que debe ser único (RFC, código, número de aula, etc.).";
            case 1452:
                return "El registro hace referencia a un dato que no existe (alumno, materia o departamento).";
            case 1451:
                return "No se puede eliminar: hay otros registros que dependen de este.";
            case 3819:
                return "Algún valor no cumple las reglas de la base de datos.";
            case 1045:
                return "MySQL rechazó el usuario o la contraseña (revisa Db.java).";
            case 1049:
                return "No existe la base de datos 'escuela'. Ejecuta primero sql/escuela.sql.";
            default:
                return String.valueOf(e.getMessage());
        }
    }
}
