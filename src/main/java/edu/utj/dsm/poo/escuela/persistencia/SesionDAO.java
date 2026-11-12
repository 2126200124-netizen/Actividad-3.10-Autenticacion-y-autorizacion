// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SesionDAO {


    public int registrarInicio(int idUsuario) {
        try (Connection c = Db.conectar()) {
            int idSesion = 0;
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO sesion (id_usuario) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) idSesion = rs.getInt(1);
                }
            }
            try (PreparedStatement ps = c.prepareStatement(
                    "UPDATE usuario SET ultimo_acceso = NOW() WHERE id_usuario = ?")) {
                ps.setInt(1, idUsuario);
                ps.executeUpdate();
            }
            return idSesion;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }


    public void registrarCierre(int idSesion) {
        Db.ejecutar("UPDATE sesion SET fecha_fin = NOW() WHERE id_sesion = ? AND fecha_fin IS NULL", idSesion);
    }
}
