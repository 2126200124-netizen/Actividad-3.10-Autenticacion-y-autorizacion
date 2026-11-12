// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class Db {

    private static final String URL = "jdbc:mysql://localhost:3306/escuela?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&connectTimeout=5000&socketTimeout=15000";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private Db() { }

    @FunctionalInterface
    public interface Mapeador<T> {
        T mapear(ResultSet rs) throws SQLException;
    }

    public static Connection conectar() throws SQLException {
        long t0 = System.currentTimeMillis();
        System.out.println("[DB] Conectando a MySQL...");
        Connection c = DriverManager.getConnection(URL, USUARIO, CLAVE);
        System.out.println("[DB] Conectado en " + (System.currentTimeMillis() - t0) + " ms");
        return c;
    }

    /** INSERT / UPDATE / DELETE. Devuelve las filas afectadas. */
    public static int ejecutar(String sql, Object... params) {
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            asignar(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /** SELECT que devuelve una lista. */
    public static <T> List<T> consultar(String sql, Mapeador<T> mapeador, Object... params) {
        List<T> resultado = new ArrayList<>();
        try (Connection c = conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            asignar(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) resultado.add(mapeador.mapear(rs));
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return resultado;
    }

    /** SELECT que devuelve el primer registro o null. */
    public static <T> T uno(String sql, Mapeador<T> mapeador, Object... params) {
        List<T> r = consultar(sql, mapeador, params);
        return r.isEmpty() ? null : r.get(0);
    }

    private static void asignar(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
    }
}
